package com.eshop.jinxiaocun.base.view;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eshop.jinxiaocun.R;

public abstract class BaseScanActivity extends BaseActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View rootView = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        View bottomView = this.getLayoutInflater().inflate(R.layout.common_scan_bottom, null);
        ((ViewGroup) rootView).addView(bottomView, -1, params);
    }

    @Override
    protected void loadData() {

    }


}
