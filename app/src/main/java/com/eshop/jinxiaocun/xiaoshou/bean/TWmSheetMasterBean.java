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

    @DatabaseField(columnName = "approve_flag")
    private String approve_flag;

    @DatabaseField(columnName = "paid_order")
    private String paid_order;

    @DatabaseField(columnName = "oper_id")
    private String oper_id;

    @DatabaseField(columnName = "order_man")
    private String order_man;

    @DatabaseField(columnName = "confirm_man")
    private String confirm_man;

    @DatabaseField(columnName = "oper_date")
    private String oper_date;

    @DatabaseField(columnName = "work_date")
    private String work_date;

    @DatabaseField(columnName = "valid_date")
    private String valid_date;

    @DatabaseField(columnName = "pay_date")
    private String pay_date;

    @DatabaseField(columnName = "trans_flag")
    private String trans_flag;

    @DatabaseField(columnName = "acct_flag")
    private String acct_flag;
    @DatabaseField(columnName = "sheet_amt")
    private String sheet_amt;
    @DatabaseField(columnName = "branch_flag")
    private String branch_flag;
    @DatabaseField(columnName = "com_flag")
    private String com_flag;
    @DatabaseField(columnName = "memo")
    private String memo;
    @DatabaseField(columnName = "print_num")
    private String print_num;
    @DatabaseField(columnName = "modified_id")
    private String modified_id;
    @DatabaseField(columnName = "modified_date")
    private String modified_date;
    @DatabaseField(columnName = "other")
    private String other;
    @DatabaseField(columnName = "Company_no")
    private String Company_no;
    @DatabaseField(columnName = "send_no")
    private String send_no;
    @DatabaseField(columnName = "car_id")
    private String car_id;
    @DatabaseField(columnName = "send_man")
    private String send_man;
    @DatabaseField(columnName = "other1")
    private String other1;

    @DatabaseField(columnName = "time_stamp")
    private String time_stamp;
    @DatabaseField(columnName = "audit_status")
    private String audit_status;

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

    public String getApprove_flag() {
        return approve_flag;
    }

    public void setApprove_flag(String approve_flag) {
        this.approve_flag = approve_flag;
    }

    public String getPaid_order() {
        return paid_order;
    }

    public void setPaid_order(String paid_order) {
        this.paid_order = paid_order;
    }

    public String getOper_id() {
        return oper_id;
    }

    public void setOper_id(String oper_id) {
        this.oper_id = oper_id;
    }

    public String getOrder_man() {
        return order_man;
    }

    public void setOrder_man(String order_man) {
        this.order_man = order_man;
    }

    public String getConfirm_man() {
        return confirm_man;
    }

    public void setConfirm_man(String confirm_man) {
        this.confirm_man = confirm_man;
    }

    public String getOper_date() {
        return oper_date;
    }

    public void setOper_date(String oper_date) {
        this.oper_date = oper_date;
    }

    public String getWork_date() {
        return work_date;
    }

    public void setWork_date(String work_date) {
        this.work_date = work_date;
    }

    public String getValid_date() {
        return valid_date;
    }

    public void setValid_date(String valid_date) {
        this.valid_date = valid_date;
    }

    public String getPay_date() {
        return pay_date;
    }

    public void setPay_date(String pay_date) {
        this.pay_date = pay_date;
    }

    public String getTrans_flag() {
        return trans_flag;
    }

    public void setTrans_flag(String trans_flag) {
        this.trans_flag = trans_flag;
    }

    public String getAcct_flag() {
        return acct_flag;
    }

    public void setAcct_flag(String acct_flag) {
        this.acct_flag = acct_flag;
    }

    public String getSheet_amt() {
        return sheet_amt;
    }

    public void setSheet_amt(String sheet_amt) {
        this.sheet_amt = sheet_amt;
    }

    public String getBranch_flag() {
        return branch_flag;
    }

    public void setBranch_flag(String branch_flag) {
        this.branch_flag = branch_flag;
    }

    public String getCom_flag() {
        return com_flag;
    }

    public void setCom_flag(String com_flag) {
        this.com_flag = com_flag;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPrint_num() {
        return print_num;
    }

    public void setPrint_num(String print_num) {
        this.print_num = print_num;
    }

    public String getModified_id() {
        return modified_id;
    }

    public void setModified_id(String modified_id) {
        this.modified_id = modified_id;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getCompany_no() {
        return Company_no;
    }

    public void setCompany_no(String company_no) {
        Company_no = company_no;
    }

    public String getSend_no() {
        return send_no;
    }

    public void setSend_no(String send_no) {
        this.send_no = send_no;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getSend_man() {
        return send_man;
    }

    public void setSend_man(String send_man) {
        this.send_man = send_man;
    }

    public String getOther1() {
        return other1;
    }

    public void setOther1(String other1) {
        this.other1 = other1;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }
}
