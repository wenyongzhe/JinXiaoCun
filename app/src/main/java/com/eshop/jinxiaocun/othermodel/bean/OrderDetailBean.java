package com.eshop.jinxiaocun.othermodel.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * @Author Lu An
 * 创建时间  2018/10/10 0010
 * 描述
 */

public class OrderDetailBean extends BaseBean {

    public OrderDetailBean.OrderDetail JsonData;

    public OrderDetailBean(){
        setStrCmd(WebConfig.QrySheetDetail);
        JsonData = new OrderDetailBean.OrderDetail();
    }

    public class OrderDetail{
        public String PosId;
        public String UserId; //用户ID
        public String SheetType;//单据类型
        public String SheetNo;//单据号
        public String cVoucher_Type;  //+ 引单明细
    }

}
