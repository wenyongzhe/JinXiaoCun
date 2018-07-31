package com.eshop.jinxiaocun.xiaoshou.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.BaseScanActivity;

public class XiaoshouDanScanActivity extends BaseScanActivity {

    private LinearLayout ly_kaidan;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT);
        mLinearLayout.addView(getView(R.layout.activity_add_xiaoshou_dan),-1,params);
        ly_kaidan = findViewById(R.id.lv_xiaoshoudan);

    }

    @Override
    protected void loadData() {
        super.loadData();
    }

}
