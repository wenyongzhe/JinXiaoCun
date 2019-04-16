package com.eshop.jinxiaocun.huiyuan.view;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/16
 * Desc:  积分兑换
 */

public class IntegralExchangeActivity extends CommonBaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral_exchange;
    }

    @Override
    protected void initView() {
        setTopToolBar("积分兑换", R.mipmap.ic_left_light,"",0,"");
    }
}
