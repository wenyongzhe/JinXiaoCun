package com.eshop.jinxiaocun.base.bean;

public class SaleFlowBean extends BaseBean {

    private String branch_no;//:;//1001;// //门店号
    private String flow_no;//:””//小票号
    private String flow_id;//1 //流水号
    private String item_no;//:”” //编码
    private String source_price;//:10//原价
    private String sale_price;//:8 //销价
    private String sale_qnty;//:1 //数量
    private String sale_money;//:8 //金额
    private String sell_way;//:;//A;// //销售标识 A正常 B退货
    private String sale_man;//:””  //营业员
    private String spec_flag;//:”” //特价标识
    private String spec_sheet_no;//:””//特价单
    private String posid;//:””//POS机号
    private String voucher_no;//:””//引单号
    private String counter_no;//:””//柜台号
    private String oper_id;//:””//操作员iD
    private String oper_date;//:”” //操作时间 2018-07-25 12:10:11
    private String isfreshcodefrag;//:0 //是否为生鲜标识 1:是 0：否
    private String batch_code;//:””  //批次号
    private String batch_made_date;//:”” //批次生产日期
    private String batch_valid_date;//:””//批次有效期
    private String bDealFlag;//:0 //表示该单结束标识  1：结束

    public String getBranch_no() {
        return branch_no;
    }

    public void setBranch_no(String branch_no) {
        this.branch_no = branch_no;
    }

    public String getFlow_no() {
        return flow_no;
    }

    public void setFlow_no(String flow_no) {
        this.flow_no = flow_no;
    }

    public String getFlow_id() {
        return flow_id;
    }

    public void setFlow_id(String flow_id) {
        this.flow_id = flow_id;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getSource_price() {
        return source_price;
    }

    public void setSource_price(String source_price) {
        this.source_price = source_price;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getSale_qnty() {
        return sale_qnty;
    }

    public void setSale_qnty(String sale_qnty) {
        this.sale_qnty = sale_qnty;
    }

    public String getSale_money() {
        return sale_money;
    }

    public void setSale_money(String sale_money) {
        this.sale_money = sale_money;
    }

    public String getSell_way() {
        return sell_way;
    }

    public void setSell_way(String sell_way) {
        this.sell_way = sell_way;
    }

    public String getSale_man() {
        return sale_man;
    }

    public void setSale_man(String sale_man) {
        this.sale_man = sale_man;
    }

    public String getSpec_flag() {
        return spec_flag;
    }

    public void setSpec_flag(String spec_flag) {
        this.spec_flag = spec_flag;
    }

    public String getSpec_sheet_no() {
        return spec_sheet_no;
    }

    public void setSpec_sheet_no(String spec_sheet_no) {
        this.spec_sheet_no = spec_sheet_no;
    }

    public String getPosid() {
        return posid;
    }

    public void setPosid(String posid) {
        this.posid = posid;
    }

    public String getVoucher_no() {
        return voucher_no;
    }

    public void setVoucher_no(String voucher_no) {
        this.voucher_no = voucher_no;
    }

    public String getCounter_no() {
        return counter_no;
    }

    public void setCounter_no(String counter_no) {
        this.counter_no = counter_no;
    }

    public String getOper_id() {
        return oper_id;
    }

    public void setOper_id(String oper_id) {
        this.oper_id = oper_id;
    }

    public String getOper_date() {
        return oper_date;
    }

    public void setOper_date(String oper_date) {
        this.oper_date = oper_date;
    }

    public String getIsfreshcodefrag() {
        return isfreshcodefrag;
    }

    public void setIsfreshcodefrag(String isfreshcodefrag) {
        this.isfreshcodefrag = isfreshcodefrag;
    }

    public String getBatch_code() {
        return batch_code;
    }

    public void setBatch_code(String batch_code) {
        this.batch_code = batch_code;
    }

    public String getBatch_made_date() {
        return batch_made_date;
    }

    public void setBatch_made_date(String batch_made_date) {
        this.batch_made_date = batch_made_date;
    }

    public String getBatch_valid_date() {
        return batch_valid_date;
    }

    public void setBatch_valid_date(String batch_valid_date) {
        this.batch_valid_date = batch_valid_date;
    }

    public String getbDealFlag() {
        return bDealFlag;
    }

    public void setbDealFlag(String bDealFlag) {
        this.bDealFlag = bDealFlag;
    }
}
