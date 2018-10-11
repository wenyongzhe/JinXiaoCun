package com.eshop.jinxiaocun.othermodel.bean;

/**
 * @Author Lu An
 * 创建时间  2018/10/11 0011
 * 描述
 */

public class OrderGoodsPriceBeanResult {

    private String BarCode;//编码
    private float price;          //批发价格
    private float BuyPrice;         //进价
    private float SalePrice;       //销价
    private float vip_price; //vip

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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

    public float getVip_price() {
        return vip_price;
    }

    public void setVip_price(float vip_price) {
        this.vip_price = vip_price;
    }
}
