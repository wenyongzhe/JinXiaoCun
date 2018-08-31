package com.eshop.jinxiaocun.base.bean;

public class GetClassPluResult extends ListBean{

    private String item_no;//" :”1000”   //商品编码
    private String item_name;// ”名称”  //商品名称
    private String item_subno;// ””    //自编码
    private String item_size;//””    //规格
    private String unit_no;//””     //单位
    private String item_clsno;//””  //类别
    private String main_supcust;//”” //主供应商
    private String Price;//10         //进价
    private String sale_price;//20 …..//售价
    private String base_price;//30 …//批发价
    private String vip_price;//28 ..//会员价
    private String stock_qty;//30..//库存
    private String item_rem;//””  //助记码
    private String item_pricetype;//0 //计价方式0普通,1记重2计数
    private String sale_min_price;//1 //最低售价
    private String enable_discount;//1 //前台打折；1/0 1允许0不允许
    private String change_price;//1//前台议价
    private String enable_batch;//0 //是否为批次商品

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

    public String getItem_subno() {
        return item_subno;
    }

    public void setItem_subno(String item_subno) {
        this.item_subno = item_subno;
    }

    public String getItem_size() {
        return item_size;
    }

    public void setItem_size(String item_size) {
        this.item_size = item_size;
    }

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
    }

    public String getItem_clsno() {
        return item_clsno;
    }

    public void setItem_clsno(String item_clsno) {
        this.item_clsno = item_clsno;
    }

    public String getMain_supcust() {
        return main_supcust;
    }

    public void setMain_supcust(String main_supcust) {
        this.main_supcust = main_supcust;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getBase_price() {
        return base_price;
    }

    public void setBase_price(String base_price) {
        this.base_price = base_price;
    }

    public String getVip_price() {
        return vip_price;
    }

    public void setVip_price(String vip_price) {
        this.vip_price = vip_price;
    }

    public String getStock_qty() {
        return stock_qty;
    }

    public void setStock_qty(String stock_qty) {
        this.stock_qty = stock_qty;
    }

    public String getItem_rem() {
        return item_rem;
    }

    public void setItem_rem(String item_rem) {
        this.item_rem = item_rem;
    }

    public String getItem_pricetype() {
        return item_pricetype;
    }

    public void setItem_pricetype(String item_pricetype) {
        this.item_pricetype = item_pricetype;
    }

    public String getSale_min_price() {
        return sale_min_price;
    }

    public void setSale_min_price(String sale_min_price) {
        this.sale_min_price = sale_min_price;
    }

    public String getEnable_discount() {
        return enable_discount;
    }

    public void setEnable_discount(String enable_discount) {
        this.enable_discount = enable_discount;
    }

    public String getChange_price() {
        return change_price;
    }

    public void setChange_price(String change_price) {
        this.change_price = change_price;
    }

    public String getEnable_batch() {
        return enable_batch;
    }

    public void setEnable_batch(String enable_batch) {
        this.enable_batch = enable_batch;
    }
}
