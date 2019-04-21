package com.eshop.jinxiaocun.huiyuan.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
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
 * Desc:  积分兑换
 */

public class IntegralExchangeActivity extends CommonBaseActivity implements INetWorResult{


    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tv_cardNumber)
    TextView mTvCardNumber;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.tv_type)
    TextView mTvCardType;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_current_integral)
    TextView mTvCurrentIntegral;//卡的当前积分
    @BindView(R.id.lv_exchange_goods)
    ListView mListView;
    @BindView(R.id.btn_exchange_goods)
    Button mBtnExchangeGoods;

    private IMemberList mApi;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral_exchange;
    }

    @Override
    protected void initView() {
        setTopToolBar("积分兑换", R.mipmap.ic_left_light,"",0,"");
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
        mTvCardType.setText(data.getCardType());
        mTvStatus.setText(data.getCardState());
        mTvCurrentIntegral.setText(MyUtils.convertToString(data.getVip_accnum(), "0"));
    }

    //查询兑换商品
    @OnClick(R.id.tv_exchange_goods)
    public void onClickLookExchangeGoods(){
        if (TextUtils.isEmpty(mTvCardNumber.getText().toString().trim())) {
            AlertUtil.showToast("请查询会员卡信息，再查询兑换商品信息");
            return;
        }

        Intent intent = new Intent(this,IntegralExchangeGoodsActivity.class);
        intent.putExtra("CardNo",mTvCardNumber.getText().toString().trim());
        intent.putExtra("Integral",MyUtils.convertToFloat(
                mTvCurrentIntegral.getText().toString().trim(), 0));
        startActivityForResult(intent,111);


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

    //兑换礼品
    @OnClick(R.id.btn_exchange_goods)
    public void onClickExchangeGoods(){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==111 && resultCode == RESULT_OK){
            mBtnExchangeGoods.setVisibility(View.VISIBLE);
        }
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
