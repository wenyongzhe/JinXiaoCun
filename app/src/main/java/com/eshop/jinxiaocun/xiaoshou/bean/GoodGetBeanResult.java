package com.eshop.jinxiaocun.xiaoshou.bean;

public class GoodGetBeanResult {

    private String item_no;//商品编码
    private String item_name;//商品名称
    private String item_subno;  //自编码
    private String item_size;    //规格
    private String unit_no;    //单位
    private String item_clsno;  //类别
    private String main_supcust; //主供应商
    private String Price;        //进价
    private String sale_price;//售价
    private String base_price;//批发价
    private String vip_price;//会员价
    private String stock_qty;//库存
    private String item_rem;  //助记码
    private String item_pricetype; //计价方式0普通,1记重2计数
    private String sale_min_price; //最低售价
    private String enable_discount; //前台打折；1/0 1允许0不允许
    private String change_price;//前台议价
    private String enable_batch; //是否为批次商品


    public String getItem_no() {
        return item_no;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_subno() {
        return item_subno;
    }

    public String getItem_size() {
        return item_size;
    }

    public String getUnit_no() {
        return unit_no;
    }

    public String getItem_clsno() {
        return item_clsno;
    }

    public String getMain_supcust() {
        return main_supcust;
    }

    public String getPrice() {
        return Price;
    }

    public String getSale_price() {
        return sale_price;
    }

    public String getBase_price() {
        return base_price;
    }

    public String getVip_price() {
        return vip_price;
    }

    public String getStock_qty() {
        return stock_qty;
    }

    public String getItem_rem() {
        return item_rem;
    }

    public String getItem_pricetype() {
        return item_pricetype;
    }

    public String getSale_min_price() {
        return sale_min_price;
    }

    public String getEnable_discount() {
        return enable_discount;
    }

    public String getChange_price() {
        return change_price;
    }

    public String getEnable_batch() {
        return enable_batch;
    }
}
