package com.eshop.jinxiaocun.pifaxiaoshou.presenter;

import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.base.bean.BillType;
import com.eshop.jinxiaocun.netWork.httpDB.INetWork;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuDetailBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuDetailBeanResult;
import com.eshop.jinxiaocun.utils.ReflectionUtils;
import com.eshop.jinxiaocun.utils.WebConfig;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.GoodGetBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.GoodGetBeanResult;
import java.io.IOException;
import java.util.Map;

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
        Map map = ReflectionUtils.obj2Map(mGoodGetBean);
        mINetWork.doPost(WebConfig.getPostWsdlUri(),map,new GetGoodInforInterface());
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

    //获取订单明细
    class GetGoodDetailInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event, String result) {
            DanJuDetailBeanResult mDanJuDetailBeanResult =  mJsonFormatImp.JsonToBean(result,DanJuDetailBeanResult.class);
            if(mDanJuDetailBeanResult.status.equals(Config.MESSAGE_OK+"")){
                mHandler.handleResule(Config.MESSAGE_SHEET_DETAIL,mDanJuDetailBeanResult);
            }else{
                mHandler.handleResule(Config.MESSAGE_ERROR,mDanJuDetailBeanResult);
            }
        }
    }

    //查询商品信息
    class GetGoodInforInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event, String result) {
            GoodGetBeanResult mGoodGetBeanResult =  mJsonFormatImp.JsonToBean(result,GoodGetBeanResult.class);
            if(mGoodGetBeanResult.status.equals(Config.MESSAGE_OK+"")){
                mHandler.handleResule(Config.MESSAGE_GOODS_INFOR,mGoodGetBeanResult);
            }else{
                mHandler.handleResule(Config.MESSAGE_ERROR,mGoodGetBeanResult);
            }
        }
    }

}
