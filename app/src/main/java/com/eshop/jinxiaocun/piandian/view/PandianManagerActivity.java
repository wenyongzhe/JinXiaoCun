package com.eshop.jinxiaocun.piandian.view;


import android.content.Intent;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;

import butterknife.OnClick;

public class PandianManagerActivity extends CommonBaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pandian_manager;
    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("盘点管理",R.mipmap.ic_left_light,"",0,"");

    }

    @OnClick(R.id.btn_create_pd)
    public void onClickCreatePandianOrder(){
        startActivity(new Intent(this,PandianCreateActivity.class));
    }


}
