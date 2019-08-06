package com.eshop.jinxiaocun.reportforms.view;

import android.content.Intent;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;

import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/8/6
 * Desc:
 */
public class ReportFormManagerActivity extends CommonBaseActivity {




    @Override
    protected int getLayoutId() {
        return R.layout.activity_reportforms_manager;
    }

    @Override
    protected void initView() {
        setTopToolBar("业务查询", R.mipmap.ic_left_light, "", 0, "");
    }


    //销售查询
    @OnClick(R.id.tv_salesCheck)
    public void onClickSalesCheck(){
        Intent intent = new Intent();
        intent.setClass(Application.mContext, SalesCheckActivity.class);
        startActivity(intent);
    }


    //收银对账
    @OnClick(R.id.tv_cashierCheck)
    public void onClickCashierCheck(){
        Intent intent = new Intent();
        intent.setClass(Application.mContext, CashierCheckActivity.class);
        startActivity(intent);
    }


}
