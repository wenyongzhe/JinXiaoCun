package com.eshop.jinxiaocun.othermodel.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * Author: 安仔夏天勤奋
 *  * Date: 2019/8/4
 *  * Desc: 销售查询参数
 */
public class SalesCheckBean extends BaseBean {

    public SalesCheck JsonData;

    public SalesCheckBean(){
        setStrCmd(WebConfig.GetSellRep);
        JsonData = new SalesCheck();
    }

    public class SalesCheck{
        public String BranchNo;// 门店机构
        public String POSId;//
        public String UserId;//操作员
        public String BeginTime;//起始时间
        public String EndTime;//结束时间
    }
}
