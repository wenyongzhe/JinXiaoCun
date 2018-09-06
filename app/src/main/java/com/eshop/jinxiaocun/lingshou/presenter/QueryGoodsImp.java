package com.eshop.jinxiaocun.lingshou.presenter;

import android.util.Log;

import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.bean.QryClassResult;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.lingshou.bean.GetClassPluInfoBean;
import com.eshop.jinxiaocun.lingshou.bean.GetPluLikeInfoBean;
import com.eshop.jinxiaocun.netWork.httpDB.INetWork;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.netWork.httpDB.NetWorkImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.ReflectionUtils;
import com.eshop.jinxiaocun.utils.WebConfig;

import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class QueryGoodsImp implements IQueryGoods {

    private INetWorResult mHandler;
    private INetWork mINetWork;
    IJsonFormat mJsonFormatImp = new JsonFormatImp();

    public QueryGoodsImp(INetWorResult mHandler) {
        this.mHandler = mHandler;
        mINetWork = new NetWorkImp(Application.mContext);
    }

    @Override
    public void getPLULikeInfo(String searchstr, int pageNum) {
        GetPluLikeInfoBean mGetPluLikeInfoBean = new GetPluLikeInfoBean();
        mGetPluLikeInfoBean.getJsonData().setAs_branchNo(Config.branch_no);
        mGetPluLikeInfoBean.getJsonData().setAs_searchstr(searchstr);
        Map map = ReflectionUtils.obj2Map(mGetPluLikeInfoBean);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new GetPLULikeInfoInterface());
    }

    class GetPLULikeInfoInterface implements IResponseListener {
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
