package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.base.bean.BaseResult;
import com.eshop.jinxiaocun.utils.WebConfig;

public class GetPluPriceBeanResult extends BaseResult {

    private String branch_no;//0001” //门店编码
    private String flow_no;//”2018070600001”//小票号
    private String flow_id;//”” //小票流水
    private String source_price;// 10  //原价
    private String sale_price;// 8   //销售价
    private String sale_qnty;//1    //数量
    private String sale_money;//8  //销售金额
    private String sell_way;//”A”  //A正常销售 B：退货
    private String sale_man;//”” //营业员
    private String spec_flag;//””   //特价标识
    private String posid;//”1001” …..//POS ID
    private String spec_sheet_no;//”” …//特价单号
    private String voucher_no;//”” ..//引单号
    private String counter_no;//””..//柜台
    private String oper_id;//””  //操作员
    private String oper_date;//”” //操作时间
    private String item_no;//1000”   //商品编码
    private String item_name;  //商品名称
    private String item_subno;//””     //助记码
    private String item_size;//””       //规格
    private String unit_no;//””        //单位
    private String item_clsno;//””    //类别
    private String main_supcust;//””//主供应商
    private String Price;//8 //进价
    private String base_price;//9 //批发价
    private String vip_price;//9 //会员价
    private String stock_qty;//10 //库存数量
    private String item_rem;//”” //助记码
    private String item_pricetype;//0//计价方式0 普通,1 记重,2计数
    private String sale_min_price;//10 //最低售价
    private String enable_discount;//0 //前台打折 1允许0 不允许
    private String change_price;//0 //前台议价1允许0 不允

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

    public String getFlow_id() {
        return flow_id;
    }

    public void setFlow_id(String flow_id) {
        this.flow_id = flow_id;
    }

    public String getSource_price() {
        return source_price;
    }

    public void setSource_price(String source_price) {
        this.source_price = source_price;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getSale_qnty() {
        return sale_qnty;
    }

    public void setSale_qnty(String sale_qnty) {
        this.sale_qnty = sale_qnty;
    }

    public String getSale_money() {
        return sale_money;
    }

    public void setSale_money(String sale_money) {
        this.sale_money = sale_money;
    }

    public String getSell_way() {
        return sell_way;
    }

    public void setSell_way(String sell_way) {
        this.sell_way = sell_way;
    }

    public String getSale_man() {
        return sale_man;
    }

    public void setSale_man(String sale_man) {
        this.sale_man = sale_man;
    }

    public String getSpec_flag() {
        return spec_flag;
    }

    public void setSpec_flag(String spec_flag) {
        this.spec_flag = spec_flag;
    }

    public String getPosid() {
        return posid;
    }

    public void setPosid(String posid) {
        this.posid = posid;
    }

    public String getSpec_sheet_no() {
        return spec_sheet_no;
    }

    public void setSpec_sheet_no(String spec_sheet_no) {
        this.spec_sheet_no = spec_sheet_no;
    }

    public String getVoucher_no() {
        return voucher_no;
    }

    public void setVoucher_no(String voucher_no) {
        this.voucher_no = voucher_no;
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

    public String getOper_date() {
        return oper_date;
    }

    public void setOper_date(String oper_date) {
        this.oper_date = oper_date;
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
}
