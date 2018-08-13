package com.eshop.jinxiaocun.xiaoshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

import java.util.List;

public class GoodGetBeanResult extends BaseResult{

    public List<GoodGetBeanJson> JsonData;
    
    public class GoodGetBeanJson{
        public String item_no;//商品编码
        public String item_name;//商品名称
        public String item_subno;  //自编码
        public String item_size;    //规格
        public String unit_no;    //单位
        public String item_clsno;  //类别
        public String main_supcust; //主供应商
        public String Price;        //进价
        public String sale_price;//售价
        public String base_price;//批发价
        public String vip_price;//会员价
        public String stock_qty;//库存
        public String item_rem;  //助记码
        public String item_pricetype; //计价方式0普通,1记重2计数
        public String sale_min_price; //最低售价
        public String enable_discount; //前台打折；1/0 1允许0不允许
        public String change_price;//前台议价
        public String enable_batch; //是否为批次商品
    }
    
   


}
