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
import com.eshop.jinxiaocun.utils.WebConfig;
import com.eshop.jinxiaocun.utils.Config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;


public class LoginImp implements ILogin {

    private Handler mHandler;
    private INetWork mINetWork;


    public LoginImp(Handler mHandler) {
        this.mHandler = mHandler;
        mINetWork = new NetWorkImp(Application.mContext);
    }

    @Override
    public void loginAction(String userName, String passWord) {
        LoginBean mLoginBean = new LoginBean();
        mLoginBean.getJsonData().setAs_branch_no(Config.ShopGroup);
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
        String jsonData ="｛\"iDevID\":" + Config.DeviceID + DeviceUtils.getMacAddress() + "}";
        mRegistBean.setStrCmd(WebConfig.getPosReg());
        mRegistBean.setJsonData(jsonData);
        mINetWork.doPost(WebConfig.getPostWsdlUri(),jsonData,new RegistInterface());
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
    }

    //注册
    class RegistInterface implements IResponseListener{

        @Override
        public void handleError(Object event) {

        }

        @Override
        public void handleResult(Response event,String result) {
            JsonFormatImp mJsonFormatImp = new JsonFormatImp();
            RegistBeanResult mRegistBeanResult = null;
            mRegistBeanResult = mJsonFormatImp.JsonToBean(result, RegistBeanResult.class);
            Config.posid = mRegistBeanResult.getPosid();
            Config.branch_no = mRegistBeanResult.getBranch_no();
            Config.soft_name = mRegistBeanResult.getSoft_name();
        }
    }



}
