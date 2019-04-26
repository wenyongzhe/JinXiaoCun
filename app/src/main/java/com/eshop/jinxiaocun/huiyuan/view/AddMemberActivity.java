package com.eshop.jinxiaocun.huiyuan.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.huiyuan.bean.AddMemberBean;
import com.eshop.jinxiaocun.huiyuan.bean.MemberCheckResultItem;
import com.eshop.jinxiaocun.huiyuan.presenter.IMemberList;
import com.eshop.jinxiaocun.huiyuan.presenter.MemberImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.util.List;
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
    @BindView(R.id.et_sex)
    EditText mEtSex;
    @BindView(R.id.et_birthday)
    EditText mEtBirthday;
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
        mEtSex.setText(info.getVip_sex());
        mEtBirthday.setText(info.getBirthDay());
        mEtRemarks.setText(info.getMemo());
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

        if(TextUtils.isEmpty(mEtSex.getText().toString().trim())){
            AlertUtil.showToast("请输入性别！");
            return;
        }

        if(TextUtils.isEmpty(mEtBirthday.getText().toString().trim())){
            AlertUtil.showToast("请输入生日！");
            return;
        }

        //正则表达式  判断日期为合法日期
        String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(mEtBirthday.getText().toString().trim());
        boolean dateType = mat.matches();
        if(!dateType){
            AlertUtil.showToast("生日格式为xxxx-xx-xx/xxxx/xx/xx");
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
        bean.JsonData.Sex = mEtSex.getText().toString().trim();
        bean.JsonData.BirthDay = mEtBirthday.getText().toString().trim();
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
                AlertUtil.dismissProgressDialog();
                AlertUtil.showToast(o.toString());
                break;
            case Config.RESULT_SUCCESS:
            case Config.RESULT_FAIL:
                AlertUtil.dismissProgressDialog();
                AlertUtil.showToast(o.toString());
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
