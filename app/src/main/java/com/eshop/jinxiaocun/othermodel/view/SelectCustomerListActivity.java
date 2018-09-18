package com.eshop.jinxiaocun.othermodel.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.BarcodeScan;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseListActivity;
import com.eshop.jinxiaocun.othermodel.adapter.CustomerInfoListAdapter;
import com.eshop.jinxiaocun.othermodel.bean.CustomerInfoBeanResult;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.RefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SelectCustomerListActivity extends CommonBaseListActivity implements INetWorResult {

    @BindView(R.id.et_barcode)
    EditText mEtBarcode;
    private CustomerInfoListAdapter mAdapter;
    private List<CustomerInfoBeanResult> mListInfo = new ArrayList<>();
    private IOtherModel mServerApi;

    protected BarcodeScan mBarcodeScan;//扫描控制
    private int mPageIndex = 1;
    private int mPageSize = 20;

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_select_customer_list;
    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("请选择客户", R.mipmap.ic_left_light, "", 0, "");
        mLayoutBottom.setVisibility(View.GONE);
        mEtBarcode.setOnKeyListener(onKey);
        setHeaderTitle(R.id.tv_0,R.string.list_item_Customer_Name,150);
        setHeaderTitle(R.id.tv_1,R.string.list_item_Customer_id,150);
        setHeaderTitle(R.id.tv_2,R.string.list_item_Customer_zjm,150);

        mListView.setonTopRefreshListener(new RefreshListView.OnTopRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex = 1;
                getCustomerInfo("1","","",mPageIndex,mPageSize);
            }
        });

        mListView.setonBottomRefreshListener(new RefreshListView.OnBottomRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex++;
                getCustomerInfo("1","","",mPageIndex,mPageSize);
            }
        });

        mAdapter = new CustomerInfoListAdapter(mListInfo);
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        super.initData();
        IntentFilter scanDataIntentFilter = new IntentFilter();
        scanDataIntentFilter.addAction("ACTION_BAR_SCAN");
        registerReceiver(mScanDataReceiver, scanDataIntentFilter);
        try {
            mBarcodeScan = new BarcodeScan(this);
            mBarcodeScan.open();
        }catch (Exception e){

        }
        mServerApi = new OtherModelImp(this);
        getCustomerInfo("1","","",mPageIndex,mPageSize);

    }
    //手动输入条码事件
    View.OnKeyListener onKey= new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()== KeyEvent.ACTION_UP){
                scanResultData(mEtBarcode.getText().toString().trim());
                return true;
            }
            return false;
        }
    };

    /*
     *扫描返回数据
     */
    private BroadcastReceiver mScanDataReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals("ACTION_BAR_SCAN")) {
                String str = intent.getStringExtra("EXTRA_SCAN_DATA");
                scanResultData(str);
            }
        }
    };

    private void scanResultData (String zjm){
        mPageIndex = 1;
        getCustomerInfo("1","",TextUtils.isEmpty(zjm)?"":zjm,mPageIndex,mPageSize);
    }
    private void getCustomerInfo(String type ,String sheetType ,String zjm,int pageIndex,int pageSize) {
        mServerApi.getCustomerInfo(type,sheetType,zjm,pageIndex,pageSize);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        Intent intent = new Intent();
        intent.putExtra("CustomerInfo",mListInfo.get(position-1));
        setResult(22,intent);
        finish();
    }

    @Override
    public void handleResule(int flag, Object o) {
        mListView.onRefreshComplete();
        switch (flag){
            case Config.MESSAGE_OK:
                if(mPageIndex == 1){
                    mListInfo = (List<CustomerInfoBeanResult>) o;
                }else{
                    if(o !=null && ((List<CustomerInfoBeanResult>) o).size()>0){
                        mListInfo.addAll((List<CustomerInfoBeanResult>) o);
                    }
                }
                mAdapter.setListInfo(mListInfo);
                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.showToast(o.toString());
                break;
        }
    }

    protected boolean createOrderBefore() {
        return false;
    }

    @Override
    protected void createOrderAfter() {

    }

    @Override
    protected boolean deleteBefore() {
        return false;
    }

    @Override
    protected void deleteAfter() {

    }

    @Override
    protected boolean modifyBefore() {
        return false;
    }

    @Override
    protected void modifyAfter() {

    }

    @Override
    protected boolean uploadBefore() {
        return false;
    }

    @Override
    protected void uploadAfter() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBarcodeScan!=null){
            mBarcodeScan.close();
        }
        unregisterReceiver(mScanDataReceiver);
    }
}
