package com.eshop.jinxiaocun.huiyuan.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/18
 * Desc:  消费查询参数
 */

public class ExpenseCheckBean extends BaseBean {

    public ExpenseCheckData JsonData;

    public ExpenseCheckBean() {
        setStrCmd(WebConfig.VipSaleQuery);
        JsonData = new ExpenseCheckData();
    }

    public class ExpenseCheckData {
        public String as_branchno; //机构编码
        public String vip_no;  //会员卡号
        public String start_date;//开始时间  2016-07-11 08:00
        public String end_date; //结束时间   2016-07-11 08:00

    }
}
