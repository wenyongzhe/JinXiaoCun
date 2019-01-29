package com.eshop.jinxiaocun.bluetoothprinter.view;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.bluetoothprinter.adapter.BluetoothDeviceListAdapter;
import com.eshop.jinxiaocun.bluetoothprinter.entity.BluetoothPrinterManage;
import com.eshop.jinxiaocun.bluetoothprinter.entity.BluetoothDeviceInfo;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.ConfigureParamSP;
import com.eshop.jinxiaocun.widget.ActionBarClickListener;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingBluetoothActivity extends AppCompatActivity implements BluetoothPrinterManage.BluetoothResultListerner {

    @BindView(R.id.actionbar)
    MyActionBar mMyActionBar;
    @BindView(R.id.listview_data)
    ListView mListView;

    private BluetoothPrinterManage bluetoothService;
    private BluetoothDeviceInfo selectMainEntity;
    private List<BluetoothDeviceInfo> listData = new ArrayList<>();
    private BluetoothDeviceListAdapter adapterData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_bluetooth);
        Application.getInstance().addActivity(this);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initView();
    }

    protected void initView() {
        ButterKnife.bind(this);
        setTopToolBar("蓝牙打印机设置",R.mipmap.ic_left_light,"",0,"");
        bluetoothService = BluetoothPrinterManage.getInstance();
        bluetoothService.init(this,this);
        listData = bluetoothService.getPairedDevices();
        adapterData = new BluetoothDeviceListAdapter(this, listData);
        mListView.setAdapter(adapterData);

        if(listData.size()>0){
            //保存已配对的地址
            Config.BluetoothAddress = listData.get(0).getBluetoothAddress();
            ConfigureParamSP.getInstance().saveValue(this, ConfigureParamSP.KEY_BLUETOOTHADDRESS, Config.BluetoothAddress);
        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView lv = (ListView) parent;
                selectMainEntity = (BluetoothDeviceInfo) lv.getItemAtPosition(position);
                adapterData.setItemClickPosition(position);
                adapterData.notifyDataSetInvalidated();
            }
        });
    }

    public void setTopToolBar(String strTitle, @DrawableRes int resIdLeft, String strLeft, @DrawableRes int resIdRight, String strRight){
        mMyActionBar.setData(strTitle, resIdLeft, strLeft,resIdRight, strRight, new ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
    }
    @Override
    protected void onDestroy() {
        if(bluetoothService!=null){
            bluetoothService.disConnectBluetooth();
            bluetoothService = null;
        }

        if(listData!=null){
            listData.clear();
            listData=null;
        }
        Application.getInstance().finishActivity(this);
        super.onDestroy();
    }

    @OnClick(R.id.searchDevices)
    public void OnSearchDevices()
    {
        bluetoothService.searchBluetooth();
    }

    @OnClick(R.id.btn_connect)
    public void OnConnect(){
        if (selectMainEntity != null) {
            bluetoothService.connectBluetooth();
        }else{
            AlertUtil.showToast("请选择连接的蓝牙打印机", this);
        }
    }

    @OnClick(R.id.btn_cancel_connect)
    public void onDisConnect(){
        bluetoothService.disConnectBluetooth();
    }

    @OnClick(R.id.btn_pair)
    public void OnPair(){
        if (selectMainEntity != null) {
           bluetoothService.pairBluetooth(selectMainEntity.getBluetoothAddress());
        }else{
            AlertUtil.showToast("请选择连接的蓝牙打印机", this);
        }
    }

    @OnClick(R.id.btn_print)
    public void OnPrint(){}

    @Override
    public void IsConnect(boolean isConnect) {
        if (isConnect){
            AlertUtil.showToast("连接成功！", this);
        }else{
            AlertUtil.showToast("连接失败！", this);
        }
    }

    @Override
    public void SearchDevices(List<BluetoothDeviceInfo> mDevices) {
        adapterData.notifyDataSetChanged();
    }

    @Override
    public void IsPair(boolean isPair) {
        if (selectMainEntity != null){
            if (isPair){
                selectMainEntity.setPairedStatus("已配对");
                adapterData.notifyDataSetChanged();
                AlertUtil.showToast("已配对成功！", this);
            }else{
                AlertUtil.showToast("已配对失败！", this);
            }
        }
    }
}
