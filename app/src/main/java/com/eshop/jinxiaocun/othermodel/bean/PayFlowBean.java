package com.eshop.jinxiaocun.othermodel.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/7/20
 * Desc:  按单号获取付款记录的参数
 */
public class PayFlowBean extends BaseBean {

    public PayFlow JsonData;

    public PayFlowBean(){
        setStrCmd(WebConfig.GetPayFlow);
        JsonData = new PayFlow();
    }

    public class PayFlow{
        public String as_branchNo;// 门店机构
        public String as_flowno;//单号
    }

}
