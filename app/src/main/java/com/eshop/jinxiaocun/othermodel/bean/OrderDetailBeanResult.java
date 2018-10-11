package com.eshop.jinxiaocun.othermodel.bean;

/**
 * @Author Lu An
 * 创建时间  2018/10/10 0010
 * 描述
 */

public class OrderDetailBeanResult {

    private String BarCode;//商品编码
    private String PluBatch; //批次
    private String SelfCode;//自编码15
    private String Name;//商品名称
    private String Unit;  //单位
    private int CheckNum; //实际 数量
    private int StockNum;//库存 数量
    private int ReferenceNum; //参考数量
    private float BuyPrice;  //进价
    private float SalePrice; //销价
    private String GoodsSeat;//货架号
    private String MadeDate;//生产日期
    private String VaildDate; //有效日期
    private String Enable_batch;

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public String getPluBatch() {
        return PluBatch;
    }

    public void setPluBatch(String pluBatch) {
        PluBatch = pluBatch;
    }

    public String getSelfCode() {
        return SelfCode;
    }

    public void setSelfCode(String selfCode) {
        SelfCode = selfCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public int getCheckNum() {
        return CheckNum;
    }

    public void setCheckNum(int checkNum) {
        CheckNum = checkNum;
    }

    public int getStockNum() {
        return StockNum;
    }

    public void setStockNum(int stockNum) {
        StockNum = stockNum;
    }

    public int getReferenceNum() {
        return ReferenceNum;
    }

    public void setReferenceNum(int referenceNum) {
        ReferenceNum = referenceNum;
    }

    public float getBuyPrice() {
        return BuyPrice;
    }

    public void setBuyPrice(float buyPrice) {
        BuyPrice = buyPrice;
    }

    public float getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(float salePrice) {
        SalePrice = salePrice;
    }

    public String getGoodsSeat() {
        return GoodsSeat;
    }

    public void setGoodsSeat(String goodsSeat) {
        GoodsSeat = goodsSeat;
    }

    public String getMadeDate() {
        return MadeDate;
    }

    public void setMadeDate(String madeDate) {
        MadeDate = madeDate;
    }

    public String getVaildDate() {
        return VaildDate;
    }

    public void setVaildDate(String vaildDate) {
        VaildDate = vaildDate;
    }

    public String getEnable_batch() {
        return Enable_batch;
    }

    public void setEnable_batch(String enable_batch) {
        Enable_batch = enable_batch;
    }
}
