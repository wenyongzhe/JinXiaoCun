package com.eshop.jinxiaocun.stock.view;

import android.widget.EditText;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseScanActivity;
import com.eshop.jinxiaocun.stock.presenter.IStock;
import com.eshop.jinxiaocun.stock.presenter.IStockImp;

import butterknife.BindView;

public class StockCheckActivity extends CommonBaseScanActivity implements INetWorResult {

    @BindView(R.id.et_barcode)
    EditText mEtBarcode;

    private IStock mServerApi;

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_stock_check;
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void initData() {
        super.initData();
        mServerApi = new IStockImp(this);
    }

    @Override
    protected boolean scanBefore() {
        return false;
    }

    @Override
    protected void scanResultData(String barcode) {

    }

    @Override
    protected boolean addBefore() {
        return false;
    }

    @Override
    protected void addAfter() {

    }

    @Override
    protected boolean deleteBefore() {
        return false;
    }

    @Override
    protected void deleteAfter() {

    }

    @Override
    protected boolean modifyCountBefore() {
        return false;
    }

    @Override
    protected void modifyCountAfter() {

    }

    @Override
    public void handleResule(int flag, Object o) {

    }
}
