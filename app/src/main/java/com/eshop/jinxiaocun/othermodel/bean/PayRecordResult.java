package com.eshop.jinxiaocun.othermodel.bean;

import java.io.Serializable;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/7/20
 * Desc:
 */
public class PayRecordResult implements Serializable {

    private String flow_no;//单号
    private String sell_way; //-销售方式: A销售 B退货 D赠送
    private float sale_amount;//销售金额
    private String pay_way;//付款方式
    private String pay_name;//付款方式名称
    private float pay_amount; //付款金额
    private float coin_rate;//汇率
    private String card_no;//付款卡(券)号
    private String vip_no;//会员卡号
    private String sale_man;//营业员
    private String oper_date;//操作时间
    private String memo;//备注

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

    public float getSale_amount() {
        return sale_amount;
    }

    public void setSale_amount(float sale_amount) {
        this.sale_amount = sale_amount;
    }

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

    public float getCoin_rate() {
        return coin_rate;
    }

    public void setCoin_rate(float coin_rate) {
        this.coin_rate = coin_rate;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getVip_no() {
        return vip_no;
    }

    public void setVip_no(String vip_no) {
        this.vip_no = vip_no;
    }

    public String getSale_man() {
        return sale_man;
    }

    public void setSale_man(String sale_man) {
        this.sale_man = sale_man;
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
