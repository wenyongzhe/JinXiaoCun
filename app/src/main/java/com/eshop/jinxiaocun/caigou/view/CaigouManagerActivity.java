package com.eshop.jinxiaocun.caigou.view;



import android.content.Intent;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;

import butterknife.OnClick;

public class CaigouManagerActivity extends CommonBaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_caigou_manager;
    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("采购管理",R.mipmap.ic_left_light,"",0,"");

    }

    @OnClick(R.id.btn_caigou_order)
    public void onClickCaigouOrder() {
        startActivity(new Intent(this,CaigouOrderListActivity.class));
    }

    @OnClick(R.id.btn_caigou_rucang)
    public void onClickCaigouRucang() {

    }


}
