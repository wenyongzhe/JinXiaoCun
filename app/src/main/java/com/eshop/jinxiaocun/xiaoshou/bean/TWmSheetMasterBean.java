package com.eshop.jinxiaocun.xiaoshou.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_wm_sheet_master")
public class TWmSheetMasterBean{

    public TWmSheetMasterBean() {
    }

    @DatabaseField(columnName = "sheet_no")
    private String sheet_no;

    @DatabaseField(columnName = "trans_no")
    private String trans_no;

    @DatabaseField(columnName = "db_no")
    private String db_no;

    @DatabaseField(columnName = "voucher_no")
    private String voucher_no;

    @DatabaseField(columnName = "branch_no")
    private String branch_no;

    @DatabaseField(columnName = "supcust_no")
    private String supcust_no;

    @DatabaseField(columnName = "pay_way")
    private String pay_way;

    @DatabaseField(columnName = "discount")
    private String discount;

    @DatabaseField(columnName = "coin_no")
    private String coin_no;

    @DatabaseField(columnName = "paid_amt")
    private String paid_amt;

    @DatabaseField(columnName = "order_status")
    private String order_status;

    @DatabaseField(columnName = "oper_id")
    private String oper_id;





    public String getOper_id() {
        return oper_id;
    }

    public void setOper_id(String oper_id) {
        this.oper_id = oper_id;
    }


    public String getBranch_no() {
        return branch_no;
    }

    public void setBranch_no(String branch_no) {
        this.branch_no = branch_no;
    }

    public String getSupcust_no() {
        return supcust_no;
    }

    public void setSupcust_no(String supcust_no) {
        this.supcust_no = supcust_no;
    }

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCoin_no() {
        return coin_no;
    }

    public void setCoin_no(String coin_no) {
        this.coin_no = coin_no;
    }

    public String getPaid_amt() {
        return paid_amt;
    }

    public void setPaid_amt(String paid_amt) {
        this.paid_amt = paid_amt;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getSheet_no() {
        return sheet_no;
    }

    public void setSheet_no(String sheet_no) {
        this.sheet_no = sheet_no;
    }

    public String getTrans_no() {
        return trans_no;
    }

    public void setTrans_no(String trans_no) {
        this.trans_no = trans_no;
    }

    public String getDb_no() {
        return db_no;
    }

    public void setDb_no(String db_no) {
        this.db_no = db_no;
    }

    public String getVoucher_no() {
        return voucher_no;
    }

    public void setVoucher_no(String voucher_no) {
        this.voucher_no = voucher_no;
    }
}
