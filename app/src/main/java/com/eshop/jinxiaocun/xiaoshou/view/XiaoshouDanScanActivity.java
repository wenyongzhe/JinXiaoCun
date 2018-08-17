package com.eshop.jinxiaocun.xiaoshou.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.BillType;
import com.eshop.jinxiaocun.base.view.BaseScanActivity;
import com.eshop.jinxiaocun.utils.Config;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class XiaoshouDanScanActivity extends BaseScanActivity implements INetWorResult {

    @BindView(R.id.ly1_sp)
    private Spinner mSpinner1;
    @BindView(R.id.ly2_sp)
    private Spinner mSpinner2;
    @BindView(R.id.ly3_sp)
    private Spinner mSpinner3;

    private LinearLayout ly_kaidan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        loadData();
        initView();
    }

    @Override
    protected void loadData() {
        super.loadData();
        mUpMainBean.setSheetType(BillType.SO+"");//单据类型-批发销售
        mUpMainBean.setSheet_No("");//单号
        mUpMainBean.setBranch_No(Config.branch_no);//当前仓库

    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void initView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        View mView = this.getLayoutInflater().inflate(R.layout.activity_add_xiaoshou_dan, null);
        mLinearLayout.addView(mView,0,params);
        mSpinner1 = findViewById(R.id.ly1_sp);
        mSpinner2 = findViewById(R.id.ly2_sp);
        mSpinner3 = findViewById(R.id.ly3_sp);


        setHeaderTitle(R.id.tv_0, R.string.list_item_ProdName, 150);
        setHeaderTitle(R.id.tv_1, R.string.list_item_CountN5, 100);
        setHeaderTitle(R.id.tv_2, R.string.list_item_BarCode, 180);


        List<String> list = new ArrayList<>();
        list.add("正品");
        list.add("赠品");
        list.add("促销品");
        list.add("不良品");
        ArrayAdapter<String> mTuiHupoAdapter = new ArrayAdapter<>(XiaoshouDanScanActivity.this, R.layout.my_simple_spinner_item, list);
        mTuiHupoAdapter.setDropDownViewResource(R.layout.my_drop_down_item);
        mSpinner1.setAdapter(mTuiHupoAdapter);
        mSpinner2.setAdapter(mTuiHupoAdapter);
        mSpinner3.setAdapter(mTuiHupoAdapter);
    }

    @Override
    public void handleResule(int flag,Object o) {
        switch (flag){
            case Config.MESSAGE_OK:
                break;
            case Config.MESSAGE_ERROR:
                break;
        }
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }
}
