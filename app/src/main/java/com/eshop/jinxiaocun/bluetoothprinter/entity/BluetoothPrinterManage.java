package com.eshop.jinxiaocun.bluetoothprinter.entity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.login.SystemSettingActivity;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.ConfigureParamSP;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.zjPrinter.BluetoothService;
import com.eshop.jinxiaocun.zjPrinter.Command;
import com.eshop.jinxiaocun.zjPrinter.PrinterCommand;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 蓝牙模块
 */

public class BluetoothPrinterManage {

    /**
     * 加载数据
     */
    private List<BluetoothDeviceInfo> listData = new ArrayList<>();
    private BluetoothResultListerner resultListerner;
    private BluetoothAdapter mBtAdapter;
    private static BluetoothService mPrinterService;
    private ProgressDialog progressDialog;
    private static BluetoothDevice mDevice;
    private static boolean isConnected = false;// 蓝牙连接状态
    private static boolean  hasRegSearchReceiver = false;
    private Context mContext;

    private BluetoothPrinterManage(){}
    private static final class BluetoothPrinterInstance{
        private static final BluetoothPrinterManage INSTANCE = new BluetoothPrinterManage();
    }
    public static BluetoothPrinterManage getInstance(){
        return BluetoothPrinterInstance.INSTANCE;
    }

    public void init(Context context, BluetoothResultListerner resultListerner){
        this.resultListerner = resultListerner;
        this.mContext = context;
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mPrinterService != null) {
            if (mPrinterService.getState() == BluetoothService.STATE_NONE) {
                mPrinterService.start();
            }
        }else{
            mPrinterService = new BluetoothService(mContext, mHandler);
        }
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
        }catch (Exception ex) {
            AlertUtil.showToast("获取已配对的蓝牙打印机失败！原因：" + ex.getMessage(), mContext);
            return null;
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
        }catch (Exception ex) {
            AlertUtil.showToast("搜索蓝牙打印机出现失败！原因：" + ex.getMessage(), mContext);
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
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                progressDialog = AlertUtil.showNoButtonProgressDialog(mContext, "正在连接蓝牙打印机...");
                mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(Config.BluetoothAddress);
                if (mPrinterService != null) {
                    if (mPrinterService.getState() == BluetoothService.STATE_CONNECTED) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        isConnected = true;
                        AlertUtil.showToast("已成功连接蓝牙打印机！", mContext);
                        if (resultListerner != null)
                            resultListerner.IsConnect(true);
                        return;
                    }
                    if (mPrinterService.getState() == BluetoothService.STATE_NONE) {
                        mPrinterService.start();
                    }
                    mPrinterService.connect(mDevice);
                }else{
                    mPrinterService = new BluetoothService(mContext, mHandler);
                    mPrinterService.connect(mDevice);
                }
            }
        } catch (Exception ex) {
            AlertUtil.showToast("连接蓝牙打印机出现失败！原因：" + ex.getMessage(), mContext);
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
        }
    }

    public boolean pairBluetooth(String bluetoothAddress){
        try {
            if (isBluetoothOpen()) {
                mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(
                        bluetoothAddress);
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    progressDialog = AlertUtil.showNoButtonProgressDialog(mContext, "正在配对蓝牙打印机...");
                    IntentFilter boundFilter = new IntentFilter(
                            BluetoothDevice.ACTION_BOND_STATE_CHANGED);
                    mContext.registerReceiver(mPairReceiver, boundFilter);
                    Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
                    createBondMethod.invoke(mDevice);
                } else {
                    //保存已配对的地址
                    Config.BluetoothAddress = mDevice.getAddress();
                    AlertUtil.showToast("该打印机已配对", mContext);
                    return true;
                }
            }
        }catch (Exception ex) {
            AlertUtil.showToast("配对蓝牙打印机出现失败！原因：" + ex.getMessage(), mContext);
        }
        return false;
    }

    public void disConnectBluetooth(){
        try {
            if (isBluetoothOpen()) {
                if (mBtAdapter != null && mBtAdapter.isDiscovering()) {
                    mBtAdapter.cancelDiscovery();
                }
                if (hasRegSearchReceiver) {
                    mContext.unregisterReceiver(mSearchReceiver);
                    hasRegSearchReceiver = false;
                }

                //关闭打印连接
                if (mPrinterService != null && isConnected){
                    //myPrinter.closeConnection();
                    mPrinterService.stop();
                    isConnected = false;
                }
            }
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
//            AlertUtil.showToast("取消蓝牙打印机成功!" , mContext);
        }catch (Exception ex) {
            AlertUtil.showToast("释放蓝牙打印机出现异常！原因：" + ex.getMessage(), mContext);
        }
    }

    private boolean isBluetoothOpen(){
        if (mBtAdapter != null && !mBtAdapter.isEnabled()){
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mContext.startActivity(enableIntent);
            AlertUtil.showToast("设备蓝牙功能没有打开，请重试！", mContext);
            return false;
        }else {
            return true;
        }
    }

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SystemSettingActivity.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
//                            AlertUtil.showToast("连接成功");
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            isConnected = true;
                            AlertUtil.showToast("已成功连接蓝牙打印机！", mContext);
                            if (resultListerner != null)
                                resultListerner.IsConnect(true);
                            break;
                        case BluetoothService.STATE_CONNECTING://现在启动一个传出连接
                            break;
                        case BluetoothService.STATE_LISTEN://现在监听传入连接
                        case BluetoothService.STATE_NONE:
                            break;
                    }
                    break;
                case SystemSettingActivity.MESSAGE_DEVICE_NAME:
                    break;
                case SystemSettingActivity.MESSAGE_TOAST:
                    break;
                case SystemSettingActivity.MESSAGE_UNABLE_CONNECT:     //无法连接设备
//                    AlertUtil.showToast("无法连接设备");
                case SystemSettingActivity.MESSAGE_CONNECTION_LOST:    //蓝牙已断开连接
//                    AlertUtil.showToast("蓝牙已断开连接");
                    isConnected = false;
                    if (resultListerner != null)
                        resultListerner.IsConnect(false);
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    break;
            }
        }
    };

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
                    for (BluetoothDeviceInfo bluetoothDevice : listData){
                        if (bluetoothDevice.getBluetoothName().equals(item.getBluetoothName())){
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
                    case BluetoothDevice.BOND_BONDED://配对成功
                        //保存已配对的地址
                        Config.BluetoothAddress = device.getAddress();
                        ConfigureParamSP.getInstance().saveValue(mContext, ConfigureParamSP.KEY_BLUETOOTHADDRESS, device.getAddress());
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

    public void printerTxt_58mm(String orderNo,List<GetClassPluResult> listDatas){
        try {
            Command.ESC_Align[2] = 0x01;
            SendDataByte(Command.ESC_Align);
            SendDataByte(Command.ESC_Align);
            Command.GS_ExclamationMark[2] = 0x11;
            SendDataByte(Command.GS_ExclamationMark);
            SendDataByte("专卖店\n".getBytes("GBK"));
            Command.ESC_Align[2] = 0x00;
            SendDataByte(Command.ESC_Align);
            Command.GS_ExclamationMark[2] = 0x00;
            SendDataByte(Command.GS_ExclamationMark);
            StringBuffer content = new StringBuffer();
            float qty = 0;
            float totalAllMoney = 0f;
            content.append("门店号: "+Config.posid+"\n单据  "+orderNo+"\n操作员："+Config.UserName+"\n");
            //SendDataByte(mes.getBytes("GBK"));
            content.append("品名       数量    单价    金额\n");
            for(int i=0; i<listDatas.size(); i++){
                GetClassPluResult mGetClassPluResult = listDatas.get(i);
                totalAllMoney += MyUtils.convertToFloat(mGetClassPluResult.getSale_price(),0f) *
                        MyUtils.convertToFloat(mGetClassPluResult.getSale_qnty(),0f);
                float totalMoney = MyUtils.convertToFloat(mGetClassPluResult.getSale_price(),0f) *
                        MyUtils.convertToFloat(mGetClassPluResult.getSale_qnty(),0f);
                qty += Integer.decode(mGetClassPluResult.getSale_qnty());
                content.append(mGetClassPluResult.getItem_name()+"   "+mGetClassPluResult.getSale_qnty()+"   "
                        +mGetClassPluResult.getSale_price()+"     "+totalMoney+"\n");
            }
//            SendDataByte(mes.getBytes("GBK"));
            content.append("数量：            "+qty+"\n总计：                "+totalAllMoney+"元\n");
//            SendDataByte(mes.getBytes("GBK"));
            content.append("公司名称：XXXXX\n公司网址：www.xxx.xxx\n地址：深圳市xx区xx号\n电话：0755-XXXXXXXX" +
                    "\n服务专线：400-xxx-xxxx\n================================\n");
            SendDataByte(content.toString().getBytes("GBK"));
            Command.ESC_Align[2] = 0x01;
            SendDataByte(Command.ESC_Align);
            Command.GS_ExclamationMark[2] = 0x11;
            SendDataByte(Command.GS_ExclamationMark);
            SendDataByte("谢谢惠顾,欢迎再次光临!\n".getBytes("GBK"));
            Command.ESC_Align[2] = 0x00;
            SendDataByte(Command.ESC_Align);
            Command.GS_ExclamationMark[2] = 0x00;
            SendDataByte(Command.GS_ExclamationMark);
            Command.ESC_Align[2] = 0x02;
            SendDataByte(Command.ESC_Align);
            SendDataString(DateUtility.getCurrentTime()+"\n\n\n");
            SendDataByte(PrinterCommand.POS_Set_PrtAndFeedPaper(1));
            SendDataByte(Command.GS_V_m_n);
        } catch (UnsupportedEncodingException e) {
            AlertUtil.showToast("打印异常，原因："+e.getMessage());
            e.printStackTrace();
        }
    }

    public void testPrinter() {
        String lang = mContext.getString(R.string.strLang);
        if ((lang.compareTo("cn")) == 0) {
            try {
                byte[] qrcode = PrinterCommand.getBarCommand("资江电子热敏票据打印机!", 0, 3, 8);
                Command.ESC_Align[2] = 0x01;
                SendDataByte(Command.ESC_Align);
                SendDataByte(qrcode);

                Command.ESC_Align[2] = 0x01;
                SendDataByte(Command.ESC_Align);
                Command.GS_ExclamationMark[2] = 0x11;
                SendDataByte(Command.GS_ExclamationMark);
                SendDataByte("NIKE专卖店\n".getBytes("GBK"));
                Command.ESC_Align[2] = 0x00;
                SendDataByte(Command.ESC_Align);
                Command.GS_ExclamationMark[2] = 0x00;
                SendDataByte(Command.GS_ExclamationMark);
                SendDataByte("门店号: 888888\n单据  S00003333\n收银员：1001\n单据日期：xxxx-xx-xx\n打印时间：xxxx-xx-xx  xx:xx:xx\n".getBytes("GBK"));
                SendDataByte("品名            数量    单价    金额\nNIKE跑鞋        10.00   899     8990\nNIKE篮球鞋      10.00   1599    15990\n".getBytes("GBK"));
                SendDataByte("数量：                20.00\n总计：                16889.00\n付款：                17000.00\n找零：                111.00\n".getBytes("GBK"));
                SendDataByte("公司名称：NIKE\n公司网址：www.xxx.xxx\n地址：深圳市xx区xx号\n电话：0755-11111111\n服务专线：400-xxx-xxxx\n===========================================\n".getBytes("GBK"));
                Command.ESC_Align[2] = 0x01;
                SendDataByte(Command.ESC_Align);
                Command.GS_ExclamationMark[2] = 0x11;
                SendDataByte(Command.GS_ExclamationMark);
                SendDataByte("谢谢惠顾,欢迎再次光临!\n".getBytes("GBK"));
                Command.ESC_Align[2] = 0x00;
                SendDataByte(Command.ESC_Align);
                Command.GS_ExclamationMark[2] = 0x00;
                SendDataByte(Command.GS_ExclamationMark);
                SendDataByte("(以上信息为测试模板,如有苟同，纯属巧合!)\n".getBytes("GBK"));
                Command.ESC_Align[2] = 0x02;
                SendDataByte(Command.ESC_Align);
                SendDataString(DateUtility.getCurrentTime()+"\n\n\n");
                SendDataByte(PrinterCommand.POS_Set_PrtAndFeedPaper(1));
                SendDataByte(Command.GS_V_m_n);
            } catch (UnsupportedEncodingException e) {
                AlertUtil.showToast("打印异常，原因："+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void SendDataByte(byte[] data) {
        if (mPrinterService.getState() != BluetoothService.STATE_CONNECTED) {
            AlertUtil.showToast(R.string.not_connected);
            return;
        }
        mPrinterService.write(data);
    }
    private void SendDataString(String data) {

        if (mPrinterService.getState() != BluetoothService.STATE_CONNECTED) {
            AlertUtil.showToast(R.string.not_connected);
            return;
        }
        if (data.length() > 0) {
            try {
                mPrinterService.write(data.getBytes("GBK"));
            } catch (UnsupportedEncodingException e) {
                AlertUtil.showToast("打印编号异常："+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public interface BluetoothResultListerner {
        void IsConnect(boolean isConnect);
        void SearchDevices(List<BluetoothDeviceInfo> mDevices);
        void IsPair(boolean isPair);
    }
}
