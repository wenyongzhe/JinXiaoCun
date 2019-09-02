package com.eshop.jinxiaocun.reportforms.bean;

import com.eshop.jinxiaocun.othermodel.bean.PayQueryBeanResult;

import java.util.List;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/8/12
 * Desc: 今日收款数据
 */
public class TodayGatheringInfo {

    private String billNo;//单据号
    private String billDate;//单据日期
    private List<PayQueryBeanResult> payRecordInfos;//付款信息

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public List<PayQueryBeanResult> getPayRecordInfos() {
        return payRecordInfos;
    }

    public void setPayRecordInfos(List<PayQueryBeanResult> payRecordInfos) {
        this.payRecordInfos = payRecordInfos;
    }
}
