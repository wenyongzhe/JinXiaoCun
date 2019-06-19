package com.eshop.jinxiaocun.huiyuan.presenter;

import com.eshop.jinxiaocun.huiyuan.bean.AddMemberBean;
import com.eshop.jinxiaocun.huiyuan.bean.IntegralExchangeBean;
import com.eshop.jinxiaocun.huiyuan.bean.IntegralSubtractBean;
import com.eshop.jinxiaocun.huiyuan.bean.MemberRechargeBean;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/18
 * Desc:  会员接口
 */

public interface IMemberList {

    void addMemberData(AddMemberBean bean);
    //会员查询
    void getMemberCheckData(String cardNo);//cardNo包括卡号、手机号、姓名
    //消费查询
    void getExpenseCheckData(String cardID,String startDate,String endDate);
    //会员充值
    void setMemberRechargeData(MemberRechargeBean bean);
    //积分冲减
    void integralSubtract(IntegralSubtractBean bean);
    //查询积分兑换商品
    void getIntegralExchangeGoods(String cardNo,float integral);
    //积分兑换
    void integralExchange(IntegralExchangeBean bean);
    //1.21查询计次项目  （t_rm_vip_stored）
    void qryCountInfo(String  sheet_no);
    //1.22计次项目销售
    void SaveCountSale(String  sheet_no);

}
