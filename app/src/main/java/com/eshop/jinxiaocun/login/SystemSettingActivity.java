package com.eshop.jinxiaocun.login;

import android.annotation.SuppressLint;
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
import com.eshop.jinxiaocun.bluetoothprinter.entity.BluetoothPrinterManage;
import com.eshop.jinxiaocun.bluetoothprinter.view.SettingBluetoothActivity;
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

    @BindView(R.id.et_service_port)
    EditText txtSeverPort;

    @BindView(R.id.btn_blue)
    Button btn_blue;

    public static final int REQUEST_CONNECT_DEVICE = 1;
    public static final int REQUEST_ENABLE_BT = 2;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_CONNECTION_LOST = 6;
    public static final int MESSAGE_UNABLE_CONNECT = 7;
    public static final String TOAST = "toast";
    public static final String DEVICE_NAME = "device_name";

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

        txtSeverUrl = (EditText) findViewById(R.id.et_service_url);
        txtSeverPort = (EditText) findViewById(R.id.et_service_port);
//        txtShopGroup = findViewById(R.id.et_shop_group);

        txtSeverUrl.setText(Config.IP);
        txtSeverPort.setText(Config.IP_POIN);
//        txtShopGroup.setText(Config.ShopGroup);

        Button btnTest = (Button)findViewById(R.id.btn_test);
        Button btn_blue = (Button)findViewById(R.id.btn_blue);
        btnTest.setOnClickListener(this);
        btn_blue.setOnClickListener(this);

        closeEditTextKeyboard();



    }

    @Override
    protected void loadData() {
    }

    @Override
    protected void initView() {
    }

    private void closeEditTextKeyboard() {
        //MyUtils.closeKeyboard(this, txtSeverUrl);
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

                MyUtils.showToast("参数保存成功！",SystemSettingActivity.this);
                break;

            case R.id.btn_blue:
                Intent serverIntent = new Intent(SystemSettingActivity.this, SettingBluetoothActivity.class);
                startActivity(serverIntent);
                break;
        }
    }

}