package com.eshop.jinxiaocun.zjPrinter;

import android.annotation.SuppressLint;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;

public class PrinterSettingActivity extends CommonBaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_printer_setting;
    }


    @Override
    protected void initView() {
        setTopToolBar("打印设置",R.mipmap.ic_left_light,"",0,"");
        setTopToolBarRightTitleAndStyle("保存",R.drawable.border_bg_primary);
    }


    @Override
    protected void initData() {

    }




}
