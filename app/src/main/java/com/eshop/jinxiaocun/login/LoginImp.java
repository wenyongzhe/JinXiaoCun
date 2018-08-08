package com.eshop.jinxiaocun.login;

import android.os.Handler;
import android.util.Log;

import com.eshop.jinxiaocun.netWork.WebService.WebServiceManager;
import com.eshop.jinxiaocun.thread.AsyncTaskThreadImp;
import com.eshop.jinxiaocun.thread.TaskInterface;
import com.eshop.jinxiaocun.thread.ThreadManagerInterface;

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
                SoapObject mSoapObject = webServiceManager.login(userName,passWord);
                String msg = mSoapObject.getProperty(0).toString();
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
