package com.eshop.jinxiaocun.pifaxiaoshou.view;

import android.content.Intent;
import android.os.Bundle;

import com.eshop.jinxiaocun.base.view.BaseTabListActivity;

public class XiaoShouDanTabListActivity extends BaseTabListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {
        Intent mIntent = new Intent(this,XiaoshouDanScanActivity.class);
        startActivity(mIntent);
    }
}
