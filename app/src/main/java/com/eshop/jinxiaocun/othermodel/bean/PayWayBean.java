package com.eshop.jinxiaocun.othermodel.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * @Author 安仔夏天勤奋
 * Create Date is  2019/9/2
 * Des
 */
public class PayWayBean extends BaseBean {

    public PayWayJsonData JsonData;
    public PayWayBean (){
        setStrCmd(WebConfig.GETPAYWAY);
        JsonData = new PayWayJsonData();
    }

    public class PayWayJsonData{
        public String as_branchno; //机构号
        public String oper_id;//操作员
        public String start_date; //开始时间2019-07-11 08:00
        public String end_date;//结束时间2019-07-11 18:30
    }

}
