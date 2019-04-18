package com.eshop.jinxiaocun.huiyuan.bean;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/18
 * Desc:
 */

public class ExpenseCheckResultItem {

    private String branch_no;  //机构编码
    private String branch_name;  //机构名称
    private String flow_no;  //小票号
    private float Amount; //付款金额（储值卡）
    private String pay_time;  //-付款时间
    private String cashier_id; //--收银员
    private String memo;//--备注

    public String getBranch_no() {
        return branch_no;
    }

    public void setBranch_no(String branch_no) {
        this.branch_no = branch_no;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getFlow_no() {
        return flow_no;
    }

    public void setFlow_no(String flow_no) {
        this.flow_no = flow_no;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getCashier_id() {
        return cashier_id;
    }

    public void setCashier_id(String cashier_id) {
        this.cashier_id = cashier_id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
