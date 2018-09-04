package com.eshop.jinxiaocun.lingshou.presenter;

import android.util.Log;

import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.bean.QryClassResult;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.lingshou.bean.GetClassPluInfoBean;
import com.eshop.jinxiaocun.lingshou.bean.QryClassInfoBean;
import com.eshop.jinxiaocun.netWork.httpDB.INetWork;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.netWork.httpDB.NetWorkImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.ReflectionUtils;
import com.eshop.jinxiaocun.utils.WebConfig;

import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class SelectGoodsImp implements ISelectGoods{
    private INetWorResult mHandler;
    private INetWork mINetWork;
    IJsonFormat mJsonFormatImp = new JsonFormatImp();

    public SelectGoodsImp(INetWorResult mHandler) {
        this.mHandler = mHandler;
        mINetWork = new NetWorkImp(Application.mContext);
    }

    @Override
    public void qryClassInfo() {
        QryClassInfoBean mQryClassInfoBean = new QryClassInfoBean();
        mQryClassInfoBean.getJsonData().setAs_branchNo(Config.branch_no);
        mQryClassInfoBean.getJsonData().setAs_posId(Config.posid);
        mQryClassInfoBean.getJsonData().setAs_clsorbrno("");
        mQryClassInfoBean.getJsonData().setAs_type("1");
        Map map = ReflectionUtils.obj2Map(mQryClassInfoBean);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new QryClassInfoInterface());
    }

    @Override
    public void getClassPluInfo(String as_cls, int pageNum) {
        GetClassPluInfoBean mGetClassPluInfoBean = new GetClassPluInfoBean();
        mGetClassPluInfoBean.getJsonData().setAs_branchNo(Config.branch_no);
        mGetClassPluInfoBean.getJsonData().setAs_posId(Config.posid);
        mGetClassPluInfoBean.getJsonData().setAs_cls(as_cls);
        mGetClassPluInfoBean.getJsonData().setPerNum("50");
        mGetClassPluInfoBean.getJsonData().setPageNum(""+pageNum);
        Map map = ReflectionUtils.obj2Map(mGetClassPluInfoBean);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new GetClassPluInfoInterface());
    }

    class QryClassInfoInterface implements IResponseListener {
        @Override
        public void handleError(Object event) {
            Log.e("error", event.toString());
        }
        @Override
        public void handleResult(Response event, String result) {
        }
        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {
            List<QryClassResult> mQryClassInfoBeanResult =  mJsonFormatImp.JsonToList(jsonData,QryClassResult.class);
            if(status.equals(Config.MESSAGE_OK+"")){
                mHandler.handleResule(Config.MESSAGE_QRYCLASSINFO,mQryClassInfoBeanResult);
            }else{
                mHandler.handleResule(Config.MESSAGE_ERROR,mQryClassInfoBeanResult);
            }
        }
    }

    class GetClassPluInfoInterface implements IResponseListener {
        @Override
        public void handleError(Object event) {
            Log.e("error", event.toString());
        }
        @Override
        public void handleResult(Response event, String result) {
        }
        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {
            List<GetClassPluResult> mGetClassPluResult =  mJsonFormatImp.JsonToList(jsonData,GetClassPluResult.class);
            if(status.equals(Config.MESSAGE_OK+"")){
                mHandler.handleResule(Config.MESSAGE_GETCLASSPLUINFO,mGetClassPluResult);
            }else{
                mHandler.handleResule(Config.MESSAGE_ERROR,mGetClassPluResult);
            }
        }
    }
}
