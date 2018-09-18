package com.eshop.jinxiaocun.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DensityUtil;
import com.eshop.jinxiaocun.utils.MyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectPayDialog extends Activity {

    @BindView(R.id.lv_select_pay)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pay);

        ButterKnife.bind(this);
    }

}
