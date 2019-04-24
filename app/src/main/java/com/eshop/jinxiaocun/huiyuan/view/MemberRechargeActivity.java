package com.eshop.jinxiaocun.huiyuan.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.huiyuan.bean.MemberCheckResultItem;
import com.eshop.jinxiaocun.huiyuan.bean.MemberRechargeBean;
import com.eshop.jinxiaocun.huiyuan.presenter.IMemberList;
import com.eshop.jinxiaocun.huiyuan.presenter.MemberImp;
import com.eshop.jinxiaocun.lingshou.bean.GetFlowNoBeanResult;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/16
 * Desc: 会员充值
 */

public class MemberRechargeActivity extends CommonBaseActivity implements INetWorResult{

    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tv_cardNumber)
    TextView mTvCardNumber;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_balance)
    TextView mTvBalance;//余额
    @BindView(R.id.et_recharge_money)
    EditText mEtRechargeMoney;//充值金额
    @BindView(R.id.et_give_free_money)
    EditText mEtGiveFreeMoney;//赠送金额
    @BindView(R.id.tv_all_recharge_money)
    TextView mTvAllRechargeMoney;//合计
    @BindView(R.id.et_remarks)
    EditText mEtRemarks;//备注

    private IMemberList mApi;
    private ILingshouScan mApi2;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_recharge;
    }

    @Override
    protected void initView() {
        setTopToolBar("会员充值",R.mipmap.ic_left_light,"",0,"");
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //点击键盘搜索按钮
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(TextUtils.isEmpty(mEtSearch.getText().toString())){
                        AlertUtil.showToast("请输入会员卡号/手机号/姓名");
                        return false;
                    }
                    AlertUtil.showNoButtonProgressDialog(MemberRechargeActivity.this,"正在读取卡信息，请稍后...");
                    mApi.getMemberCheckData(mEtSearch.getText().toString());
                    hideSoftInput();
                    return true;
                }
                return false;
            }
        });

        mEtRechargeMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setCountRechargeMoney();
            }
        });
        mEtGiveFreeMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setCountRechargeMoney();
            }
        });
    }

    @Override
    protected void initData() {
        mApi = new MemberImp(this);
        mApi2 = new LingShouScanImp(this);
    }

    //计算合计并显示
    private void setCountRechargeMoney(){
        float allCountMoney = MyUtils.convertToFloat(mEtRechargeMoney.getText().toString().trim(),0)+
                MyUtils.convertToFloat(mEtGiveFreeMoney.getText().toString().trim(),0);
        mTvAllRechargeMoney.setText(MyUtils.convertToString(allCountMoney,"0"));
    }

    //查询到卡号信息，则刷新
    private void refreshUIByData(MemberCheckResultItem data) {
        mEtSearch.setText("");
        mTvCardNumber.setText(data.getCardNo_TelNo());
        mTvName.setText(data.getCardName());
        mTvBalance.setText(MyUtils.convertToString(data.getResidual_amt(),"0"));
    }

    //点击搜索按钮
    @OnClick(R.id.iv_search)
    public void onClickSearch(){
        if(TextUtils.isEmpty(mEtSearch.getText().toString())){
            AlertUtil.showToast("请输入会员卡号/手机号/姓名");
            return;
        }
        AlertUtil.showNoButtonProgressDialog(this,"正在读取卡信息，请稍后...");
        mApi.getMemberCheckData(mEtSearch.getText().toString());
        hideSoftInput();
    }

    //充值
    @OnClick(R.id.btn_recharge)
    public void onClickRecharge(){
        if(TextUtils.isEmpty(mTvCardNumber.getText().toString().trim())){
            AlertUtil.showToast("充值前,先输入卡号查询卡信息!");
            return;
        }

        if(MyUtils.convertToFloat(mEtRechargeMoney.getText().toString().trim(),0)==0){
            AlertUtil.showToast("充值前,先输入充值金额!");
            return;
        }
        AlertUtil.showNoButtonProgressDialog(this,"正在为您充值，请稍后...");
        mApi2.getFlowNo();//取流水号
        hideSoftInput();
    }

    private void rechargeData(String flowNo){
        MemberRechargeBean bean = new MemberRechargeBean();
        bean.JsonData.BranchNo = Config.branch_no;
        bean.JsonData.UserId = Config.UserId;
        bean.JsonData.CardNo = mTvCardNumber.getText().toString().trim();
        //充值金额(有可能包含赠送金额)
        bean.JsonData.AddMoney = MyUtils.convertToFloat(mTvAllRechargeMoney.getText().toString().trim(),0);
        //实付金额
        bean.JsonData.PayMoney = MyUtils.convertToFloat(mEtRechargeMoney.getText().toString().trim(),0);
        bean.JsonData.FlowNo = flowNo;//"流水号"
        bean.JsonData.PayWay = "RMB";//支付方式  后续需要改成多种支付方式，跟销售一样
        bean.JsonData.Memo = mEtRemarks.getText().toString().trim();

        mApi.setMemberRechargeData(bean);
    }


    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){

            case Config.MESSAGE_OK:
                List<MemberCheckResultItem> data = (List<MemberCheckResultItem>) o;
                if (data != null && data.size()>0) {
                    refreshUIByData(data.get(0));
                } else {
                    AlertUtil.showToast("没有对应此卡号的信息！");
                }
                AlertUtil.dismissProgressDialog();
                break;
            case Config.MESSAGE_FLOW_NO:
                GetFlowNoBeanResult.FlowNoJson result = (GetFlowNoBeanResult.FlowNoJson)o;
                if(result != null ){
                    String flowNo = MyUtils.formatFlowNo(result.getFlowNo());
                    rechargeData(flowNo);
                }else{
                    AlertUtil.dismissProgressDialog();
                    AlertUtil.showToast("没有小票号信息");
                }
                break;
            case Config.RESULT_SUCCESS:
                float balance = MyUtils.convertToFloat(mTvBalance.getText().toString().trim(),0)+
                        MyUtils.convertToFloat(mTvAllRechargeMoney.getText().toString().trim(),0);
                mTvBalance.setText(MyUtils.convertToString(balance,"0"));
                mEtRechargeMoney.setText("");
                mEtGiveFreeMoney.setText("");
                mEtRemarks.setText("");
                mTvAllRechargeMoney.setText("");
                AlertUtil.dismissProgressDialog();
                AlertUtil.showToast(o.toString());
                break;
            case Config.RESULT_FAIL:
            case Config.MESSAGE_ERROR:
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
