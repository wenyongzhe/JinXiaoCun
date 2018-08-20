package com.eshop.jinxiaocun.pifaxiaoshou.presenter;

import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.netWork.httpDB.INetWork;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.utils.WebConfig;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.GoodGetBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.GoodGetBeanResult;
import java.io.IOException;
import okhttp3.Response;

public class PiFaXiaoShouScanImp implements IXiaoShouScan {
    private INetWorResult mHandler;
    private INetWork mINetWork;
    IJsonFormat mJsonFormatImp = new JsonFormatImp();

    public PiFaXiaoShouScanImp(INetWorResult mHandler) {
        this.mHandler = mHandler;
    }

    @Override
    public void getPLUInfo(String barCode) {
        GoodGetBean mGoodGetBean = new GoodGetBean();
        mGoodGetBean.getJsonData().setAs_branchNo(Config.branch_no);
        mGoodGetBean.getJsonData().setAs_item_no(barCode);
        IJsonFormat mJsonFormatImp = new JsonFormatImp();
        String jsonData = mJsonFormatImp.ObjetToString(mGoodGetBean);

        mINetWork.doPost(WebConfig.getWsdlUri(),jsonData,new GetGoodInforInterface());
    }

    //查询商品信息
    class GetGoodInforInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event) {
            String result = "";
            try {
                result = event.body().string();
                GoodGetBeanResult mGoodGetBeanResult =  mJsonFormatImp.JsonToBean(result,GoodGetBeanResult.class);
                if(mGoodGetBeanResult.status.equals(Config.MESSAGE_OK+"")){
                    mHandler.handleResule(Config.MESSAGE_OK,mGoodGetBeanResult);
                }else{
                    mHandler.handleResule(Config.MESSAGE_ERROR,mGoodGetBeanResult);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
