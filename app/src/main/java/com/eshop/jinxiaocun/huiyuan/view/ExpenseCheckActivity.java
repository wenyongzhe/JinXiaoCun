package com.eshop.jinxiaocun.huiyuan.view;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/16
 * Desc: 消费查询
 */

public class ExpenseCheckActivity extends CommonBaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_expense_check;
    }

    @Override
    protected void initView() {
        setTopToolBar("消费查询", R.mipmap.ic_left_light,"",0,"");
    }
}
