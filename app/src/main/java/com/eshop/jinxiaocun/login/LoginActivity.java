package com.eshop.jinxiaocun.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.BaseActivity;
import com.eshop.jinxiaocun.main.view.MainActivity;
import com.eshop.jinxiaocun.utils.CommonUtility;
import com.eshop.jinxiaocun.utils.Config;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_user_code)
    EditText editUser;

    @BindView(R.id.et_pwd)
    EditText editPassword;

    @BindView(R.id.tv_vision)
    TextView tv_version;

    @BindView(R.id.tv_deviceid)
    TextView tv_deviceid;

    @BindView(R.id.btn_login)
    Button btn_login;

    Button btn_systemset;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        tv_version = findViewById(R.id.tv_vision);
        tv_deviceid = findViewById(R.id.tv_deviceid);
        editUser = (EditText) findViewById(R.id.et_user_code);
        editPassword = (EditText) findViewById(R.id.et_pwd);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_systemset = (Button) findViewById(R.id.btn_systemset);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnLogin();
            }
        });
        btn_systemset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnSysemSet();
            }
        });
        tv_version.setText(CommonUtility.getInstance().GetAppVersion(this));
        tv_deviceid.setText(Config.DeviceID);

        editUser.setText(Config.UserCode);
        if (!editUser.getText().toString().equals(""))
            editPassword.requestFocus();

        //测试时设置默认密码
        editUser.setText("9999");
        editPassword.setText("111111");
        editPassword.setSelection(editPassword.length());


        closeEditTextKeyboard();
        HomeProhibit();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {

    }

    private void closeEditTextKeyboard() {
        CommonUtility.getInstance().closeKeyboard(this, editUser);
        CommonUtility.getInstance().closeKeyboard(this, editPassword);
    }



    @OnClick(R.id.btn_login)
    public void OnLogin() {
//        try {
//            if (!Build.MANUFACTURER.equals("SUPOIN")){
//
//                MyUtils.showToast("机器不合法，请使用销邦设备！", this);
//                return;
//            }
//
//            if (!CommonUtility.getInstance().isConnectingToInternet(LoginActivity.this))
//            {
//                MyUtils.showToast("您的网络有问题，请检查网络连接！",LoginActivity.this);
//                return;
//            }
//
//            if (editUser.getText().toString().trim().length() == 0) {
//                MyUtils.showToast("用户名不能为空", this);
//                return;
//            }
//            if (TextUtils.isEmpty(ConfigureParam.ShopGroup)) {
//                MyUtils.showToast("请先输入门店组", this);
//                Intent intent = new Intent();
//                intent.setClass(LoginActivity.this, SystemSettingActivity.class);
//                startActivity(intent);
//                return;
//            }
//            progressDialog = MyUtils.showNoButtonProgressDialog(LoginActivity.this, "正在登录...");
//            OkHttpUtils.login(editUser.getText().toString().trim(),editPassword.getText().toString().trim(),new GsonObjectCallback<LoginResponseEntity>() {
//                @Override
//                public void onUi(LoginResponseEntity responseEntity) throws Exception {
//                    if(responseEntity.getRetCode().equals("0000")){
//                        if(responseEntity.getData()!=null){
//                            MyUtils.showToast("登录成功！", LoginActivity.this);
//
                            Intent intent = new Intent();
//                            Config.UserCode = editUser.getText().toString().trim();
//                            Config.listStore = responseEntity.getData().getUsr().getDataRange();
                            intent.setClass(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
//                        }
//                    }else{
//                        MyUtils.showToast("登录失败！原因："+responseEntity.getRetMsg(), LoginActivity.this);
//                    }
//                    progressDialog.dismiss();
//                }
//                @Override
//                public void onFailed(Call call, IOException e) {
//                    MyUtils.showToast("登录失败！原因："+e.getMessage(), LoginActivity.this);
//                    progressDialog.dismiss();
//                }
//            });
//
//
//        }
//        catch (Exception ex){
//            MyUtils.showToast("登录失败！原因：" + ex.getMessage(), this);
//        }
    }

    @OnClick(R.id.btn_systemset)
    public void OnSysemSet(){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, SystemSettingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_softkey)
    public void OnSoftKey(){
        showSoftKeyboard();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

//        if (KeyEvent.KEYCODE_BACK == keyCode) {
//            Intent intent = new Intent();
//            intent.setClass(LoginActivity.this, PasswordInputActivity.class);
//            startActivityForResult(intent, 1);
//            return false;
//        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == 1)
        {
            //需要密码验证才能退出系统
            HomeProhibit();
            finish();
            System.exit(0);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void HomeProhibit() {
        Intent intent2 = new Intent("com.geenk.action.HOMEKEY_SWITCH_STATE");
        intent2.putExtra("enable", true);
        getApplicationContext().sendBroadcast(intent2);
    }
}
