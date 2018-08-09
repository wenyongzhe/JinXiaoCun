package com.eshop.jinxiaocun.login;

import android.os.Handler;
import android.util.Log;

import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.login.Bean.LoginBean;
import com.eshop.jinxiaocun.login.Bean.LoginBeanResult;
import com.eshop.jinxiaocun.netWork.WebService.WebConfig;
import com.eshop.jinxiaocun.netWork.WebService.WebServiceManager;
import com.eshop.jinxiaocun.thread.AsyncTaskThreadImp;
import com.eshop.jinxiaocun.thread.TaskInterface;
import com.eshop.jinxiaocun.thread.ThreadManagerInterface;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.GsonUtil;

import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class LoginImp implements ILogin {

    private Handler mHandler;
    private WebServiceManager webServiceManager;


    public LoginImp(Handler mHandler) {
        this.mHandler = mHandler;
        webServiceManager = new WebServiceManager();
    }

    @Override
    public void loginAction(String userName, String passWord) {
        ThreadManagerInterface mThreadManagerInterface = new AsyncTaskThreadImp();
        mThreadManagerInterface.executeRunnable(new LoginTaskInterface(userName, passWord));
    }

    class  LoginTaskInterface implements TaskInterface{
        private String userName;
        private String passWord;

        public LoginTaskInterface(String userName, String passWord) {
            this.userName = userName;
            this.passWord = passWord;
        }

        @Override
        public Object doInBackground(Object[] objects) {
            try {
                LoginBean mLoginBean = new LoginBean();
                mLoginBean.setAs_branch_no(Config.ShopGroup);
                mLoginBean.setAs_operid(userName);
                mLoginBean.setAs_operpsw(passWord);
                String jsonData = GsonUtil.GsonString(mLoginBean);

                SoapObject mSoapObject = webServiceManager.action(WebConfig.getLoginCmd(),jsonData);
                String msg = mSoapObject.getProperty(0).toString();
                JsonFormatImp mJsonFormatImp = new JsonFormatImp();
                mJsonFormatImp.JsonToBean( mSoapObject.getProperty(2).toString(), LoginBeanResult.class);

                Log.e("","---------------"+msg);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(Object o) {
            mHandler.sendEmptyMessage(1);
        }
    }


}
