package com.eshop.jinxiaocun.huiyuan.view;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/16
 * Desc:  积分冲减
 */

public class IntegralSubtractActivity extends CommonBaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral_subtract;
    }

    @Override
    protected void initView() {
        setTopToolBar("积分冲减", R.mipmap.ic_left_light,"",0,"");
    }
}
