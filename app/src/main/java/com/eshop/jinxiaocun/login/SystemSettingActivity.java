package com.eshop.jinxiaocun.login;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.BaseActivity;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.ConfigureParamSP;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.ActionBarClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SystemSettingActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.et_service_url)
    EditText txtSeverUrl;

    @BindView(R.id.et_shop_group)
    EditText txtShopGroup;

    @BindView(R.id.et_service_port)
    EditText txtSeverPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT);
        mLinearLayout.addView(getView(R.layout.activity_system_setting),-1,params);

        mMyActionBar.setData("系统设置", R.mipmap.icon_back, "", 0, "", new ActionBarClickListener() {
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
        txtShopGroup = findViewById(R.id.et_shop_group);

        txtSeverUrl.setText(Config.IP);
        txtSeverPort.setText(Config.IP_POIN);
        txtShopGroup.setText(Config.ShopGroup);

        Button btnTest = (Button)findViewById(R.id.btn_test);
        btnTest.setOnClickListener(this);

        closeEditTextKeyboard();
    }

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
                ConfigureParamSP.getInstance().saveValue(SystemSettingActivity.this, ConfigureParamSP.KEY_SHOPGROUP, txtShopGroup.getText().toString());

                Config.ShopGroup = txtShopGroup.getText().toString();
                Config.IP = txtSeverUrl.getText().toString();
                Config.IP_POIN = txtSeverPort.getText().toString();

                Config.httpURL = "http://"+Config.IP+":"+Config.IP_POIN+"/mssp/odsi/amso/service";

                MyUtils.showToast("参数保存成功！",SystemSettingActivity.this);
                break;
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