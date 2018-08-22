package com.eshop.jinxiaocun.lingshou.presenter;

import android.util.Log;

import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.base.bean.BillType;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.lingshou.bean.GetFlowNoBean;
import com.eshop.jinxiaocun.lingshou.bean.GetFlowNoBeanResult;
import com.eshop.jinxiaocun.netWork.httpDB.INetWork;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.netWork.httpDB.NetWorkImp;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuDetailBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuDetailBeanResult;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.GoodGetBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.GoodGetBeanResult;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.IXiaoShouScan;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.WebConfig;

import java.io.IOException;

import okhttp3.Response;

public class LingShouScanImp implements ILingshouScan {
    private INetWorResult mHandler;
    private INetWork mINetWork;
    IJsonFormat mJsonFormatImp = new JsonFormatImp();

    public LingShouScanImp(INetWorResult mHandler) {
        this.mHandler = mHandler;
        mINetWork = new NetWorkImp(Application.mContext);
    }

    @Override
    public void getFlowNo() {
        GetFlowNoBean mGetFlowNoBean = new GetFlowNoBean();
        mGetFlowNoBean.getJsonData().setBranchNo(Config.branch_no);
        mGetFlowNoBean.getJsonData().setPosId(Config.posid);
        String jsonData = mJsonFormatImp.ObjetToString(mGetFlowNoBean);
        mINetWork.doPost(WebConfig.getWsdlUri(),jsonData,new GetFlowNoInterface());
    }

    @Override
    public void getPLUInfo(String barCode) {
        GoodGetBean mGoodGetBean = new GoodGetBean();
        mGoodGetBean.getJsonData().setAs_branchNo(Config.branch_no);
        mGoodGetBean.getJsonData().setAs_item_no(barCode);
        String jsonData = mJsonFormatImp.ObjetToString(mGoodGetBean);

        mINetWork.doPost(WebConfig.getWsdlUri(),jsonData,new GetGoodInforInterface());
    }

    @Override
    public void getSheetDetail(String sheetNo) {
        DanJuDetailBean mDanJuDetailBean = new DanJuDetailBean();
        DanJuDetailBean.DanJuJsonData mDanJuJsonData = mDanJuDetailBean.getJsonData();
        mDanJuJsonData.setUserId(Config.DeviceID);
        mDanJuJsonData.setSheetType(BillType.SO+"");
        mDanJuJsonData.setSheetNo(sheetNo);
        mDanJuJsonData.setBranchNo("");//源仓库机构
        mDanJuJsonData.setTBranchNo("");//目标仓库机构

        IJsonFormat mJsonFormatImp = new JsonFormatImp();
        String jsonData = mJsonFormatImp.ObjetToString(mDanJuDetailBean);

        mINetWork.doPost(WebConfig.getWsdlUri(),jsonData,new GetGoodDetailInterface());
    }

    //获取流水
    class GetFlowNoInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
            Log.e("error", event.toString());
        }

        @Override
        public void handleResult(Response event) {
            String result = "";
            try {
                result = event.body().string();
                GetFlowNoBeanResult mGetFlowNoBeanResult =  mJsonFormatImp.JsonToBean(result,GetFlowNoBeanResult.class);
                if(mGetFlowNoBeanResult.status.equals(Config.MESSAGE_OK+"")){
                    mHandler.handleResule(Config.MESSAGE_SHEET_DETAIL,mGetFlowNoBeanResult);
                }else{
                    mHandler.handleResule(Config.MESSAGE_ERROR,mGetFlowNoBeanResult);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //获取订单明细
    class GetGoodDetailInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event) {
            String result = "";
            try {
                result = event.body().string();
                DanJuDetailBeanResult mDanJuDetailBeanResult =  mJsonFormatImp.JsonToBean(result,DanJuDetailBeanResult.class);
                if(mDanJuDetailBeanResult.status.equals(Config.MESSAGE_OK+"")){
                    mHandler.handleResule(Config.MESSAGE_SHEET_DETAIL,mDanJuDetailBeanResult);
                }else{
                    mHandler.handleResule(Config.MESSAGE_ERROR,mDanJuDetailBeanResult);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                    mHandler.handleResule(Config.MESSAGE_GOODS_INFOR,mGoodGetBeanResult);
                }else{
                    mHandler.handleResule(Config.MESSAGE_ERROR,mGoodGetBeanResult);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
