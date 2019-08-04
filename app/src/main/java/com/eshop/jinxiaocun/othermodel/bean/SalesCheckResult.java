package com.eshop.jinxiaocun.othermodel.bean;

import java.io.Serializable;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/8/4
 * Desc: 销售查询 返回的数据
 */
public class SalesCheckResult implements Serializable {
    private String BeginTime;//最前一笔时间
    private String EndTime; //最后一笔时间
    private String SaleSum;	//销售金额
    private String PaySum;	//付款金额

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getSaleSum() {
        return SaleSum;
    }

    public void setSaleSum(String saleSum) {
        SaleSum = saleSum;
    }

    public String getPaySum() {
        return PaySum;
    }

    public void setPaySum(String paySum) {
        PaySum = paySum;
    }
}
