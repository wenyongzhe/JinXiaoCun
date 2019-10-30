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
import com.eshop.jinxiaocun.othermodel.adapter.WarehouseInfoListAdapter;
import com.eshop.jinxiaocun.othermodel.bean.WarehouseInfoBeanResult;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.RefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.eshop.jinxiaocun.base.view.BaseScanActivity.EXTRA_BARCODE_STRING;
import static com.eshop.jinxiaocun.base.view.CommonBaseScanActivity.ACTION_BROADCAST_RECEIVER;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/28
 * Desc: 门店、仓库、机构、分部 都是用这个接口(就是名称是的叫法不一样)
 */
public class SelectWarehouseListActivity extends CommonBaseListActivity implements INetWorResult {

    @BindView(R.id.et_barcode)
    EditText mEtBarcode;

    private WarehouseInfoListAdapter mAdapter;
    private List<WarehouseInfoBeanResult> mListInfo = new ArrayList<>();
    private IOtherModel mServerApi;

    protected BarcodeScan mBarcodeScan;//扫描控制
    private String mSheetType;
    private int mShowType=1;//1门店、2仓库、3机构、4分部

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_select_warehouse_list;
    }

    @Override
    protected void initView() {
        super.initView();

        mSheetType = getIntent().getStringExtra("SheetType");
        mShowType = getIntent().getIntExtra("ShowType",1);
        mLayoutBottom.setVisibility(View.GONE);
        mEtBarcode.setOnKeyListener(onKey);

        if(mShowType ==1){
            setTopToolBar("请选择门店", R.mipmap.ic_left_light, "", 0, "");
            setHeaderTitle(R.id.tv_0,R.string.list_item_ShopName,150);
            setHeaderTitle(R.id.tv_1,R.string.list_item_ShopCode,150);
        }else if(mShowType ==2){
            setTopToolBar("请选择仓库", R.mipmap.ic_left_light, "", 0, "");
            setHeaderTitle(R.id.tv_0,R.string.list_item_StoreName,150);
            setHeaderTitle(R.id.tv_1,R.string.list_item_StoreCode,150);
        }else if(mShowType ==3){
            setTopToolBar("请选择机构", R.mipmap.ic_left_light, "", 0, "");
            setHeaderTitle(R.id.tv_0,R.string.list_item_OrganizationName,150);
            setHeaderTitle(R.id.tv_1,R.string.list_item_OrganizationCode,150);
        }else if(mShowType ==4){
            setTopToolBar("请选择分部", R.mipmap.ic_left_light, "", 0, "");
            setHeaderTitle(R.id.tv_0,R.string.list_item_DepName,150);
            setHeaderTitle(R.id.tv_1,R.string.list_item_DepCode,150);
        }

        setHeaderTitle(R.id.tv_2,R.string.list_item_Customer_zjm,150);

        mAdapter = new WarehouseInfoListAdapter(mListInfo);
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
            if(Config.DEVICE_TYPE != 1){
                mBarcodeScan = new BarcodeScan(this);
                mBarcodeScan.open();
            }
        }catch (Exception e){

        }
        mServerApi = new OtherModelImp(this);
        getWarehouseInfo();

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
            if (action.equals(ACTION_BROADCAST_RECEIVER)) {
                String str = intent.getStringExtra(EXTRA_BARCODE_STRING);
                scanResultData(str);
            }

            if (action.equals("ACTION_BAR_SCAN")) {
                String str = intent.getStringExtra("EXTRA_SCAN_DATA");
                scanResultData(str);
            }
        }
    };

    private void scanResultData (String supplyCode){
        getWarehouseInfo();
    }
    private void getWarehouseInfo() {
        mServerApi.getWarehouseUnfo(mSheetType);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        Intent intent = new Intent();
        intent.putExtra("WarehouseInfo",mListInfo.get(position-1));
        setResult(22,intent);
        finish();
    }

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){
            case Config.MESSAGE_OK:
                mListInfo = (List<WarehouseInfoBeanResult>) o;
                mAdapter.setListInfo(mListInfo);
                break;
            case Config.MESSAGE_FAIL:
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
    protected boolean deleteOrderBefore() {
        return false;
    }

    @Override
    protected void deleteOrderAfter() {

    }

    @Override
    protected boolean modifyBefore() {
        return false;
    }

    @Override
    protected void modifyAfter() {

    }

    @Override
    protected boolean checkBefore() {
        return false;
    }

    @Override
    protected void checkAfter() {

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
