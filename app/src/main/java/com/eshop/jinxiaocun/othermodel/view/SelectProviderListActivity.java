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
import com.eshop.jinxiaocun.othermodel.adapter.ProviderInfoListAdapter;
import com.eshop.jinxiaocun.othermodel.bean.ProviderInfoBeanResult;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.RefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/28
 * Desc: 选择供应商
 */
public class SelectProviderListActivity extends CommonBaseListActivity implements INetWorResult {

    @BindView(R.id.et_barcode)
    EditText mEtBarcode;
    private ProviderInfoListAdapter mAdapter;
    private List<ProviderInfoBeanResult> mListInfo = new ArrayList<>();
    private IOtherModel mServerApi;

    protected BarcodeScan mBarcodeScan;//扫描控制
    private int mPageIndex = 1;
    private int mPageSize = 20;
    private String mSheetType;

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_select_provider_list;
    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("请选择供应商", R.mipmap.ic_left_light, "", 0, "");
        mLayoutBottom.setVisibility(View.GONE);
        mEtBarcode.setOnKeyListener(onKey);
        mSheetType = getIntent().getStringExtra("SheetType");
        setHeaderTitle(R.id.tv_0,R.string.list_item_SupName,150);
        setHeaderTitle(R.id.tv_1,R.string.list_item_SupCode,150);
        setHeaderTitle(R.id.tv_2,R.string.list_item_Customer_zjm,150);

        mListView.setonTopRefreshListener(new RefreshListView.OnTopRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex = 1;
                getProviderInfo(TextUtils.isEmpty(mEtBarcode.getText().toString().trim())?""
                        :mEtBarcode.getText().toString().trim(),mPageIndex,mPageSize);
            }
        });

        mListView.setonBottomRefreshListener(new RefreshListView.OnBottomRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex++;
                getProviderInfo(TextUtils.isEmpty(mEtBarcode.getText().toString().trim())?""
                        :mEtBarcode.getText().toString().trim(),mPageIndex,mPageSize);
            }
        });

        mAdapter = new ProviderInfoListAdapter(mListInfo);
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
        getProviderInfo("",mPageIndex,mPageSize);

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

    private void scanResultData (String supplyCode){
        mPageIndex = 1;
        getProviderInfo(TextUtils.isEmpty(supplyCode)?"":supplyCode,mPageIndex,mPageSize);
    }
    private void getProviderInfo(String supplyCode ,int pageIndex,int pageSize) {
        mServerApi.getProviderInfo(mSheetType,supplyCode,pageIndex,pageSize);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        Intent intent = new Intent();
        intent.putExtra("ProviderInfo",mListInfo.get(position-1));
        setResult(22,intent);
        finish();
    }

    @Override
    public void handleResule(int flag, Object o) {
        mListView.onRefreshComplete();
        switch (flag){
            case Config.MESSAGE_OK:
                if(mPageIndex == 1){
                    mListInfo = (List<ProviderInfoBeanResult>) o;
                }else{
                    if(o !=null && ((List<ProviderInfoBeanResult>) o).size()>0){
                        mListInfo.addAll((List<ProviderInfoBeanResult>) o);
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
