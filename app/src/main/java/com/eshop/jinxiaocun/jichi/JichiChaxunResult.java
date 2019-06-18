package com.eshop.jinxiaocun.jichi;

import java.io.Serializable;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/18
 * Desc:
 */

public class JichiChaxunResult implements Serializable{

    private String sheet_no;//xc0001” //单号
    private String cust_no ;//0001”   //客户号
    private String cust_name;//002” //客户名
    private String vip_id;//”:    “10001”  //
    private String tel_no ;//135”   //电话
    private String server_no ;//01” //服务项目ID
    private String server_name ;//游泳” //服务项目名称
    private String tot_money;// " :300 //总金额
    private String real_money;// " :300 //实际金额
    private String tot_num;// " :10 //总次数
    private String ret_num;//" :10 //剩余次数
    private String volid_date;//2019-06-30 12：00：00” //有效日期

    public String getSheet_no() {
        return sheet_no;
    }

    public void setSheet_no(String sheet_no) {
        this.sheet_no = sheet_no;
    }

    public String getCust_no() {
        return cust_no;
    }

    public void setCust_no(String cust_no) {
        this.cust_no = cust_no;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getVip_id() {
        return vip_id;
    }

    public void setVip_id(String vip_id) {
        this.vip_id = vip_id;
    }

    public String getTel_no() {
        return tel_no;
    }

    public void setTel_no(String tel_no) {
        this.tel_no = tel_no;
    }

    public String getServer_no() {
        return server_no;
    }

    public void setServer_no(String server_no) {
        this.server_no = server_no;
    }

    public String getServer_name() {
        return server_name;
    }

    public void setServer_name(String server_name) {
        this.server_name = server_name;
    }

    public String getTot_money() {
        return tot_money;
    }

    public void setTot_money(String tot_money) {
        this.tot_money = tot_money;
    }

    public String getReal_money() {
        return real_money;
    }

    public void setReal_money(String real_money) {
        this.real_money = real_money;
    }

    public String getTot_num() {
        return tot_num;
    }

    public void setTot_num(String tot_num) {
        this.tot_num = tot_num;
    }

    public String getRet_num() {
        return ret_num;
    }

    public void setRet_num(String ret_num) {
        this.ret_num = ret_num;
    }

    public String getVolid_date() {
        return volid_date;
    }

    public void setVolid_date(String volid_date) {
        this.volid_date = volid_date;
    }
}
