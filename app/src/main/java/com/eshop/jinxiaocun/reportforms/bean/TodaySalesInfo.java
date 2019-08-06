package com.eshop.jinxiaocun.reportforms.bean;

import java.util.List;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/8/6
 * Desc:
 */
public class TodaySalesInfo {

    private String billNo;//单据号
    private String billDate;//单据日期
    private List<SalesGoodsInfo> salesGoodsInfos;//销售商品信息

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

    public List<SalesGoodsInfo> getSalesGoodsInfos() {
        return salesGoodsInfos;
    }

    public void setSalesGoodsInfos(List<SalesGoodsInfo> salesGoodsInfos) {
        this.salesGoodsInfos = salesGoodsInfos;
    }
}
