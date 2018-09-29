package com.eshop.jinxiaocun.peisong.view;



import android.content.Intent;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;

import butterknife.OnClick;

public class PeisongManagerActivity extends CommonBaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_peisong_manager;
    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("配送管理",R.mipmap.ic_left_light,"",0,"");

    }

    @OnClick(R.id.btn_peisong_ruku)
    public void onClickPeisongRuku() {
        startActivity(new Intent(this,PeisongRukuListActivity.class));
    }

    @OnClick(R.id.btn_peisong_chuku)
    public void onClickPeisongChuku() {
        startActivity(new Intent(this,PeisongChukuListActivity.class));
    }


}
