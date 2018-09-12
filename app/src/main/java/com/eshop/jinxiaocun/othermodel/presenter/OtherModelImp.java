package com.eshop.jinxiaocun.othermodel.presenter;

import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.netWork.httpDB.INetWork;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.netWork.httpDB.NetWorkImp;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBeanResult;
import com.eshop.jinxiaocun.othermodel.bean.SheetNoBeanResult;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.ReflectionUtils;
import com.eshop.jinxiaocun.utils.WebConfig;

import java.util.List;
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

    @Override
    public void getGoodsPiciInfo(BaseBean bean) {
        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new OtherModelImp.GoodsPiciInterface());
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
                if(status.equals(Config.MESSAGE_OK+"")){
                    SheetNoBeanResult resultItem=  mJsonFormatImp.JsonToBean(jsonData,SheetNoBeanResult.class);
                    mHandler.handleResule(Config.MESSAGE_SHEETNO_OK,resultItem);
                }else{
                    mHandler.handleResule(Config.MESSAGE_SHEETNO_ERROR,Msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.MESSAGE_SHEETNO_ERROR,e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //获取商品批次信息
    class GoodsPiciInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event, String result) {

        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {
            try {
                if(status.equals(Config.MESSAGE_OK+"")){
                    List<GoodsPiciInfoBeanResult> resultItem=  mJsonFormatImp.JsonToList(jsonData,GoodsPiciInfoBeanResult.class);
                    mHandler.handleResule(Config.RESULT_SUCCESS,resultItem);
                }else{
                    mHandler.handleResule(Config.RESULT_FAIL,Msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.RESULT_FAIL,e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
