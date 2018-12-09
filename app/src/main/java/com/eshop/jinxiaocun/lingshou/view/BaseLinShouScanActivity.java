package com.eshop.jinxiaocun.lingshou.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.BaseScanActivity;

public class BaseLinShouScanActivity extends BaseScanActivity {

    @Override
    public void addViewConten() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View bottomView = this.getLayoutInflater().inflate(R.layout.common_linshou_scan_bottom, null);
        mLinearLayout.addView(bottomView,-1,params);
        mListview = bottomView.findViewById(R.id.listview_data);
        mMyActionBar.setData("零售",R.mipmap.ic_left_light,"",0,"查询商品",this);

    }

    @Override
    protected void scanData(String barcode) {
    }

    @Override
    public void onLeftClick() {
    }

    @Override
    public void onRightClick() {
    }
}
