package com.eshop.jinxiaocun.huiyuan.presenter;

import com.eshop.jinxiaocun.huiyuan.bean.MemberRechargeBean;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/18
 * Desc:  会员接口
 */

public interface IMemberList {

    //会员查询
    void getMemberCheckData(String cardNo);//cardNo包括卡号、手机号、姓名
    //消费查询
    void getExpenseCheckData(String cardID,String startDate,String endDate);
    //会员充值
    void setMemberRechargeData(MemberRechargeBean bean);
}
