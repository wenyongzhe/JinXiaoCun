package com.eshop.jinxiaocun.login;

import android.os.Handler;
import android.util.Log;

import com.blankj.utilcode.util.DeviceUtils;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.login.Bean.LoginBean;
import com.eshop.jinxiaocun.login.Bean.RegistBean;
import com.eshop.jinxiaocun.login.Bean.RegistBeanResult;
import com.eshop.jinxiaocun.netWork.httpDB.INetWork;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.netWork.httpDB.NetWorkImp;
import com.eshop.jinxiaocun.utils.ReflectionUtils;
import com.eshop.jinxiaocun.utils.WebConfig;
import com.eshop.jinxiaocun.utils.Config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;


public class LoginImp implements ILogin {

    private Handler mHandler;
    private INetWork mINetWork;
    JsonFormatImp mJsonFormatImp = new JsonFormatImp();

    public LoginImp(Handler mHandler) {
        this.mHandler = mHandler;
        mINetWork = new NetWorkImp(Application.mContext);
    }

    @Override
    public void loginAction(String userName, String passWord) {
        LoginBean mLoginBean = new LoginBean();
        mLoginBean.getJsonData().setAs_branch_no(Config.branch_no);
        mLoginBean.getJsonData().setAs_operid(userName);
        mLoginBean.getJsonData().setAs_operpsw(passWord);

        JsonFormatImp mJsonFormatImp = new JsonFormatImp();
        String jsonData = mJsonFormatImp.ObjetToString(mLoginBean.getJsonData());


        Map<String,String> map = new HashMap();
        map.put("DevID",Config.DeviceID);
        map.put("SoftVer",Config.VersionCode+"");
        map.put("strCmd",WebConfig.getPosLogin());
        map.put("Sign","");
        map.put("JsonData",jsonData);

        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new LoginInterface());

    }

    @Override
    public void registDevice() {
        RegistBean mRegistBean = new RegistBean();
        mRegistBean.getJsonData().setiDevID(Config.DeviceID + DeviceUtils.getMacAddress());
        Map map = ReflectionUtils.obj2Map(mRegistBean);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new RegistInterface());
    }

    //登陆
    class LoginInterface implements IResponseListener{

        @Override
        public void handleError(Object event) {

        }

        @Override
        public void handleResult(Response event,String result) {
            Log.e("-----",result);
            mHandler.sendEmptyMessage(Config.MESSAGE_INTENT);
        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {
            Log.e("-----",jsonData);
        }
    }

    //注册
    class RegistInterface implements IResponseListener{

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event,String result) {
        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {
            RegistBeanResult.RegistJson jsonBean = mJsonFormatImp.JsonToBean(jsonData, RegistBeanResult.RegistJson.class);
            if(jsonBean==null){
                Config.posid = "123";
                Config.branch_no = "123";
                Config.soft_name = "123";
            }else{
                Config.posid = jsonBean.getPosid();
                Config.branch_no = jsonBean.getBranch_no();
                Config.soft_name = jsonBean.getSoft_name();
            }
            mHandler.sendEmptyMessage(Config.MESSAGE_OK);
        }
    }



}
