package com.eshop.jinxiaocun.lingshou.presenter;

import android.util.Log;

import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.lingshou.bean.GetFlowNoBean;
import com.eshop.jinxiaocun.lingshou.bean.GetFlowNoBeanResult;
import com.eshop.jinxiaocun.lingshou.bean.QryClassInfoBean;
import com.eshop.jinxiaocun.netWork.httpDB.INetWork;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.netWork.httpDB.NetWorkImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.ReflectionUtils;
import com.eshop.jinxiaocun.utils.WebConfig;

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
    public void getClassPluInfo(int pageNum) {

    }

    class QryClassInfoInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
            Log.e("error", event.toString());
        }

        @Override
        public void handleResult(Response event, String result) {
            GetFlowNoBeanResult mGetFlowNoBeanResult =  mJsonFormatImp.JsonToBean(result,GetFlowNoBeanResult.class);
            if(mGetFlowNoBeanResult.status.equals(Config.MESSAGE_OK+"")){
                mHandler.handleResule(Config.MESSAGE_FLOW_NO,mGetFlowNoBeanResult);
            }else{
                mHandler.handleResule(Config.MESSAGE_ERROR,mGetFlowNoBeanResult);
            }
        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {

        }
    }
}
