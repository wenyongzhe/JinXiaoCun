package com.eshop.jinxiaocun.othermodel.presenter;

import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.netWork.httpDB.INetWork;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.netWork.httpDB.NetWorkImp;
import com.eshop.jinxiaocun.othermodel.bean.CustomerInfoBean;
import com.eshop.jinxiaocun.othermodel.bean.CustomerInfoBeanResult;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBeanResult;
import com.eshop.jinxiaocun.othermodel.bean.ProviderBean;
import com.eshop.jinxiaocun.othermodel.bean.ProviderInfoBeanResult;
import com.eshop.jinxiaocun.othermodel.bean.SheetNoBeanResult;
import com.eshop.jinxiaocun.othermodel.bean.SheetSaveBean;
import com.eshop.jinxiaocun.othermodel.bean.WarehouseInfoBean;
import com.eshop.jinxiaocun.othermodel.bean.WarehouseInfoBeanResult;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.ReflectionUtils;
import com.eshop.jinxiaocun.utils.WebConfig;

import org.json.JSONObject;

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
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new GoodsPiciInterface());
    }

    @Override
    public void getCustomerInfo(String type ,String sheetType ,String zjm,int pageIndex,int pageSize) {

        CustomerInfoBean bean = new CustomerInfoBean();
        bean.JsonData.POSId = Config.posid;
        bean.JsonData.UserId = Config.UserId;//操作员
        bean.JsonData.Type = type;// 1门店机构 2分部
        bean.JsonData.BranchNo = Config.branch_no; //机构号
        bean.JsonData.SheetType = sheetType;//单据类型
        bean.JsonData.zjm = zjm;//助记码不生效
        bean.JsonData.Page = pageIndex;
        bean.JsonData.PageNum = pageSize;

        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new CustomerInfoInterface());
    }

    //上传单据主表信息
    @Override
    public void uploadDanjuMainInfo(BaseBean bean) {
        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doPost(WebConfig.getPostWsdlUri(),map,new UploadDanjuMianInterface());
    }

    @Override
    public void uploadDanjuDetailInfo(BaseBean bean) {
        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doPost(WebConfig.getPostWsdlUri(),map,new UploadDanjuDetailInterface());
    }

    @Override
    public void sheetSave(String orderType, String orderNo) {
        SheetSaveBean bean = new SheetSaveBean();
        bean.JsonData.trans_no = orderType ;//单据类型
        bean.JsonData.branchNo= Config.branch_no ; //门店机构
        bean.JsonData.Sheet_No= orderNo ;//单据号
        bean.JsonData.oper_id= Config.UserId ; //操作员

        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doPost(WebConfig.getPostWsdlUri(),map,new SheetSaveInterface());
    }

    //查询供应商
    @Override
    public void getProviderInfo(String sheetType,String zjm, int pageIndex, int pageSize) {
        ProviderBean bean = new ProviderBean();
        bean.JsonData.pos_id = Config.posid;
        bean.JsonData.user_id = Config.UserId ; //操作员
        bean.JsonData.type= "";
        bean.JsonData.branchNo = Config.branch_no ; //门店/机构
        bean.JsonData.sheettype = sheetType;//当前操作的单据类型
        bean.JsonData.zjm = zjm;//
        bean.JsonData.page = pageIndex;//每页显示的数量
        bean.JsonData.pagenum = pageSize;//每页显示的数量

        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doPost(WebConfig.getGetWsdlUri(),map,new ProviderInfoInterface());
    }

    //查询仓库信息
    @Override
    public void getWarehouseUnfo(String sheetType) {
        WarehouseInfoBean bean = new WarehouseInfoBean();
        bean.JsonData.pos_id = Config.posid;
        bean.JsonData.user_id = Config.UserId;//操作员
        bean.JsonData.type = "P";
        bean.JsonData.sheettype = sheetType;//当前操作的单据类型
        bean.JsonData.branchNo = Config.branch_no;//门店/机构

        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doPost(WebConfig.getGetWsdlUri(),map,new WarehouseInfoInterface());
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
                    mHandler.handleResule(Config.MESSAGE_PICI,resultItem);
                }else{
                    mHandler.handleResule(Config.RESULT_FAIL,Msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.RESULT_FAIL,e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //获取客户信息
    class CustomerInfoInterface implements IResponseListener {

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
                    List<CustomerInfoBeanResult> resultItem=  mJsonFormatImp.JsonToList(jsonData,CustomerInfoBeanResult.class);
                    mHandler.handleResule(Config.MESSAGE_OK,resultItem);
                }else{
                    mHandler.handleResule(Config.MESSAGE_ERROR,Msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.MESSAGE_ERROR,e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //上传单据主表
    class UploadDanjuMianInterface implements IResponseListener {

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
                    mHandler.handleResule(Config.MESSAGE_SUCCESS,"上传单据主表成功!");
                }else{
                    mHandler.handleResule(Config.MESSAGE_FAIL,"上传单据主表失败："+Msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.MESSAGE_FAIL,"上传单据主表失败："+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //上传单据明细
    class UploadDanjuDetailInterface implements IResponseListener {

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
                    mHandler.handleResule(Config.MESSAGE_RESULT_SUCCESS,"上传单据明细成功!");
                }else{
                    mHandler.handleResule(Config.MESSAGE_FAIL,"上传单据明细失败："+Msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.MESSAGE_FAIL,"上传单据明细失败："+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //保存业务单据
    class SheetSaveInterface implements IResponseListener {

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
                    mHandler.handleResule(Config.RESULT_SUCCESS,"保存成功!");
                }else{
                    mHandler.handleResule(Config.MESSAGE_FAIL,"保存失败: "+Msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.MESSAGE_FAIL,"保存失败: "+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //查询供应商
    class ProviderInfoInterface implements IResponseListener {

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
                    List<ProviderInfoBeanResult> resultList = mJsonFormatImp.JsonToList(jsonData,ProviderInfoBeanResult.class);
                    mHandler.handleResule(Config.MESSAGE_OK,resultList);
                }else{
                    mHandler.handleResule(Config.MESSAGE_FAIL,"查询失败: "+Msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.MESSAGE_FAIL,"查询失败: "+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //查询仓库
    class WarehouseInfoInterface implements IResponseListener {

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
                    List<WarehouseInfoBeanResult> resultList = mJsonFormatImp.JsonToList(jsonData,WarehouseInfoBeanResult.class);
                    mHandler.handleResule(Config.MESSAGE_OK,resultList);
                }else{
                    mHandler.handleResule(Config.MESSAGE_FAIL,"查询失败: "+Msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.MESSAGE_FAIL,"查询失败: "+e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
