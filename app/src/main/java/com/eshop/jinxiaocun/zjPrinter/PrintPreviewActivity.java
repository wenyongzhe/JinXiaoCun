package com.eshop.jinxiaocun.zjPrinter;

import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;

public class PrintPreviewActivity extends CommonBaseActivity {

    TextView tv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_print_preview;
    }

    @Override
    protected void initView() {
        super.initView();
        tv = findViewById(R.id.tv_print);
        String str = getIntent().getStringExtra("content");
        if(str != null){
            tv.setText(str);
        }
    }

    @Override
    protected void initData() {
        super.initData();

    }
}
