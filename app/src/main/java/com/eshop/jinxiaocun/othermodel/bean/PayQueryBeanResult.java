package com.eshop.jinxiaocun.othermodel.bean;

/**
 * @Author 安仔夏天勤奋
 * Create Date is  2019/9/2
 * Des 查询付款记录
 */
public class PayQueryBeanResult {

    private int totpage;//总页数
    private String flow_no;//小票号
    private String sell_way; //A,B
    private String sell_way_name;//销售方式名称
    private String pay_way; //付款方式
    private String pay_way_name;//付款方式名称
    private float pay_amount;//付款金额
    private float total_amount;//整单金额
    private String oper_date;//日期时间

    public int getTotpage() {
        return totpage;
    }

    public void setTotpage(int totpage) {
        this.totpage = totpage;
    }

    public String getFlow_no() {
        return flow_no;
    }

    public void setFlow_no(String flow_no) {
        this.flow_no = flow_no;
    }

    public String getSell_way() {
        return sell_way;
    }

    public void setSell_way(String sell_way) {
        this.sell_way = sell_way;
    }

    public String getSell_way_name() {
        return sell_way_name;
    }

    public void setSell_way_name(String sell_way_name) {
        this.sell_way_name = sell_way_name;
    }

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public String getPay_way_name() {
        return pay_way_name;
    }

    public void setPay_way_name(String pay_way_name) {
        this.pay_way_name = pay_way_name;
    }

    public float getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(float pay_amount) {
        this.pay_amount = pay_amount;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }

    public String getOper_date() {
        return oper_date;
    }

    public void setOper_date(String oper_date) {
        this.oper_date = oper_date;
    }
}
