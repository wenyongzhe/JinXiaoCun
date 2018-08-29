package com.eshop.jinxiaocun.piandian.bean;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/8/29
 * Desc:
 */

public class PandianPihaoHuoquBeanResult {

    private String sheet_no;//盘点批次号
    private String branch_no;//门店号
    private String branch_name;//门店号
    private String oper_range; //盘点范围
    private String oper_range_name; //盘点范围名称
    private String check_cls;//盘点类别
    private String oper_id; //操作员ID
    private String oper_date;//操作日期
    private String memo; //备注

    public String getSheet_no() {
        return sheet_no;
    }

    public void setSheet_no(String sheet_no) {
        this.sheet_no = sheet_no;
    }

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

    public String getOper_range() {
        return oper_range;
    }

    public void setOper_range(String oper_range) {
        this.oper_range = oper_range;
    }

    public String getOper_range_name() {
        return oper_range_name;
    }

    public void setOper_range_name(String oper_range_name) {
        this.oper_range_name = oper_range_name;
    }

    public String getCheck_cls() {
        return check_cls;
    }

    public void setCheck_cls(String check_cls) {
        this.check_cls = check_cls;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
