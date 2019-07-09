package com.eshop.jinxiaocun.huiyuan.presenter;

import android.text.TextUtils;

import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.huiyuan.bean.AddMemberBean;
import com.eshop.jinxiaocun.huiyuan.bean.ExpenseCheckBean;
import com.eshop.jinxiaocun.huiyuan.bean.ExpenseCheckResultItem;
import com.eshop.jinxiaocun.huiyuan.bean.IntegralExchangeBean;
import com.eshop.jinxiaocun.huiyuan.bean.IntegralExchangeGoodsBean;
import com.eshop.jinxiaocun.huiyuan.bean.IntegralExchangeGoodsResultItem;
import com.eshop.jinxiaocun.huiyuan.bean.IntegralSubtractBean;
import com.eshop.jinxiaocun.huiyuan.bean.MemberCheckBean;
import com.eshop.jinxiaocun.huiyuan.bean.MemberCheckResultItem;
import com.eshop.jinxiaocun.huiyuan.bean.MemberRechargeBean;
import com.eshop.jinxiaocun.jichi.JichiChaxunBean;
import com.eshop.jinxiaocun.jichi.JichiChaxunResult;
import com.eshop.jinxiaocun.jichi.JichiSaveBean;
import com.eshop.jinxiaocun.jichi.JichiSaveResult;
import com.eshop.jinxiaocun.netWork.httpDB.INetWork;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.netWork.httpDB.NetWorkImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.utils.ReflectionUtils;
import com.eshop.jinxiaocun.utils.WebConfig;

import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/18
 * Desc: 会员接口的实现
 */

public class MemberImp implements IMemberList{

    private INetWorResult mHandler;
    private INetWork mINetWork;
    IJsonFormat mJsonFormatImp = new JsonFormatImp();

    public MemberImp(INetWorResult mHandler) {
        this.mHandler = mHandler;
        mINetWork = new NetWorkImp(Application.mContext);
    }

    /**
     * 新增会员
     * @param bean 参数
     */
    @Override
    public void addMemberData(AddMemberBean bean) {
        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doPost(WebConfig.getPostWsdlUri(),map,new AddMemberInterface());
    }

    /**
     * 会员查询
     * @param cardNo 卡号、手机号、姓名
     */
    @Override
    public void getMemberCheckData(String cardNo) {
        MemberCheckBean bean = new MemberCheckBean();
        bean.JsonData.CardNo = cardNo;
        bean.JsonData.BranchNo = Config.branch_no;
        bean.JsonData.UserId = Config.UserId;
        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new MemberCheckInterface());
    }

    /**
     * 消费查询
     * @param cardID 会员id
     * @param startDate 开始时间
     * @param endDate  结束时间
     */
    @Override
    public void getExpenseCheckData(String cardID, String startDate, String endDate) {
        ExpenseCheckBean bean = new ExpenseCheckBean();
        bean.JsonData.vip_no = cardID;
        bean.JsonData.start_date = startDate;
        bean.JsonData.end_date = endDate;
        bean.JsonData.as_branchno = Config.branch_no;
        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new ExpenseCheckInterface());
    }

    /**
     * 会员充值
     * @param bean 参数
     */
    @Override
    public void setMemberRechargeData(MemberRechargeBean bean) {
        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doPost(WebConfig.getPostWsdlUri(),map,new MemberRechargeInterface());
    }
    /**
     * 积分冲减
     * @param bean 参数
     */
    @Override
    public void integralSubtract(IntegralSubtractBean bean) {
        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doPost(WebConfig.getPostWsdlUri(),map,new IntegralSubtractInterface());
    }

    /**
     * 查看积分兑换商品
     * @param cardNo 卡号
     * @param integral 积分
     */
    @Override
    public void getIntegralExchangeGoods(String cardNo, float integral) {
        IntegralExchangeGoodsBean bean = new IntegralExchangeGoodsBean();
        bean.JsonData.BranchNo = Config.branch_no;
        bean.JsonData.UserId = Config.UserId;
        bean.JsonData.CardNo = cardNo;
        bean.JsonData.VipAcc = integral;

        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new IntegralExchangeGoodsInterface());
    }

    /**
     * 积分兑换
     * @param bean 参数
     */
    @Override
    public void integralExchange(IntegralExchangeBean bean) {
        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doPost(WebConfig.getPostWsdlUri(),map,new IntegralExchangeInterface());
    }

    @Override
    public void qryCountInfo(String sheet_no) {
        JichiChaxunBean bean = new JichiChaxunBean();
        bean.JsonData.sheet_no = sheet_no;
        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new JichiChaxunInterface());
    }

    //计次查询
    class JichiChaxunInterface implements IResponseListener{
        @Override
        public void handleError(Object event) {}
        @Override
        public void handleResult(Response event, String result) {}

        @Override
        public void handleResultJson(String status, String msg, String jsonData) {
            try {
                List<JichiChaxunResult> listResult =  mJsonFormatImp.JsonToList(jsonData,JichiChaxunResult.class);
                if(!TextUtils.isEmpty(status) && status.equals(Config.MESSAGE_OK+"")){
                    mHandler.handleResule(Config.MESSAGE_JICI_CHAXUN_OK,listResult);
                }else{
                    mHandler.handleResule(Config.MESSAGE_ERROR,msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.MESSAGE_ERROR,e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void SaveCountSale(String sheet_no) {
        JichiSaveBean bean = new JichiSaveBean();
        bean.JsonData.sheet_no = sheet_no;
        bean.JsonData.branch_no = Config.branch_no;
        bean.JsonData.consum_count = 1;
        bean.JsonData.oper_oper = Config.UserName;
        bean.JsonData.oper_date = DateUtility.getCurrentTime();
        bean.JsonData.memo = "";
        bean.JsonData.aprove = "1";
        bean.JsonData.aprove_date = bean.JsonData.oper_date;
        Map map = ReflectionUtils.obj2Map(bean);
        mINetWork.doPost(WebConfig.getPostWsdlUri(),map,new SaveCountSaleInterface());
    }

    //计次保存
    class SaveCountSaleInterface implements IResponseListener{
        @Override
        public void handleError(Object event) {}
        @Override
        public void handleResult(Response event, String result) {}

        @Override
        public void handleResultJson(String status, String msg, String jsonData) {
            try {
                if( status.equals("-1") || jsonData == null || jsonData.equals("null")){
                    mHandler.handleResule(Config.MESSAGE_ERROR,"操作失败 "+msg);
                    return;
                }
                JichiSaveResult listResult =  mJsonFormatImp.JsonToBean(jsonData, JichiSaveResult.class);
                if(!TextUtils.isEmpty(status) && status.equals(Config.MESSAGE_JICHI_OK+"")){
                    mHandler.handleResule(Config.MESSAGE_JICI_SAVE_OK,listResult);
                }else{
                    mHandler.handleResule(Config.MESSAGE_ERROR,msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.MESSAGE_ERROR,e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //新增会员
    class AddMemberInterface implements IResponseListener{
        @Override
        public void handleError(Object event) {}
        @Override
        public void handleResult(Response event, String result) {}

        @Override
        public void handleResultJson(String status, String msg, String jsonData) {
            try {
                if(!TextUtils.isEmpty(status) && status.equals(Config.MESSAGE_OK+"")){
                    mHandler.handleResule(Config.RESULT_SUCCESS,"保存成功");
                }else{
                    mHandler.handleResule(Config.RESULT_FAIL,"保存失败:"+msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.RESULT_FAIL,"保存异常:"+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //会员查询
    class MemberCheckInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event, String result) {

        }

        @Override
        public void handleResultJson(String status, String msg, String jsonData) {
            try {
                List<MemberCheckResultItem> listResult =  mJsonFormatImp.JsonToList(jsonData,MemberCheckResultItem.class);
                if(!TextUtils.isEmpty(status) && status.equals(Config.MESSAGE_OK+"")){
                    mHandler.handleResule(Config.MESSAGE_OK,listResult);
                }else{
                    mHandler.handleResule(Config.MESSAGE_ERROR,msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.MESSAGE_ERROR,e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //消费查询
    class ExpenseCheckInterface implements IResponseListener {

        @Override
        public void handleError(Object event) {
        }

        @Override
        public void handleResult(Response event, String result) {

        }

        @Override
        public void handleResultJson(String status, String Msg, String jsonData) {
            try {
                List<ExpenseCheckResultItem> listResult =  mJsonFormatImp.JsonToList(jsonData,ExpenseCheckResultItem.class);
                if(!TextUtils.isEmpty(status) && status.equals(Config.MESSAGE_OK+"")){
                    mHandler.handleResule(Config.MESSAGE_OK,listResult);
                }else{
                    mHandler.handleResule(Config.MESSAGE_ERROR,Msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.MESSAGE_ERROR,e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //会员充值
    class MemberRechargeInterface implements IResponseListener{
        @Override
        public void handleError(Object event) {}
        @Override
        public void handleResult(Response event, String result) {}

        @Override
        public void handleResultJson(String status, String msg, String jsonData) {
            try {
                if(!TextUtils.isEmpty(status) && status.equals(Config.MESSAGE_OK+"")){
                    mHandler.handleResule(Config.RESULT_SUCCESS,"充值成功");
                }else{
                    mHandler.handleResule(Config.RESULT_FAIL,"充值失败:"+msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.RESULT_FAIL,"充值异常:"+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //积分冲减
    class IntegralSubtractInterface implements IResponseListener{
        @Override
        public void handleError(Object event) {}
        @Override
        public void handleResult(Response event, String result) {}

        @Override
        public void handleResultJson(String status, String msg, String jsonData) {
            try {
                if(!TextUtils.isEmpty(status) && status.equals(Config.MESSAGE_OK+"")){
                    mHandler.handleResule(Config.RESULT_SUCCESS,"积分冲减成功");
                }else{
                    mHandler.handleResule(Config.RESULT_FAIL,"积分冲减失败:"+msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.RESULT_FAIL,"积分冲减异常:"+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //查看积分兑换商品
    class IntegralExchangeGoodsInterface implements IResponseListener{

        @Override
        public void handleError(Object event) {}

        @Override
        public void handleResult(Response event, String result) {}

        @Override
        public void handleResultJson(String status, String msg, String jsonData) {
            try {
                List<IntegralExchangeGoodsResultItem> listResult =  mJsonFormatImp.JsonToList(jsonData,IntegralExchangeGoodsResultItem.class);
                if(!TextUtils.isEmpty(status) && status.equals(Config.MESSAGE_OK+"")){
                    mHandler.handleResule(Config.MESSAGE_OK,listResult);
                }else{
                    mHandler.handleResule(Config.MESSAGE_ERROR,msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.MESSAGE_ERROR,e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //积分兑换
    class IntegralExchangeInterface implements IResponseListener{

        @Override
        public void handleError(Object event) {}

        @Override
        public void handleResult(Response event, String result) {}

        @Override
        public void handleResultJson(String status, String msg, String jsonData) {
            try {
                if(!TextUtils.isEmpty(status) && status.equals(Config.MESSAGE_OK+"")){
                    mHandler.handleResule(Config.RESULT_SUCCESS,"兑换成功");
                }else{
                    mHandler.handleResule(Config.RESULT_FAIL,"兑换失败:"+msg);
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.RESULT_FAIL,"兑换异常:"+e.getMessage());
                e.printStackTrace();
            }
        }
    }


}
