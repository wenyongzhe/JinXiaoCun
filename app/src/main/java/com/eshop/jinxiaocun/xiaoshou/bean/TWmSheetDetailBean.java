package com.eshop.jinxiaocun.xiaoshou.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_wm_sheet_detail")
public class TWmSheetDetailBean {

    @DatabaseField(generatedId = true)
    private long flow_id;

    @DatabaseField(columnName = "sheet_no")
    private String sheet_no;

    @DatabaseField(columnName = "item_no")
    private String item_no;

    @DatabaseField(columnName = "orgi_price")
    private String orgi_price;

    @DatabaseField(columnName = "valid_price")
    private String valid_price;

    @DatabaseField(columnName = "order_qty")
    private String order_qty;

    @DatabaseField(columnName = "real_qty")
    private String real_qty;

    @DatabaseField(columnName = "large_qty")
    private String large_qty;

    @DatabaseField(columnName = "sub_amt")
    private String sub_amt;

    @DatabaseField(columnName = "tax")
    private String tax;

    @DatabaseField(columnName = "cost_price")
    private String cost_price;

    @DatabaseField(columnName = "send_qty")
    private String send_qty;

    @DatabaseField(columnName = "sale_price")
    private String sale_price;

    @DatabaseField(columnName = "memo")
    private String memo;
    @DatabaseField(columnName = "num1")
    private String num1;
    @DatabaseField(columnName = "memo1")
    private String memo1;
    @DatabaseField(columnName = "memo2")
    private String memo2;
    @DatabaseField(columnName = "row_id")
    private String row_id;
    @DatabaseField(columnName = "item_barcode")
    private String item_barcode;
    @DatabaseField(columnName = "produce_date")
    private String produce_date;
    @DatabaseField(columnName = "valid_date")
    private String valid_date;

    public long getFlow_id() {
        return flow_id;
    }

    public void setFlow_id(long flow_id) {
        this.flow_id = flow_id;
    }

    public String getSheet_no() {
        return sheet_no;
    }

    public void setSheet_no(String sheet_no) {
        this.sheet_no = sheet_no;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getOrgi_price() {
        return orgi_price;
    }

    public void setOrgi_price(String orgi_price) {
        this.orgi_price = orgi_price;
    }

    public String getValid_price() {
        return valid_price;
    }

    public void setValid_price(String valid_price) {
        this.valid_price = valid_price;
    }

    public String getOrder_qty() {
        return order_qty;
    }

    public void setOrder_qty(String order_qty) {
        this.order_qty = order_qty;
    }

    public String getReal_qty() {
        return real_qty;
    }

    public void setReal_qty(String real_qty) {
        this.real_qty = real_qty;
    }

    public String getLarge_qty() {
        return large_qty;
    }

    public void setLarge_qty(String large_qty) {
        this.large_qty = large_qty;
    }

    public String getSub_amt() {
        return sub_amt;
    }

    public void setSub_amt(String sub_amt) {
        this.sub_amt = sub_amt;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getCost_price() {
        return cost_price;
    }

    public void setCost_price(String cost_price) {
        this.cost_price = cost_price;
    }

    public String getSend_qty() {
        return send_qty;
    }

    public void setSend_qty(String send_qty) {
        this.send_qty = send_qty;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public String getMemo1() {
        return memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1;
    }

    public String getMemo2() {
        return memo2;
    }

    public void setMemo2(String memo2) {
        this.memo2 = memo2;
    }

    public String getRow_id() {
        return row_id;
    }

    public void setRow_id(String row_id) {
        this.row_id = row_id;
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
