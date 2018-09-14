package com.eshop.jinxiaocun.lingshou.bean;

public class PlayFlowBean{
    private String branch_no;//1001” //门店号
    private String flow_no;//””//小票号
    private int flow_id;//”1 //流水号
    private double sale_amount;//;//10 //销售金额
    private String pay_way;//”//支付方式
    private String sell_way;//A” //A:正常 B:退货
    private int card_no;//1 //卡号
    private int vip_no;//1 //会员号
    private String coin_no;//RMB” //货币标识 RMB
    private double coin_rate;//0.8 //汇率
    private double pay_amount;//” //付款金额
    private String voucher_no;//”//引用单号
    private String posid;//”//POS机号
    private String counter_no;//”//柜台号
    private String oper_id;//”//操作员iD
    private String sale_man;//” //营业员
    private String shift_no;//” //班次号
    private String oper_date;//” //操作时间 2018-07-25 12:10:11
    private String memo;//” //备注
    private String worderno;//”//微订单号
    private String bDealFlag;//0 //表示该单结束标识  1：结束

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

    public int getFlow_id() {
        return flow_id;
    }

    public void setFlow_id(int flow_id) {
        this.flow_id = flow_id;
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

    public String getSell_way() {
        return sell_way;
    }

    public void setSell_way(String sell_way) {
        this.sell_way = sell_way;
    }

    public int getCard_no() {
        return card_no;
    }

    public void setCard_no(int card_no) {
        this.card_no = card_no;
    }

    public int getVip_no() {
        return vip_no;
    }

    public void setVip_no(int vip_no) {
        this.vip_no = vip_no;
    }

    public String getCoin_no() {
        return coin_no;
    }

    public void setCoin_no(String coin_no) {
        this.coin_no = coin_no;
    }


    public void setCoin_rate(float coin_rate) {
        this.coin_rate = coin_rate;
    }

    public double getSale_amount() {
        return sale_amount;
    }

    public void setSale_amount(double sale_amount) {
        this.sale_amount = sale_amount;
    }

    public double getCoin_rate() {
        return coin_rate;
    }

    public void setCoin_rate(double coin_rate) {
        this.coin_rate = coin_rate;
    }

    public double getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(double pay_amount) {
        this.pay_amount = pay_amount;
    }

    public void setPay_amount(float pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getVoucher_no() {
        return voucher_no;
    }

    public void setVoucher_no(String voucher_no) {
        this.voucher_no = voucher_no;
    }

    public String getPosid() {
        return posid;
    }

    public void setPosid(String posid) {
        this.posid = posid;
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

    public String getSale_man() {
        return sale_man;
    }

    public void setSale_man(String sale_man) {
        this.sale_man = sale_man;
    }

    public String getShift_no() {
        return shift_no;
    }

    public void setShift_no(String shift_no) {
        this.shift_no = shift_no;
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

    public String getWorderno() {
        return worderno;
    }

    public void setWorderno(String worderno) {
        this.worderno = worderno;
    }

    public String getbDealFlag() {
        return bDealFlag;
    }

    public void setbDealFlag(String bDealFlag) {
        this.bDealFlag = bDealFlag;
    }
}
