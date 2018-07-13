package com.eshop.jinxiaocun.xiaoshou.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.BaseListActivity;

public class XiaoShouTuiHuoListActivity extends BaseListActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLinearLayout.addView(getView(R.layout.activity_xiaoshou_tuihuo));
    }
}
