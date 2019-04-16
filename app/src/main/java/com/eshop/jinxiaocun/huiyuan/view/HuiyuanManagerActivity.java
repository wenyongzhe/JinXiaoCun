package com.eshop.jinxiaocun.huiyuan.view;

import android.content.Intent;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;

import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/16
 * Desc: 会员管理
 */

public class HuiyuanManagerActivity extends CommonBaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_huiyuan_manager;
    }

    @Override
    protected void initView() {
        setTopToolBar("会员",R.mipmap.ic_left_light,"",0,"");
    }

    //新增会员
    @OnClick(R.id.btn_add_member)
    public void onClickAddMember(){
        startActivity(new Intent(this,AddMemberActivity.class));
    }

    //会员查询
    @OnClick(R.id.btn_member_check)
    public void onClickMemberCheck(){
        startActivity(new Intent(this,MemberCheckActivity.class));
    }

    //会员充值
    @OnClick(R.id.btn_member_recharge)
    public void onClickMemberRecharge(){
        startActivity(new Intent(this,MemberRechargeActivity.class));
    }

    //消费查询
    @OnClick(R.id.btn_expense_check)
    public void onClickExpenseCheck(){
        startActivity(new Intent(this,ExpenseCheckActivity.class));
    }

    //积分冲减
    @OnClick(R.id.btn_integral_subtract)
    public void onClickIntegralSubtract(){
        startActivity(new Intent(this,IntegralSubtractActivity.class));
    }

    //积分兑换
    @OnClick(R.id.btn_integral_exchange)
    public void onClickIntegralExchange(){
        startActivity(new Intent(this,IntegralExchangeActivity.class));
    }


}
