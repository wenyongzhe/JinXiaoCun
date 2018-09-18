package com.eshop.jinxiaocun.othermodel.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * @Author Lu An
 * 创建时间  2018/9/18 0018
 * 描述  客户信息参数
 */

public class CustomerInfoBean extends BaseBean {

    public CustomerInfoBean.CustomerInfo JsonData;

    public CustomerInfoBean() {
        setStrCmd(WebConfig.GetCustomerInfo);
        JsonData = new CustomerInfoBean.CustomerInfo();
    }

    public class CustomerInfo {
        public String POSId;
        public String UserId;  //操作员
        public String Type; // 1门店机构 2分部
        public String BranchNo;  //机构号
        public String SheetType; //单据类型
        public String zjm;   //助记码不生效
        public int Page; //
        public int PageNum;//
    }

}
