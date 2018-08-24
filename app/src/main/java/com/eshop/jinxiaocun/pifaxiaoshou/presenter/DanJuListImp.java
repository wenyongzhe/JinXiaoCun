package com.eshop.jinxiaocun.pifaxiaoshou.presenter;

import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.netWork.httpDB.INetWork;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.netWork.httpDB.NetWorkImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.WebConfig;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResult;
import okhttp3.Response;

public class DanJuListImp implements IDanJuList {

    private INetWorResult mHandler;
    private INetWork mINetWork;
    IJsonFormat mJsonFormatImp = new JsonFormatImp();

    public DanJuListImp(INetWorResult mHandler) {
        this.mHandler = mHandler;
        mINetWork = new NetWorkImp(Application.mContext);
    }


    @Override
    public void getDanJuList(BaseBean bean) {
        DanJuMainBean mDanJuMainBean = (DanJuMainBean) bean;
        IJsonFormat mJsonFormatImp = new JsonFormatImp();
        String jsonData = mJsonFormatImp.ObjetToString(mDanJuMainBean);

        mINetWork.doPost(WebConfig.getPostWsdlUri(),jsonData,new DanJuListInterface());
    }

    //查询商品列表
    class DanJuListInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event, String result) {
            DanJuMainBeanResult mDanJuMainBeanResult = null;
            try {
                mDanJuMainBeanResult =  mJsonFormatImp.JsonToBean(result,DanJuMainBeanResult.class);
                if(mDanJuMainBeanResult.status.equals(Config.MESSAGE_OK+"")){
                    mHandler.handleResule(Config.MESSAGE_OK,mDanJuMainBeanResult);
                }else{
                    mHandler.handleResule(Config.MESSAGE_ERROR,mDanJuMainBeanResult);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.MESSAGE_ERROR,mDanJuMainBeanResult);
                e.printStackTrace();
            }
        }
    }

}
