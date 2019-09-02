package com.eshop.jinxiaocun.othermodel.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * @Author 安仔夏天勤奋
 * Create Date is  2019/9/2
 * Des
 */
public class SaleQueryBean extends BaseBean {

    public SaleQueryJsonData JsonData;
    public SaleQueryBean (){
        setStrCmd(WebConfig.GETSALEQUERY);
        JsonData = new SaleQueryJsonData();
    }

    public class SaleQueryJsonData{
        public String as_branchno; //机构号
        public String oper_id;//操作员
        public String start_date; //开始时间2019-07-11 08:00
        public String end_date;//结束时间2019-07-11 18:30
        public String flow_no; //小票号
        public int PerNum; //每页显示数量
        public int PageNum;//页码
    }
}
