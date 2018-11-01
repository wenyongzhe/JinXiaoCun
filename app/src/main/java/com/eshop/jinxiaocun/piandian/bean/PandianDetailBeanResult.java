package com.eshop.jinxiaocun.piandian.bean;

import java.io.Serializable;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/8/29
 * Desc:
 */

public class PandianDetailBeanResult implements Serializable{

    private String branch_no; // 盘点仓库
    private String item_no;//商品编码
    private String item_name;//名称
    private String item_size;//规格
    private String unit_no; //单位
    private float in_price;//盘点价格
    private float sale_price;//销售价格
    private int stock_qty; //库存数量
    private int check_qty;//盘点数量
    private int balance_qty; //差异数量
    private String item_barcode;//批次号
    private String produce_date; //生产日期
    private String valid_date;//有效日期

    public String getBranch_no() {
        return branch_no;
    }

    public void setBranch_no(String branch_no) {
        this.branch_no = branch_no;
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

    public String getItem_size() {
        return item_size;
    }

    public void setItem_size(String item_size) {
        this.item_size = item_size;
    }

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
    }

    public float getIn_price() {
        return in_price;
    }

    public void setIn_price(float in_price) {
        this.in_price = in_price;
    }

    public float getSale_price() {
        return sale_price;
    }

    public void setSale_price(float sale_price) {
        this.sale_price = sale_price;
    }

    public int getStock_qty() {
        return stock_qty;
    }

    public void setStock_qty(int stock_qty) {
        this.stock_qty = stock_qty;
    }

    public int getCheck_qty() {
        return check_qty;
    }

    public void setCheck_qty(int check_qty) {
        this.check_qty = check_qty;
    }

    public int getBalance_qty() {
        return balance_qty;
    }

    public void setBalance_qty(int balance_qty) {
        this.balance_qty = balance_qty;
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
