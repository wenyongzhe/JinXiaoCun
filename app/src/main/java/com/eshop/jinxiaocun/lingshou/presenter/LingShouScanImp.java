package com.eshop.jinxiaocun.lingshou.presenter;

import android.database.Cursor;
import android.util.Log;

import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.base.bean.BillType;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.lingshou.bean.GetFlowNoBean;
import com.eshop.jinxiaocun.lingshou.bean.GetFlowNoBeanResult;
import com.eshop.jinxiaocun.lingshou.bean.GetPluPriceBean;
import com.eshop.jinxiaocun.lingshou.bean.GetPluPriceBeanResult;
import com.eshop.jinxiaocun.lingshou.bean.SellSubBean;
import com.eshop.jinxiaocun.lingshou.bean.SellSubBeanResult;
import com.eshop.jinxiaocun.netWork.httpDB.INetWork;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.netWork.httpDB.NetWorkImp;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuDetailBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuDetailBeanResult;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.GoodGetBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.GoodGetBeanResult;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.PluLikeBean;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.IXiaoShouScan;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.ReflectionUtils;
import com.eshop.jinxiaocun.utils.WebConfig;

import java.util.Map;

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
        Map map = ReflectionUtils.obj2Map(mGetFlowNoBean);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new GetFlowNoInterface());
    }


    @Override
    public void getPLUInfo(String barCode) {
        GoodGetBean mGoodGetBean = new GoodGetBean();
        mGoodGetBean.getJsonData().setAs_branchNo(Config.branch_no);
        mGoodGetBean.getJsonData().setAs_item_no(barCode);
        Map map = ReflectionUtils.obj2Map(mGoodGetBean);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new GetGoodInforInterface());
    }

    @Override
    public void getPLULikeInfo(String barCode) {
        PluLikeBean mPluLikeBean = new PluLikeBean();
        mPluLikeBean.getJsonData().setAs_branchNo(Config.branch_no);
        mPluLikeBean.getJsonData().setAs_searchstr(barCode);
        Map map = ReflectionUtils.obj2Map(mPluLikeBean);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new GetGoodInforInterface());
    }

    //销售商品取价
    @Override
    public void getPluPrice(String barCode) {
        GetPluPriceBean mGetPluPrice = new GetPluPriceBean();
        mGetPluPrice.getJsonData().setAs_branchNo(Config.branch_no);
        mGetPluPrice.getJsonData().setAs_flowno(Config.posid);
        mGetPluPrice.getJsonData().setAs_cardno("");
        Map map = ReflectionUtils.obj2Map(mGetPluPrice);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new GetGoodPriceInterface());
    }

    //结算
    @Override
    public void sellSub() {
        SellSubBean mSellSubBean = new SellSubBean();
        mSellSubBean.getJsonData().setAs_branchNo(Config.branch_no);
        mSellSubBean.getJsonData().setAs_flowno(Config.posid);//结账流水
        Map map = ReflectionUtils.obj2Map(mSellSubBean);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new SellSubInterface());
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
        Map map = ReflectionUtils.obj2Map(mDanJuDetailBean);
        mINetWork.doPost(WebConfig.getPostWsdlUri(),map,new GetGoodDetailInterface());
    }

    //获取流水
    class GetFlowNoInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
            Log.e("error", event.toString());
        }

        @Override
        public void handleResult(Response event, String result) {
            //{"status":"0","msg":"","jsonData":"{\"FlowNo\":\"0\"}","sign":""}
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

    //获取订单明细
    class GetGoodDetailInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event,String result) {
            DanJuDetailBeanResult mDanJuDetailBeanResult =  mJsonFormatImp.JsonToBean(result,DanJuDetailBeanResult.class);
            if(mDanJuDetailBeanResult.status.equals(Config.MESSAGE_OK+"")){
                mHandler.handleResule(Config.MESSAGE_SHEET_DETAIL,mDanJuDetailBeanResult);
            }else{
                mHandler.handleResule(Config.MESSAGE_ERROR,mDanJuDetailBeanResult);
            }
        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {

        }
    }

    //查询商品信息
    class GetGoodInforInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event,String result) {
            GoodGetBeanResult mGoodGetBeanResult =  mJsonFormatImp.JsonToBean(result,GoodGetBeanResult.class);
            if(mGoodGetBeanResult.status.equals(Config.MESSAGE_OK+"")){
                mHandler.handleResule(Config.MESSAGE_GOODS_INFOR,mGoodGetBeanResult);
            }else{
                mHandler.handleResule(Config.MESSAGE_ERROR,mGoodGetBeanResult);
            }
        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {

        }
    }

    //获取商品价格
    class GetGoodPriceInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event,String result) {
            GetPluPriceBeanResult mGetPluPriceBeanResult =  mJsonFormatImp.JsonToBean(result,GetPluPriceBeanResult.class);
            if(mGetPluPriceBeanResult.status.equals(Config.MESSAGE_OK+"")){
                mHandler.handleResule(Config.MESSAGE_GOODS_INFOR,mGetPluPriceBeanResult);
            }else{
                mHandler.handleResule(Config.MESSAGE_ERROR,mGetPluPriceBeanResult);
            }
        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {

        }
    }

    //结算
    class SellSubInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event,String result) {
            SellSubBeanResult mSellSubBeanResult =  mJsonFormatImp.JsonToBean(result,SellSubBeanResult.class);
            if(mSellSubBeanResult.status.equals(Config.MESSAGE_OK+"")){
                mHandler.handleResule(Config.MESSAGE_GOODS_INFOR,mSellSubBeanResult);
            }else{
                mHandler.handleResule(Config.MESSAGE_ERROR,mSellSubBeanResult);
            }
        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {

        }
    }
}
