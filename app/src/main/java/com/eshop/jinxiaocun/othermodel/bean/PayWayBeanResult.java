package com.eshop.jinxiaocun.othermodel.bean;

/**
 * @Author 安仔夏天勤奋
 * Create Date is  2019/9/2
 * Des  付款方式汇总
 */
public class PayWayBeanResult {
    private String pay_way; //付款方式
    private String pay_name;//付款方式名称
    private float pay_amount;//付款金额

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public float getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(float pay_amount) {
        this.pay_amount = pay_amount;
    }
}
