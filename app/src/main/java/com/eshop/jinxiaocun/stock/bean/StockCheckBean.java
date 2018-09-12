package com.eshop.jinxiaocun.stock.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * @Author Lu An
 * 创建时间  2018/9/12 0012
 * 描述
 */

public class StockCheckBean extends BaseBean{

    public StockCheckBean.StockCheckJsonData JsonData;

    public StockCheckBean(){
        setStrCmd(WebConfig.QryPluStock);
        JsonData = new StockCheckBean.StockCheckJsonData();
    }

    public class StockCheckJsonData{
        public String BarCode; //编码
        public String BranchNo; //机构号
        public String UserId; //操作员
    }

}
