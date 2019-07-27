package com.eshop.jinxiaocun.othermodel.bean;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/7/27
 * Desc:  销售记录
 */
public class SaleFlowRecordResult {

    private String flow_no;//单据号
    private String item_no; //商品编码
    private String item_name;//商品名称
    private int sale_qnty;//数量
    private float sale_price; //销售价
    private float sale_money; //销售金额
    private float source_price;//源价
    private String sell_way; //-销售方式: A销售 B退货 D赠送
    private String item_barcode;//批次号
    private String produce_date;//生产期
    private String valid_date;//有效期

    public String getFlow_no() {
        return flow_no;
    }

    public void setFlow_no(String flow_no) {
        this.flow_no = flow_no;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getSale_qnty() {
        return sale_qnty;
    }

    public void setSale_qnty(int sale_qnty) {
        this.sale_qnty = sale_qnty;
    }

    public float getSale_price() {
        return sale_price;
    }

    public void setSale_price(float sale_price) {
        this.sale_price = sale_price;
    }

    public float getSale_money() {
        return sale_money;
    }

    public void setSale_money(float sale_money) {
        this.sale_money = sale_money;
    }

    public float getSource_price() {
        return source_price;
    }

    public void setSource_price(float source_price) {
        this.source_price = source_price;
    }

    public String getSell_way() {
        return sell_way;
    }

    public void setSell_way(String sell_way) {
        this.sell_way = sell_way;
    }

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
}
