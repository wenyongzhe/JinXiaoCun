package com.eshop.jinxiaocun.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.BaseActivity;
import com.eshop.jinxiaocun.login.Bean.LoginBeanResult;
import com.eshop.jinxiaocun.main.view.MainActivity;
import com.eshop.jinxiaocun.utils.CommonUtility;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.MyUtils;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements INetWorResult {

    @BindView(R.id.et_user_code)
    EditText editUser;

    @BindView(R.id.et_pwd)
    EditText editPassword;

    @BindView(R.id.cb_show_psw)
    CheckBox mCbShowPSW;

    @BindView(R.id.tv_vision)
    TextView tv_version;

    @BindView(R.id.tv_deviceid)
    TextView tv_deviceid;

    @BindView(R.id.btn_login)
    Button btn_login;

    @BindView(R.id.btn_regit)
    Button btn_regit;

    @BindView(R.id.btn_systemset)
    Button btn_systemset;

    ProgressDialog progressDialog;
    ILogin loginAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnRegist();
            }
        });
        btn_systemset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnSysemSet();
            }
        });
        btn_regit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnRegist();
            }
        });

        tv_version.setText(CommonUtility.getInstance().GetAppVersion(this));
        tv_deviceid.setText(Config.DeviceID);

        editUser.setText(Config.UserCode);
        if (!editUser.getText().toString().equals(""))
            editPassword.requestFocus();

        //测试时设置默认密码
        editUser.setText("1001");
        editPassword.setText("1001");
        editPassword.setSelection(editPassword.length());

        mCbShowPSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    //如果选中，显示密码
                    editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }

            }
        });

        loginAction = new LoginImp(this);

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

    public void OnLogin() {
        try {
            if (!CommonUtility.getInstance().isConnectingToInternet(LoginActivity.this)) {
                MyUtils.showToast("您的网络有问题，请检查网络连接！", LoginActivity.this);
                return;
            }

            if (editUser.getText().toString().trim().length() == 0) {
                MyUtils.showToast("用户名不能为空", this);
                return;
            }
            if (TextUtils.isEmpty(Config.branch_no)) {
                MyUtils.showToast("请先绑定设备", this);
                return;
               /* Intent intent = new Intent();
                intent.setClass(LoginActivity.this, SystemSettingActivity.class);
                startActivity(intent);*/
//                return;
            }

            loginAction.loginAction(editUser.getText().toString().trim(),editPassword.getText().toString().trim());
        }
        catch (Exception ex){
            MyUtils.showToast("登录失败！原因：" + ex.getMessage(), this);
        }
    }

    @OnClick(R.id.btn_systemset)
    public void OnSysemSet(){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, SystemSettingActivity.class);
        startActivity(intent);
    }

    public void OnRegist(){
        loginAction.registDevice();
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

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){
            case Config.MESSAGE_OK:
                //ToastUtils.showLong("注册成功！");
                OnLogin();
                break;
            case Config.MESSAGE_ERROR:
                ToastUtils.showLong(o.toString());
                OnLogin();
                break;
            case Config.MESSAGE_INTENT:
                LoginBeanResult mGetFlowNoBeanResult = (LoginBeanResult)o;
                Config.intValue = mGetFlowNoBeanResult.getIntValue();
                Config.strgrant = mGetFlowNoBeanResult.getStrgrant();
                Config.UserName = editUser.getText().toString().trim();
                Config.UserId = editUser.getText().toString().trim();
                Config.PassWord = editPassword.getText().toString().trim();
                //ToastUtils.showLong("登录成功！");
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
