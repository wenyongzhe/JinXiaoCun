package com.eshop.jinxiaocun.lingshou.view;


import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.BaseScanActivity;

import java.util.ArrayList;
import java.util.List;

public class GetSellBillActivity extends BaseScanActivity {

    private GridLayout gv_bill_main;
    private List<GetClassPluResult> mListMainData = new ArrayList<>();
    private List<GetClassPluResult> mListDetalData = new ArrayList<>();
    private LingShouGetBillMainAdapter mGetBIllMainAdapter;
    private LingShouScanAdapter mGetBIllDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void addViewConten() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View bottomView = this.getLayoutInflater().inflate(R.layout.activity_get_sell_bill, null);
        mLinearLayout.addView(bottomView,-1,params);
        mListview = bottomView.findViewById(R.id.listview_data);
        gv_bill_main = bottomView.findViewById(R.id.gv_bill_main);
        mMyActionBar.setData("零售取单",R.mipmap.ic_left_light,"",0,"",this);
        loadData();
    }

    @Override
    protected void loadData() {
        mGetBIllMainAdapter = new LingShouGetBillMainAdapter(mListMainData);
        mGetBIllDetailAdapter = new LingShouScanAdapter(mListDetalData);
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
