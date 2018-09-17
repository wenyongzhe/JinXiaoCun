package com.eshop.jinxiaocun.lingshou.presenter;

import android.util.Log;

import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.base.bean.BillType;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.lingshou.bean.GetFlowNoBean;
import com.eshop.jinxiaocun.lingshou.bean.GetFlowNoBeanResult;
import com.eshop.jinxiaocun.lingshou.bean.GetPluPriceBean;
import com.eshop.jinxiaocun.lingshou.bean.GetPluPriceBeanResult;
import com.eshop.jinxiaocun.lingshou.bean.SellSubBean;
import com.eshop.jinxiaocun.lingshou.bean.SellSubBeanResult;
import com.eshop.jinxiaocun.lingshou.bean.UpPalyFlowBean;
import com.eshop.jinxiaocun.lingshou.bean.UpSallFlowBean;
import com.eshop.jinxiaocun.netWork.httpDB.INetWork;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.netWork.httpDB.NetWorkImp;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuDetailBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuDetailBeanResult;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.GoodGetBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.PluLikeBean;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.ReflectionUtils;
import com.eshop.jinxiaocun.utils.WebConfig;

import java.util.List;
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
        mGoodGetBean.getJsonData().setOper_id(Config.posid);
        mGoodGetBean.getJsonData().setPerNum(Config.PerNum);
        mGoodGetBean.getJsonData().setPageNum("1");

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
    public void getPluPrice() {
        GetPluPriceBean mGetPluPrice = new GetPluPriceBean();
        mGetPluPrice.getJsonData().setAs_branchNo(Config.branch_no);
        mGetPluPrice.getJsonData().setAs_flowno(Config.posid);
        mGetPluPrice.getJsonData().setAs_cardno("");
        Map map = ReflectionUtils.obj2Map(mGetPluPrice);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new GetGoodPriceInterface());
    }

    //结算
    @Override
    public void sellSub(String flowno) {
        SellSubBean mSellSubBean = new SellSubBean();
        mSellSubBean.getJsonData().setAs_branchNo(Config.branch_no);
        mSellSubBean.getJsonData().setAs_flowno(flowno);//结账流水
        Map map = ReflectionUtils.obj2Map(mSellSubBean);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new SellSubInterface());
    }

    @Override
    public void upSallFlow(List listdata) {
        UpSallFlowBean mUpSallFlowBean = new UpSallFlowBean();
        mUpSallFlowBean.setJsonData(listdata);
        Map map = ReflectionUtils.obj2Map(mUpSallFlowBean);
        mINetWork.doPost(WebConfig.getPostWsdlUri(),map,new UpSallFlowInterface());

    }

    @Override
    public void upPlayFlow(List list) {
        UpPalyFlowBean mUpPalyFlowBean = new UpPalyFlowBean();
        mUpPalyFlowBean.setJsonData(list);
        Map map = ReflectionUtils.obj2Map(mUpPalyFlowBean);
        mINetWork.doPost(WebConfig.getPostWsdlUri(),map,new UpPlayFlowInterface());
    }

    @Override
    public void getOptAuth() {

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
        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {
            GetFlowNoBeanResult.FlowNoJson mGetFlowNoBeanResult =  mJsonFormatImp.JsonToBean(jsonData,GetFlowNoBeanResult.FlowNoJson.class);
            if(status.equals(Config.MESSAGE_OK+"")){
                mHandler.handleResule(Config.MESSAGE_FLOW_NO,mGetFlowNoBeanResult);
            }else{
                mHandler.handleResule(Config.MESSAGE_ERROR,mGetFlowNoBeanResult);
            }
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
        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {
            try {
                if(status.equals(Config.MESSAGE_OK+"")){
                    List<GetClassPluResult> mGoodGetBeanResult =  mJsonFormatImp.JsonToList(jsonData,GetClassPluResult.class);
                    mHandler.handleResule(Config.MESSAGE_GOODS_INFOR,mGoodGetBeanResult);
                }else{
                    mHandler.handleResule(Config.MESSAGE_GOODS_INFOR_FAIL,Msg);
                }
            }catch (Exception e){
                mHandler.handleResule(Config.MESSAGE_GOODS_INFOR_FAIL,e.getMessage());
            }

        }
    }

    //获取商品价格
    class GetGoodPriceInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event,String result) {
        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {
            List<GetPluPriceBeanResult> mGetPluPriceBeanResult =  mJsonFormatImp.JsonToList(jsonData,GetPluPriceBeanResult.class);
            if(status.equals(Config.MESSAGE_OK+"")){
                mHandler.handleResule(Config.MESSAGE_GETPLU_PRICE,mGetPluPriceBeanResult);
            }else{
                mHandler.handleResule(Config.MESSAGE_ERROR,mGetPluPriceBeanResult);
            }
        }
    }

    //结算
    class SellSubInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event,String result) {
        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {
            SellSubBeanResult.SellSubJsonData mSellSubBeanResult =  mJsonFormatImp.JsonToBean(jsonData,SellSubBeanResult.SellSubJsonData.class);
            if(status.equals(Config.MESSAGE_OK+"")){
                mHandler.handleResule(Config.MESSAGE_SELL_SUB,mSellSubBeanResult);
            }else{
                mHandler.handleResule(Config.MESSAGE_ERROR,mSellSubBeanResult);
            }
        }
    }

    //上传销售流水
    class UpSallFlowInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event,String result) {
        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {
            if(status.equals(Config.MESSAGE_OK+"")){
                mHandler.handleResule(Config.MESSAGE_UP_SALL_FLOW,null);
            }else{
                mHandler.handleResule(Config.MESSAGE_ERROR,null);
            }
        }
    }

    //上传付款
    class UpPlayFlowInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event,String result) {
        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {
            if(status.equals(Config.MESSAGE_OK+"")){
                mHandler.handleResule(Config.MESSAGE_UP_PLAY_FLOW,null);
            }else{
                mHandler.handleResule(Config.MESSAGE_ERROR,null);
            }
        }
    }
}
