package com.eshop.jinxiaocun.turnedpurchase.bean;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/7/17
 * Desc:
 */
public class ReturnedPurchaseBean {

    private String billNo;//单据号
    private String goodsName;//商品名称
    private String billType;//单据类型
    private float price;//价格
    private float reQty;//可退数量
    private float qty;//退货数量


    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getReQty() {
        return reQty;
    }

    public void setReQty(float reQty) {
        this.reQty = reQty;
    }

    public float getQty() {
        return qty;
    }

    public void setQty(float qty) {
        this.qty = qty;
    }
}
