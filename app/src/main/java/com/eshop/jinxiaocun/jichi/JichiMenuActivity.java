package com.eshop.jinxiaocun.jichi;

import android.content.Intent;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.peisong.view.PeisongChukuListActivity;
import com.eshop.jinxiaocun.peisong.view.PeisongRukuListActivity;
import com.eshop.jinxiaocun.peisong.view.YaohuoOrderListActivity;

import butterknife.OnClick;

public class JichiMenuActivity extends CommonBaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_jichi_manager;
    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("配送管理",R.mipmap.ic_left_light,"",0,"");

    }


    @OnClick(R.id.btn_yaohuo_order)
    public void onClickYaohuoOrder() {
        startActivity(new Intent(this, YaohuoOrderListActivity.class));
    }

    @OnClick(R.id.btn_peisong_ruku)
    public void onClickPeisongRuku() {
        startActivity(new Intent(this, PeisongRukuListActivity.class));
    }


}