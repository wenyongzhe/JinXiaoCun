package com.eshop.jinxiaocun.othermodel.presenter;

import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.netWork.httpDB.INetWork;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.netWork.httpDB.NetWorkImp;
import com.eshop.jinxiaocun.othermodel.bean.SheetNoBeanResult;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.ReflectionUtils;
import com.eshop.jinxiaocun.utils.WebConfig;

import java.util.Map;

import okhttp3.Response;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/5
 * Desc:
 */

public class OtherModelImp implements IOtherModel {


    private INetWorResult mHandler;
    private INetWork mINetWork;
    private IJsonFormat mJsonFormatImp = new JsonFormatImp();

    public OtherModelImp(INetWorResult handler) {
        this.mHandler = handler;
        mINetWork = new NetWorkImp(Application.mContext);
    }

    @Override
    public void getSheetNoData(BaseBean bean) {
        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new OtherModelImp.SheetNoInterface());
    }


    //获取业务单据号
    class SheetNoInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event, String result) {

        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {
            try {
                SheetNoBeanResult resultItem=  mJsonFormatImp.JsonToBean(jsonData,SheetNoBeanResult.class);
                if(status.equals(Config.MESSAGE_OK+"")){
                    mHandler.handleResule(Config.MESSAGE_OK,resultItem);
                }else{
                    mHandler.handleResule(Config.MESSAGE_ERROR,Msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.MESSAGE_ERROR,e.getMessage());
                e.printStackTrace();
            }
        }
    }


}
