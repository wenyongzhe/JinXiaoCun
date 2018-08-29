package com.eshop.jinxiaocun.piandian.presenter;

import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.netWork.httpDB.INetWork;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.netWork.httpDB.NetWorkImp;
import com.eshop.jinxiaocun.piandian.bean.PandianFanweiBeanResult;
import com.eshop.jinxiaocun.piandian.bean.PandianLeibieBeanResult;
import com.eshop.jinxiaocun.piandian.bean.PandianLeibieBeanResultItem;
import com.eshop.jinxiaocun.piandian.bean.PandianPihaoCreateBeanResult;
import com.eshop.jinxiaocun.piandian.bean.PandianStoreJigouBeanResult;
import com.eshop.jinxiaocun.piandian.bean.PandianStoreJigouBeanResultItem;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.ReflectionUtils;
import com.eshop.jinxiaocun.utils.WebConfig;

import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * @Author Lu An
 * 创建时间  2018/8/27 0027
 * 描述
 */

public class PandianCreatImp implements IPandianCreat{


    private INetWorResult mHandler;
    private INetWork mINetWork;
    private IJsonFormat mJsonFormatImp = new JsonFormatImp();

    public PandianCreatImp(INetWorResult handler) {
        this.mHandler = handler;
        mINetWork = new NetWorkImp(Application.mContext);
    }


    @Override
    public void getPandianFanweiData(BaseBean bean) {
        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new PandianCreatImp.PandianFanweiInterface());
    }

    @Override
    public void getPandianTypeData(BaseBean bean) {
        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new PandianCreatImp.PandianLeibieInterface());
    }

    @Override
    public void getPandianStoreJigouData(BaseBean bean) {
        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new PandianCreatImp.PandianStoreJigouInterface());
    }

    @Override
    public void getPandianPihaoCreateData(BaseBean bean) {
        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doGet(WebConfig.getPostWsdlUri(),map,new PandianCreatImp.PandianPihaoCreateInterface());
    }

    //取盘点范围
    class PandianFanweiInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event, String result) {

        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {
            PandianFanweiBeanResult mBeanResult = null;
            try {
                mBeanResult =  mJsonFormatImp.JsonToBean(jsonData,PandianFanweiBeanResult.class);
                if(status.equals(Config.MESSAGE_OK+"")){
                    mHandler.handleResule(Config.MESSAGE_OK,mBeanResult);
                }else{
                    mHandler.handleResule(Config.MESSAGE_ERROR,Msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.MESSAGE_ERROR,e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //取盘点类别
    class PandianLeibieInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event, String result) {
        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {
            if(status.equals(Config.MESSAGE_OK+"")){
                List<PandianLeibieBeanResultItem> resultItemList = mJsonFormatImp.JsonToList(jsonData,PandianLeibieBeanResultItem.class);
                mHandler.handleResule(Config.MESSAGE_PANDIANLEIBIE_OK,resultItemList);
            }else{
                mHandler.handleResule(Config.MESSAGE_PANDIANLEIBIE_ERROR,Msg);
            }
        }
    }

    //取盘点门店机构
    class PandianStoreJigouInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event, String result) {

        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {
            try {
                List<PandianStoreJigouBeanResultItem> resultItemList =  mJsonFormatImp.JsonToList(jsonData,PandianStoreJigouBeanResultItem.class);
                if(status.equals(Config.MESSAGE_OK+"")){
                    mHandler.handleResule(Config.MESSAGE_PANDIANSTOREJIGOU_OK,resultItemList);
                }else{
                    mHandler.handleResule(Config.MESSAGE_PANDIANSTOREJIGOU_ERROR,Msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.MESSAGE_PANDIANSTOREJIGOU_ERROR,e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //盘点批号生成
    class PandianPihaoCreateInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event, String result) {

        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {
            PandianPihaoCreateBeanResult mBeanResult = null;
            try {
                mBeanResult =  mJsonFormatImp.JsonToBean(jsonData,PandianPihaoCreateBeanResult.class);
                if(status.equals(Config.MESSAGE_OK+"")){
                    mHandler.handleResule(Config.MESSAGE_PANDIANPIHAOCREATE_OK,mBeanResult);
                }else{
                    mHandler.handleResule(Config.MESSAGE_PANDIANPIHAOCREATE_ERROR,Msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.MESSAGE_PANDIANPIHAOCREATE_ERROR,e.getMessage());
                e.printStackTrace();
            }
        }
    }


}
