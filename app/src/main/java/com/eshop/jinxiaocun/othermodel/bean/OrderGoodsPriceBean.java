package com.eshop.jinxiaocun.othermodel.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * @Author Lu An
 * 创建时间  2018/10/11 0011
 * 描述
 */

public class OrderGoodsPriceBean extends BaseBean {
    public OrderGoodsPriceBean.OrderGoodsPrice JsonData;

    public OrderGoodsPriceBean(){
        setStrCmd(WebConfig.QrySheetPluPrice);
        JsonData = new OrderGoodsPriceBean.OrderGoodsPrice();
    }

    public class OrderGoodsPrice{
        public String PosId;
        public String SheetType; //单据类型
        public String branchNo;
        public String d_branchNo;
        public String as_itemNo;//商品编码
        public String oper_id; //操作员
        public String loginbranch_no;//登陆机构
        public String supcust_no;//供应商或客户编码
    }


}
