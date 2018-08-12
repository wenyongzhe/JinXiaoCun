package com.eshop.jinxiaocun.xiaoshou.presenter;

import android.os.Handler;

import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.utils.WebConfig;
import com.eshop.jinxiaocun.netWork.WebService.WebServiceManager;
import com.eshop.jinxiaocun.thread.AsyncTaskThreadImp;
import com.eshop.jinxiaocun.thread.TaskInterface;
import com.eshop.jinxiaocun.thread.ThreadManagerInterface;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.xiaoshou.bean.GoodGetBean;
import com.eshop.jinxiaocun.xiaoshou.bean.GoodGetBeanResult;

import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class XiaoShouScanImp implements IXiaoShouScan {
    private Handler mHandler;

    @Override
    public void getPLUInfo(String barCode) {
        ThreadManagerInterface mThreadManagerInterface = new AsyncTaskThreadImp();
        mThreadManagerInterface.executeRunnable(new GetGoodInforInterface(barCode));
    }

    class  GetGoodInforInterface implements TaskInterface {

        private String barCode;
        private WebServiceManager webServiceManager;

        public GetGoodInforInterface(String barCode) {
            this.barCode = barCode;
            webServiceManager = new WebServiceManager();
        }

        @Override
        public Object doInBackground(Object[] objects) {
            try {

                GoodGetBean mGoodGetBean = new GoodGetBean();
                mGoodGetBean.setAs_branchNo(Config.branch_no);
                mGoodGetBean.setAs_item_no(barCode);
                IJsonFormat mJsonFormatImp = new JsonFormatImp();
                String jsonData = mJsonFormatImp.ObjetToString(mGoodGetBean);

                SoapObject mSoapObject = webServiceManager.action(WebConfig.getGetPLUInfo(),jsonData);
                String status = mSoapObject.getProperty(0).toString();
                if(status.equals(Config.MESSAGE_ERROR+"")){
                    return Config.MESSAGE_ERROR;
                }
                GoodGetBeanResult mGoodGetBeanResult = mJsonFormatImp.JsonToBean( mSoapObject.getProperty(2).toString(), GoodGetBeanResult.class);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return Config.MESSAGE_OK;
        }

        @Override
        public void onPostExecute(Object result) {
            if((int)result==Config.MESSAGE_OK){
                mHandler.sendEmptyMessage(Config.MESSAGE_OK);
            }else{
                mHandler.sendEmptyMessage(Config.MESSAGE_ERROR);
            }
        }
    }
}
