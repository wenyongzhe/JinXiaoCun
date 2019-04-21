package com.eshop.jinxiaocun.huiyuan.view;

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
import com.eshop.jinxiaocun.huiyuan.bean.IntegralSubtractBean;
import com.eshop.jinxiaocun.huiyuan.bean.MemberCheckResultItem;
import com.eshop.jinxiaocun.huiyuan.presenter.IMemberList;
import com.eshop.jinxiaocun.huiyuan.presenter.MemberImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/16
 * Desc:  积分冲减
 */

public class IntegralSubtractActivity extends CommonBaseActivity implements INetWorResult{

    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tv_cardNumber)
    TextView mTvCardNumber;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_current_integral)
    TextView mTvCurrentIntegral;//当前积分
    @BindView(R.id.et_subtract_integral)
    EditText mEtSubtractIntegral;//冲减积分
    @BindView(R.id.et_remarks)
    EditText mEtRemarks;


    private IMemberList mApi;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral_subtract;
    }

    @Override
    protected void initView() {
        setTopToolBar("积分冲减", R.mipmap.ic_left_light,"",0,"");
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //点击键盘搜索按钮
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
                        AlertUtil.showToast("请输入卡号/手机号/姓名");
                        return false;
                    }
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

    private void refreshUIByData(MemberCheckResultItem data) {
        mEtSearch.setText("");
        mTvCardNumber.setText(data.getCardNo_TelNo());
        mTvName.setText(data.getCardName());
        mTvStatus.setText(data.getCardState());
        mTvCurrentIntegral.setText(MyUtils.convertToString(data.getVip_accnum(), "0"));
    }

    //点击搜索按钮
    @OnClick(R.id.iv_search)
    public void onClickSearch(){
        if (TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
            AlertUtil.showToast("请输入卡号/手机号/姓名");
            return;
        }

        mApi.getMemberCheckData(mEtSearch.getText().toString().trim());
        hideSoftInput();
    }


    //点击积分奖励按钮
    @OnClick(R.id.btn_integral_add)
    public void onClickIntegralAdd(){
        integralSubtract(2);
    }

    //点击积分冲减按钮
    @OnClick(R.id.btn_integral_subtract)
    public void onClickIntegralSubtract(){
        integralSubtract(3);
    }

    //as_type;//业务类型 --1：储值消费，2:积分奖励，3：积分冲减 ,4:储值冲正
    private void integralSubtract(int as_type){

        if(TextUtils.isEmpty(mTvCardNumber.getText().toString().trim())){
            AlertUtil.showToast("请先查询卡信息，再进行积分冲减/积分奖励");
            return;
        }

        IntegralSubtractBean bean = new IntegralSubtractBean();
        bean.JsonData.oper_id = Config.UserId;
        bean.JsonData.branch_no = Config.branch_no;
        bean.JsonData.as_vipNo = mTvCardNumber.getText().toString().trim();
        bean.JsonData.as_type = as_type;
        bean.JsonData.as_flow_no = "小票号";//小票号
        bean.JsonData.adec_consume_num ="本单积分值"; //本单积分值
        bean.JsonData.adec_consume_amt ="本单消费金额";  //本单消费金额
        bean.JsonData.adec_sav_amtnumeric ="储值消费金额";  //储值消费金额
        bean.JsonData.as_card_pass = "卡密码";//卡密码
        bean.JsonData.memo = mEtRemarks.getText().toString().trim();
        mApi.integralSubtract(bean);

    }



    @Override
    public void handleResule(int flag, Object o) {
        switch (flag) {

            case Config.MESSAGE_OK:
                List<MemberCheckResultItem> data = (List<MemberCheckResultItem>) o;
                if (data != null && data.size()>0) {
                    refreshUIByData(data.get(0));
                } else {
                    AlertUtil.showToast("没有对应此卡号的信息！");
                }
                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.showToast(o.toString());
                break;
            case Config.RESULT_SUCCESS:
            case Config.RESULT_FAIL:
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
