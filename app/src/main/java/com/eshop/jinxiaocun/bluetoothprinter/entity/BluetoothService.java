package com.eshop.jinxiaocun.bluetoothprinter.entity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;

import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.printer.sdk.PrinterConstants;
import com.printer.sdk.PrinterInstance;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by zwei on 2018/6/9.
 * 蓝牙打印模块
 */

public class BluetoothService {

    /**
     * 加载数据
     */
    private List<BluetoothDeviceInfo> listData = new ArrayList<>();
    private BluetoothResultListerner resultListerner;
    private BluetoothAdapter mBtAdapter;
    private static PrinterInstance myPrinter;
    private ProgressDialog progressDialog;
    private static BluetoothDevice mDevice;
    private static boolean isConnected = false;// 蓝牙连接状态
    private static boolean  hasRegSearchReceiver = false;
    private Context mContext;

    public BluetoothService(Context context, BluetoothResultListerner resultListerner){
        this.resultListerner = resultListerner;
        this.mContext = context;
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
    }


    public List<BluetoothDeviceInfo> getPairedDevices(){
        try {
            if (isBluetoothOpen()) {
                Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
                if (pairedDevices.size() > 0) {
                    for (BluetoothDevice device : pairedDevices) {
                        BluetoothDeviceInfo item = new BluetoothDeviceInfo();
                        item.setBluetoothName(device.getName());
                        item.setBluetoothAddress(device.getAddress());
                        item.setPairedStatus("已配对");
                        listData.add(item);
                    }
                }
            }
            return listData;
        }
        catch (Exception ex) {
            AlertUtil.showToast("获取已配对的蓝牙打印机失败！原因：" + ex.getMessage(), mContext);
            return null;
        }
    }

    /*
    increase参数说明：
    1、范围0~7
    2、0 表示不加大
    3、1 表示加大一倍，以此类推
     */
    public void setFont(int increase, boolean isBold, boolean isUnline){
        try {
            if (isBluetoothOpen()) {
                if (PrinterInstance.mPrinter != null && isConnected) {

                    PrinterInstance.mPrinter.setFont(0, increase, increase, isBold ? 1 : 0, isUnline ? 1 :0);
                } else {
                    AlertUtil.showToast("请先连接打印机", mContext);
                }
            }
        }
        catch (Exception ex) {
            AlertUtil.showToast("打印出现失败！原因：" + ex.getMessage(), mContext);
        }
    }

    public void setMargin(int mar) {
        try {
            if (isBluetoothOpen()) {
                if (PrinterInstance.mPrinter != null && isConnected) {
                    PrinterInstance.mPrinter.setLeftMargin(mar);
                } else {
                    AlertUtil.showToast("请先连接打印机", mContext);
                }
            }
        }
        catch (Exception ex) {
            AlertUtil.showToast("打印出现失败！原因：" + ex.getMessage(), mContext);
        }
    }

    //搜索蓝牙
    public void searchBluetooth(){
        try {
            if (isBluetoothOpen()) {
                if (mBtAdapter.isDiscovering()) {
                    mBtAdapter.cancelDiscovery();
                }

                listData.clear();

                IntentFilter filter = new IntentFilter();
                filter.addAction(BluetoothDevice.ACTION_FOUND);
                filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
                mContext.registerReceiver(mSearchReceiver, filter);

                mBtAdapter.startDiscovery();
                progressDialog = AlertUtil.showNoButtonProgressDialog(mContext, "正在搜索蓝牙打印机...");
            }
        }
        catch (Exception ex) {
            AlertUtil.showToast("搜索蓝牙打印机出现失败！原因：" + ex.getMessage(), mContext);
        }
    }

    public void printText(String data){
        try {
            if (isBluetoothOpen()) {
                if (PrinterInstance.mPrinter != null && isConnected) {

                    PrinterInstance.mPrinter.printText(data);
                } else {
                    AlertUtil.showToast("请先连接打印机", mContext);
                }
            }
        }
        catch (Exception ex) {
            AlertUtil.showToast("打印出现失败！原因：" + ex.getMessage(), mContext);
        }
    }

    public void connectBluetooth(){
        try {
            if (isBluetoothOpen()) {
                //已连接成功后，则不再进行连接
                if (Config.BluetoothAddress.equals("")) {
                    AlertUtil.showToast("请先配对蓝牙打印机！", mContext);
                    return;
                }
                progressDialog = AlertUtil.showNoButtonProgressDialog(mContext, "正在连接蓝牙打印机...");
                mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(
                        Config.BluetoothAddress);
                myPrinter = PrinterInstance.getPrinterInstance(mDevice, mHandler);
                new connectThread().start();
            }
        }
        catch (Exception ex) {
            AlertUtil.showToast("连接蓝牙打印机出现失败！原因：" + ex.getMessage(), mContext);
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
        }
    }

    public boolean pairBluetooth(String bluetoothAddress)
    {
        try {
            if (isBluetoothOpen()) {
                mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(
                        bluetoothAddress);
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    progressDialog = AlertUtil.showNoButtonProgressDialog(mContext, "正在配对蓝牙打印机...");
                    IntentFilter boundFilter = new IntentFilter(
                            BluetoothDevice.ACTION_BOND_STATE_CHANGED);
                    mContext.registerReceiver(mPairReceiver, boundFilter);
                    Method createBondMethod = BluetoothDevice.class
                            .getMethod("createBond");
                    createBondMethod.invoke(mDevice);
                } else {
                    AlertUtil.showToast("该打印机已配对", mContext);
                    return true;

                }
            }
        }
        catch (Exception ex) {
            AlertUtil.showToast("配对蓝牙打印机出现失败！原因：" + ex.getMessage(), mContext);
        }
        return false;
    }

    public void disConnectBluetooth()
    {
        try {
            if (isBluetoothOpen()) {
                if (mBtAdapter != null && mBtAdapter.isDiscovering()) {
                    mBtAdapter.cancelDiscovery();
                }
                if (hasRegSearchReceiver) {
                    mContext.unregisterReceiver(mSearchReceiver);
                    hasRegSearchReceiver = false;
                }
//                if (hasRegDisconnectReceiver) {
//                    mContext.unregisterReceiver(mCloseConnectionReceiver);
//                    hasRegDisconnectReceiver = false;
//                }

                if (myPrinter != null
                        && isConnected)
                {
                    myPrinter.closeConnection();
                    isConnected = false;
                }

            }
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
        catch (Exception ex) {
            AlertUtil.showToast("释放蓝牙打印机出现异常！原因：" + ex.getMessage(), mContext);
        }
    }

    private boolean isBluetoothOpen()
    {
        if (mBtAdapter != null && !mBtAdapter.isEnabled())
        {
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mContext.startActivity(enableIntent);
            AlertUtil.showToast("设备蓝牙功能没有打开，请重试！", mContext);
            return false;
        }
        else {
            return true;
        }
    }

    private final BroadcastReceiver mSearchReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (device.getName() !=null) {
                    BluetoothDeviceInfo item = new BluetoothDeviceInfo();
                    item.setBluetoothAddress(device.getAddress());
                    item.setBluetoothName(device.getName());
                    String pairedStatus = device.getBondState() == BluetoothDevice.BOND_BONDED ? "已配对" : "";
                    item.setPairedStatus(pairedStatus);
                    for (BluetoothDeviceInfo bluetoothDevice : listData)
                    {
                        if (bluetoothDevice.getBluetoothName().equals(item.getBluetoothName()))
                        {
                            return;
                        }
                    }
                    listData.add(item);
                }

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                    .equals(action)) {

                hasRegSearchReceiver = true;

                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (resultListerner != null)
                    resultListerner.SearchDevices(listData);
            }
        }
    };

    // 用于接受连接状态消息的 Handler
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PrinterConstants.Connect.SUCCESS:

//                    IntentFilter bluDisconnectFilter = new IntentFilter();
//                    bluDisconnectFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
//                    mContext.registerReceiver(mCloseConnectionReceiver, bluDisconnectFilter);
//                    hasRegDisconnectReceiver = true;

                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    isConnected = true;
                    AlertUtil.showToast("已成功连接蓝牙打印机！", mContext);

                    if (resultListerner != null)
                        resultListerner.IsConnect(true);

                    break;
                case PrinterConstants.Connect.FAILED:
                    isConnected = false;
                    if (resultListerner != null)
                        resultListerner.IsConnect(false);
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    break;
                case PrinterConstants.Connect.CLOSED:
                    isConnected = false;
//                    if (resultListerner != null)
//                        resultListerner.IsConnect(false);
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    break;
                case PrinterConstants.Connect.NODEVICE:
                    isConnected = false;
                    if (resultListerner != null)
                        resultListerner.IsConnect(false);

                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    break;
                default:
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    break;
            }
        }

    };

//    private BroadcastReceiver mCloseConnectionReceiver = new BroadcastReceiver()
//    {
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            BluetoothDevice device = intent
//                    .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//
//            if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED))
//            {
//
//                if (device != null && myPrinter != null
//                        && isConnected && device.equals(mDevice))
//                {
//                    myPrinter.closeConnection();
//                    mHandler.obtainMessage(PrinterConstants.Connect.CLOSED).sendToTarget();
//                }
//            }
//        }
//    };

    private BroadcastReceiver mPairReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (!mDevice.equals(device)) {
                    return;
                }
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_BONDING:
                        break;
                    case BluetoothDevice.BOND_BONDED:
                        //保存已配对的地址
                        Config.BluetoothAddress = device.getAddress();
                        //ConfigureParamSP.getInstance().saveValue(mContext, ConfigureParamSP.KEY_BLUETOOTHADDRESS, device.getAddress());
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        mContext.unregisterReceiver(mPairReceiver);
                        if (resultListerner != null)
                            resultListerner.IsPair(true);
                        break;
                    case BluetoothDevice.BOND_NONE:
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        mContext.unregisterReceiver(mPairReceiver);
                        if (resultListerner != null)
                            resultListerner.IsPair(false);
                        break;
                    default:
                        break;
                }
            }
        }
    };

    private class connectThread extends Thread {
        @Override
        public void run() {
            if (myPrinter != null){
                isConnected = myPrinter.openConnection();
            }
        }
    }

    public interface BluetoothResultListerner {
        void IsConnect(boolean isConnect);
        void SearchDevices(List<BluetoothDeviceInfo> mDevices);
        void IsPair(boolean isPair);
    }
}
