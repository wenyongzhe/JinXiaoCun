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

        setTopToolBar("计次管理",R.mipmap.ic_left_light,"",0,"");

    }


    @OnClick(R.id.btn_jichi)
    public void onClickYaohuoOrder() {
        startActivity(new Intent(this, JichiActivity.class));
    }

    @OnClick(R.id.btn_jichi_chaxun)
    public void onClickPeisongRuku() {
        startActivity(new Intent(this, JichiChaxunActivity.class));
    }


}