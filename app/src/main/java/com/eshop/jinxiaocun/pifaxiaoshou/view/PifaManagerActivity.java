package com.eshop.jinxiaocun.pifaxiaoshou.view;


import android.content.Intent;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.piandian.view.PandianCreateActivity;

import butterknife.OnClick;

public class PifaManagerActivity extends CommonBaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pifa_manager;
    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("批发管理",R.mipmap.ic_left_light,"",0,"");

    }

    @OnClick(R.id.btn_pifa_order)
    public void onClickPifaOrder(){
        startActivity(new Intent(this,PifaOrderListActivity.class));
    }

    @OnClick(R.id.btn_pifa_chuku)
    public void onClickPifaChuku(){
        startActivity(new Intent(this,PifaChukuListActivity.class));
    }

    @OnClick(R.id.btn_pifa_tuihuo)
    public void onClickPifaTuihuo(){
        startActivity(new Intent(this,PifaTuihuoListActivity.class));
    }

}
