package com.eshop.jinxiaocun.othermodel.bean;

/**
 * @Author Lu An
 * 创建时间  2018/9/12 0012
 * 描述
 */

public class GoodsPiciInfoBeanResult {

    private String item_barcode;// 批次号
    private String produce_date; //生产日期
    private String valid_date;//有效日期
    private int stock_qty; //数量

    public String getItem_barcode() {
        return item_barcode;
    }

    public void setItem_barcode(String item_barcode) {
        this.item_barcode = item_barcode;
    }

    public String getProduce_date() {
        return produce_date;
    }

    public void setProduce_date(String produce_date) {
        this.produce_date = produce_date;
    }

    public String getValid_date() {
        return valid_date;
    }

    public void setValid_date(String valid_date) {
        this.valid_date = valid_date;
    }

    public int getStock_qty() {
        return stock_qty;
    }

    public void setStock_qty(int stock_qty) {
        this.stock_qty = stock_qty;
    }
}
