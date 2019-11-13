package com.eshop.jinxiaocun.login;

import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.DeviceUtils;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.login.Bean.LoginBean;
import com.eshop.jinxiaocun.login.Bean.LoginBeanResult;
import com.eshop.jinxiaocun.login.Bean.RegistBean;
import com.eshop.jinxiaocun.login.Bean.RegistBeanResult;
import com.eshop.jinxiaocun.netWork.httpDB.INetWork;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.netWork.httpDB.NetWorkImp;
import com.eshop.jinxiaocun.utils.ReflectionUtils;
import com.eshop.jinxiaocun.utils.WebConfig;
import com.eshop.jinxiaocun.utils.Config;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;


public class LoginImp implements ILogin {

    private INetWorResult mHandler;
    private INetWork mINetWork;
    JsonFormatImp mJsonFormatImp = new JsonFormatImp();

    public LoginImp(INetWorResult mHandler) {
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
        map.put("JsonData",jsonData);
        mHandler.handleResule(Config.SHOW_PROGRESS,null);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new LoginInterface());

    }

    @Override
    public void registDevice() {
        if(DeviceUtils.getMacAddress()==null || DeviceUtils.getMacAddress().equals("")){
            mHandler.handleResule(Config.MESSAGE_ERROR,"获取MAC地址失败");
            return;
        }
        RegistBean mRegistBean = new RegistBean();
        mRegistBean.getJsonData().setiDevID(Config.DeviceID + DeviceUtils.getMacAddress());
        Map map = ReflectionUtils.obj2Map(mRegistBean);
        mHandler.handleResule(Config.SHOW_PROGRESS,null);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new RegistInterface());
    }

    //登陆
    class LoginInterface implements IResponseListener{

        @Override
        public void handleError(Object event) {
            mHandler.handleResule(Config.DISS_PROGRESS,null);
            Log.e("-----",event.toString());
        }

        @Override
        public void handleResult(Response event,String result) {
            mHandler.handleResule(Config.DISS_PROGRESS,null);
            Log.e("-----",result);
        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {
            //Log.e("-----",jsonData);
            try{
                mHandler.handleResule(Config.DISS_PROGRESS,null);
                LoginBeanResult jsonBean = mJsonFormatImp.JsonToBean(jsonData, LoginBeanResult.class);
                if(status.equals(Config.MESSAGE_OK+"")){
                    if(jsonBean!=null){
                        if(jsonBean.getResult().equals("Y")){
                            mHandler.handleResule(Config.MESSAGE_INTENT,jsonBean);
                        }else{
                            mHandler.handleResule(Config.MESSAGE_ERROR,jsonBean.getMessage());
                        }
                    }else{
                        mHandler.handleResule(Config.MESSAGE_ERROR,"登录成功，但返回jsonData为空");
                    }

                }else{
                    mHandler.handleResule(Config.MESSAGE_ERROR,Msg);
                }
            }catch (Exception e){
                mHandler.handleResule(Config.MESSAGE_ERROR,e.toString());
            }

        }
    }

    //注册
    class RegistInterface implements IResponseListener{

        @Override
        public void handleError(Object event) {
            mHandler.handleResule(Config.DISS_PROGRESS,null);
        }

        @Override
        public void handleResult(Response event,String result) {
            mHandler.handleResule(Config.DISS_PROGRESS,null);
        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {

            try {
                mHandler.handleResule(Config.DISS_PROGRESS,null);

                RegistBeanResult.RegistJson jsonBean = mJsonFormatImp.JsonToBean(jsonData, RegistBeanResult.RegistJson.class);
                if( (!status.equals(Config.MESSAGE_OK+"") && jsonBean==null) ||
                        (jsonBean!=null && jsonBean.getResult().equals("N")) ){
                    /*Config.posid = "1001";
                    Config.branch_no = "000101";*/
                    Config.soft_name = "智能移动POS";
                    String errorMsg;
                    if(jsonBean==null){
                        errorMsg ="返回jsonData数据为空";
                    }else{
                        errorMsg = TextUtils.isEmpty(jsonBean.getMessage())?"未返回错误提示":jsonBean.getMessage();
                    }
                    mHandler.handleResule(Config.MESSAGE_ERROR,"注册失败: "+errorMsg);
                }else{
                    Config.posid = jsonBean.getPosid();
                    Config.branch_no = jsonBean.getBranch_no();
//                    Config.branch_no = "000001";
                    Config.soft_name = jsonBean.getSoft_name();
                    mHandler.handleResule(Config.MESSAGE_OK,jsonBean);
                }
            }catch (Exception e){
                mHandler.handleResule(Config.MESSAGE_ERROR,"注册失败,原因："+e.getMessage());
            }

        }
    }



}
