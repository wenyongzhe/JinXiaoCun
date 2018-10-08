package com.eshop.jinxiaocun.login;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.BaseActivity;
import com.eshop.jinxiaocun.zjPrinter.BluetoothService;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.ConfigureParamSP;
import com.eshop.jinxiaocun.zjPrinter.DeviceListActivity;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.ActionBarClickListener;

import butterknife.BindView;

import static com.eshop.jinxiaocun.BuildConfig.DEBUG;

public class SystemSettingActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.et_service_url)
    EditText txtSeverUrl;

//    @BindView(R.id.et_shop_group)
//    EditText txtShopGroup;

    @BindView(R.id.et_service_port)
    EditText txtSeverPort;

    @BindView(R.id.btn_blue)
    Button btn_blue;

    TextView tv_blu_status;

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private BluetoothAdapter mBluetoothAdapter = null;
    // Message types sent from the BluetoothService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_CONNECTION_LOST = 6;
    public static final int MESSAGE_UNABLE_CONNECT = 7;
    public static final String TOAST = "toast";
    // Key names received from the BluetoothService Handler
    public static final String DEVICE_NAME = "device_name";

    // Member object for the services
    private BluetoothService mService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT);
        mLinearLayout.addView(getView(R.layout.activity_system_setting),-1,params);

        mMyActionBar.setData("系统设置", R.mipmap.ic_left_light, "", 0, "", new ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
//        ButterKnife.bind(this);

        txtSeverUrl = findViewById(R.id.et_service_url);
        txtSeverPort = findViewById(R.id.et_service_port);
//        txtShopGroup = findViewById(R.id.et_shop_group);

        txtSeverUrl.setText(Config.IP);
        txtSeverPort.setText(Config.IP_POIN);
//        txtShopGroup.setText(Config.ShopGroup);

        Button btnTest = (Button)findViewById(R.id.btn_test);
        Button btn_blue = (Button)findViewById(R.id.btn_blue);
        tv_blu_status = (TextView) findViewById(R.id.tv_blu_status);
        btnTest.setOnClickListener(this);
        btn_blue.setOnClickListener(this);

        closeEditTextKeyboard();

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mService = new BluetoothService(this, mHandler);

        btn_blue.setClickable(true);
        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available",
                    Toast.LENGTH_LONG).show();
            btn_blue.setClickable(false);
        }

    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            tv_blu_status.setText("连接中~~~");
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            tv_blu_status.setText("没有连接");
                            break;
                    }
                    break;
                case MESSAGE_WRITE:

                    break;
                case MESSAGE_READ:

                    break;
                case MESSAGE_DEVICE_NAME:
                    break;
                case MESSAGE_UNABLE_CONNECT:     //无法连接设备
                    Toast.makeText(getApplicationContext(), "Unable to connect device",
                            Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_CONNECTION_LOST:    //蓝牙已断开连接
                    Toast.makeText(getApplicationContext(), "Device connection was lost",
                            Toast.LENGTH_SHORT).show();

            }
        }
    };

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {

    }

    private void closeEditTextKeyboard() {
        MyUtils.closeKeyboard(this, txtSeverUrl);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_test:

                ConfigureParamSP.getInstance().saveValue(SystemSettingActivity.this, ConfigureParamSP.KEY_SERVERURL, txtSeverUrl.getText().toString());
                ConfigureParamSP.getInstance().saveValue(SystemSettingActivity.this, ConfigureParamSP.KEY_SERVERPORT, txtSeverPort.getText().toString());
//                ConfigureParamSP.getInstance().saveValue(SystemSettingActivity.this, ConfigureParamSP.KEY_SHOPGROUP, txtShopGroup.getText().toString());

//                Config.ShopGroup = txtShopGroup.getText().toString();
                Config.IP = txtSeverUrl.getText().toString();
                Config.IP_POIN = txtSeverPort.getText().toString();

                Config.httpURL = "http://"+Config.IP+":"+Config.IP_POIN+"/mssp/odsi/amso/service";

                MyUtils.showToast("参数保存成功！",SystemSettingActivity.this);
                break;

            case R.id.btn_blue:
                Intent serverIntent = new Intent(SystemSettingActivity.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (DEBUG)
            Log.d("", "onActivityResult " + resultCode);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE: {
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras().getString(
                            DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // Get the BLuetoothDevice object
                    if (BluetoothAdapter.checkBluetoothAddress(address)) {
                        BluetoothDevice device = mBluetoothAdapter
                                .getRemoteDevice(address);
                        // Attempt to connect to the device
                        mService.connect(device);
                    }
                }
                break;
            }
        }
    }

//    private class TestServer extends AsyncTask<String, String, Void>
//    {
//        @Override
//        protected Void doInBackground(String... params) {
//            try {
//
//                ConfigureParam.WebURL = params[0];
//                T_ResultMsg msg = soapService.Test();
//                if (msg.getStatusCode().equals("0"))
//                {
//                    publishProgress("true");
//                    ConfigureParamSP.getInstance().saveValue(SystemSettingActivity.this, ConfigureParamSP.KEY_SERVERURL, params[0]);
//                }
//                else{
//                    publishProgress("测试失败");
//                }
//            } catch (Exception ex) {
//                publishProgress("快鱼服务器地址["+ params[0] +"]不通，请查询服务地址和端口号是否正确");
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            progressDialog = AlertUtil.showNoButtonProgressDialog(SystemSettingActivity.this,"正在测试");
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onProgressUpdate(String... values) {
//            progressDialog.dismiss();
//            if (values[0].equals("true")) {
//                AlertUtil.showToast("测试成功",SystemSettingActivity.this);
//            }
//            else
//                AlertUtil.showToast("测试失败！原因："+values[0],SystemSettingActivity.this);
//            super.onProgressUpdate(values);
//        }
//    }
}