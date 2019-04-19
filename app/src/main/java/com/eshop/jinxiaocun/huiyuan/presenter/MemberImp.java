package com.eshop.jinxiaocun.huiyuan.presenter;

import android.text.TextUtils;

import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.huiyuan.bean.ExpenseCheckBean;
import com.eshop.jinxiaocun.huiyuan.bean.ExpenseCheckResult;
import com.eshop.jinxiaocun.huiyuan.bean.ExpenseCheckResultItem;
import com.eshop.jinxiaocun.huiyuan.bean.MemberCheckBean;
import com.eshop.jinxiaocun.huiyuan.bean.MemberCheckResult;
import com.eshop.jinxiaocun.huiyuan.bean.MemberCheckResultItem;
import com.eshop.jinxiaocun.huiyuan.bean.MemberRechargeBean;
import com.eshop.jinxiaocun.netWork.httpDB.INetWork;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.netWork.httpDB.NetWorkImp;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.DanJuListImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.ReflectionUtils;
import com.eshop.jinxiaocun.utils.WebConfig;

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
        mINetWork.doGet(WebConfig.getGetWsdlUri(),map,new MemberRechargeInterface());
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
        public void handleResultJson(String status, String Msg, String jsonData) {
            try {
                MemberCheckResult listResult =  mJsonFormatImp.JsonToBean(jsonData,MemberCheckResult.class);
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
                ExpenseCheckResult listResult =  mJsonFormatImp.JsonToBean(jsonData,ExpenseCheckResult.class);
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
                    mHandler.handleResule(Config.RESULT_FAIL,"充值失败");
                }
            } catch (Exception e) {
                mHandler.handleResule(Config.RESULT_FAIL,"充值异常:"+e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
