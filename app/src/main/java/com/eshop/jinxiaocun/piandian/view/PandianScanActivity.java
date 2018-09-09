package com.eshop.jinxiaocun.piandian.view;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseScanActivity;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/9
 * Desc:
 */

public class PandianScanActivity extends CommonBaseScanActivity implements INetWorResult{


    private String mSheetNo;//盘点批号

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_pandian_scan;
    }

    @Override
    protected void scanResultData(String barcode) {

    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("盘点明细",R.mipmap.ic_left_light,"",0,"");


    }

    @Override
    protected void loadData() {
        super.loadData();
        mSheetNo = getIntent().getStringExtra("sheet_no");


    }

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){

        }
    }
}
