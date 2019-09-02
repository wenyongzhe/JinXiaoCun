package com.eshop.jinxiaocun.othermodel.bean;

/**
 * @Author 安仔夏天勤奋
 * Create Date is  2019/9/2
 * Des  查询销售记录
 */
public class SaleQueryBeanResult {

    private int totpage;//总页数
    private String flow_no;//小票号
    private int flow_id;//单内序号
    private String item_no;//商品编码
    private String item_name;//商品名称
    private int sale_qnty;//销售数量
    private float sale_price;//销售价格
    private float sale_money;//销售金额
    private float source_price;//原价
    private String sell_way;//A,B
    private String sell_way_name;//销售方式名称
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

    public int getFlow_id() {
        return flow_id;
    }

    public void setFlow_id(int flow_id) {
        this.flow_id = flow_id;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getSale_qnty() {
        return sale_qnty;
    }

    public void setSale_qnty(int sale_qnty) {
        this.sale_qnty = sale_qnty;
    }

    public float getSale_price() {
        return sale_price;
    }

    public void setSale_price(float sale_price) {
        this.sale_price = sale_price;
    }

    public float getSale_money() {
        return sale_money;
    }

    public void setSale_money(float sale_money) {
        this.sale_money = sale_money;
    }

    public float getSource_price() {
        return source_price;
    }

    public void setSource_price(float source_price) {
        this.source_price = source_price;
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

    public String getOper_date() {
        return oper_date;
    }

    public void setOper_date(String oper_date) {
        this.oper_date = oper_date;
    }
}
