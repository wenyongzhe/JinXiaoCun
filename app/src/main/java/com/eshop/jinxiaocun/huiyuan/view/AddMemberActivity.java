package com.eshop.jinxiaocun.huiyuan.view;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.huiyuan.bean.AddMemberBean;
import com.eshop.jinxiaocun.huiyuan.bean.MemberCheckResultItem;
import com.eshop.jinxiaocun.huiyuan.presenter.IMemberList;
import com.eshop.jinxiaocun.huiyuan.presenter.MemberImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/16
 * Desc:
 */

public class AddMemberActivity extends CommonBaseActivity implements INetWorResult{

    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.et_card_number)
    EditText mEtCardNumber;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_phone_number)
    EditText mEtPhoneNumber;
    @BindView(R.id.et_card_type)
    EditText mEtCardType;
    @BindView(R.id.rb_man)
    RadioButton mRbMan;
    @BindView(R.id.rb_women)
    RadioButton mRbWomen;
    @BindView(R.id.tv_birthday)
    TextView mTvBirthday;
    @BindView(R.id.et_remarks)
    EditText mEtRemarks;

    private IMemberList mApi;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_member;
    }

    @Override
    protected void initView() {
        super.initView();
        setTopToolBar("会员卡维护",R.mipmap.ic_left_light,"",0,"");

        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //点击键盘搜索按钮
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
                        AlertUtil.showToast("请输入卡号/手机号/姓名");
                        return false;
                    }
                    AlertUtil.showNoButtonProgressDialog(AddMemberActivity.this,"正在读取卡信息，请稍后...");
                    mApi.getMemberCheckData(mEtSearch.getText().toString().trim());
                    hideSoftInput();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void initData() {
        mApi = new MemberImp(this);
    }

    private void refreshUIByData(MemberCheckResultItem info){
        mEtSearch.setText("");
        mEtCardNumber.setText(info.getCardNo_TelNo());
        mEtName.setText(info.getCardName());
        mEtPhoneNumber.setText(info.getMobile());
        mEtCardType.setText(info.getCardType());
        if("男".equals(info.getVip_sex())){
            mRbMan.setChecked(true);
        }else{
            mRbWomen.setChecked(true);
        }
        mTvBirthday.setText(info.getBirthDay());
        mEtRemarks.setText(info.getMemo());
    }

    //全部设置默认值
    private void refreshViewValues(){
        mEtSearch.setText("");
        mEtCardNumber.setText("");
        mEtName.setText("");
        mEtPhoneNumber.setText("");
        mEtCardType.setText("");
        mRbMan.setChecked(true);
        mRbWomen.setChecked(true);
        mTvBirthday.setText("");
        mEtRemarks.setText("");
    }

    @OnClick(R.id.iv_close)
    public void onClickClose(){
        mEtSearch.setText("");
    }

    //设置生日
    @OnClick(R.id.tv_birthday)
    public void onClickBrithday(){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);//获取日期格式器对象
        //生成一个DatePickerDialog对象，并显示。显示的DatePickerDialog控件可以选择年月日，并设置
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddMemberActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mTvBirthday.setText(year+"-"+(month+1)+"-"+dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    //搜索
    @OnClick(R.id.iv_search)
    public void onClickSearch() {
        if (TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
            AlertUtil.showToast("请输入卡号/手机号/姓名");
            return;
        }
        AlertUtil.showNoButtonProgressDialog(this,"正在读取卡信息，请稍后...");
        mApi.getMemberCheckData(mEtSearch.getText().toString().trim());
        hideSoftInput();
    }

    @OnClick(R.id.btn_save_member_info)
    public void onClickAddMember(){
        if(TextUtils.isEmpty(mEtCardNumber.getText().toString().trim())){
            AlertUtil.showToast("请输入卡号！");
            return;
        }
        if(TextUtils.isEmpty(mEtName.getText().toString().trim())){
            AlertUtil.showToast("请输入姓名！");
            return;
        }

        if(TextUtils.isEmpty(mEtPhoneNumber.getText().toString().trim())){
            AlertUtil.showToast("请输入手机号！");
            return;
        }

        if(TextUtils.isEmpty(mEtCardType.getText().toString().trim())){
            AlertUtil.showToast("请输入卡类型！");
            return;
        }

        //取出字符中的数字 [02]储值卡
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(mEtCardType.getText().toString().trim());
        String cardTypeId = m.replaceAll("").trim();

        if(TextUtils.isEmpty(cardTypeId.trim())){
            AlertUtil.showToast("卡类型没有对应的ID！");
            return;
        }

        if(!mRbMan.isChecked() && !mRbWomen.isChecked()){
            AlertUtil.showToast("请输入性别！");
            return;
        }

        if(TextUtils.isEmpty(mTvBirthday.getText().toString().trim())){
            AlertUtil.showToast("请输入生日！");
            return;
        }


        AlertUtil.showNoButtonProgressDialog(this,"正在激活新会员，请稍后...");
        AddMemberBean bean = new AddMemberBean();
        bean.JsonData.OperInfo = Config.UserId;//操作人员
        bean.JsonData.Branch_No = Config.branch_no;
        bean.JsonData.CardNo_TelNo = mEtCardNumber.getText().toString().trim();
        bean.JsonData.Name = mEtName.getText().toString().trim();
        bean.JsonData.Mobile = mEtPhoneNumber.getText().toString().trim();
        bean.JsonData.Tel = "";
        bean.JsonData.MemberType = cardTypeId;
        bean.JsonData.Sex = mRbMan.isChecked()?"男":"女";
        bean.JsonData.BirthDay = mTvBirthday.getText().toString().trim();
        bean.JsonData.Memo = mEtRemarks.getText().toString().trim();

        mApi.addMemberData(bean);

    }

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){

            case Config.MESSAGE_OK:
                AlertUtil.dismissProgressDialog();
                List<MemberCheckResultItem> data = (List<MemberCheckResultItem>) o;
                if (data != null && data.size()>0) {
                    refreshUIByData(data.get(0));
                } else {
                    AlertUtil.showToast("此卡号不存在,请输入未激活的卡号！");
                }
                break;
            case Config.MESSAGE_ERROR:
            case Config.RESULT_FAIL:
                AlertUtil.dismissProgressDialog();
                AlertUtil.showToast(o.toString());
                break;
            case Config.RESULT_SUCCESS:
                AlertUtil.dismissProgressDialog();
                AlertUtil.showToast(o.toString());
                refreshViewValues();
                break;
        }
    }

    /*隐藏软键盘*/
    private void hideSoftInput(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()){
            inputMethodManager.hideSoftInputFromWindow(mEtSearch.getApplicationWindowToken(), 0);
        }
    }

}
