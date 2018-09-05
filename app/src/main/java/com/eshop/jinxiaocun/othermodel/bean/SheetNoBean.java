package com.eshop.jinxiaocun.othermodel.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/5
 * Desc: 获取业务单据号  传参数
 */

public class SheetNoBean extends BaseBean {

    public SheetNoBean.SheetNoJsonDataBean JsonDate;
    public SheetNoBean(){
        setStrCmd(WebConfig.GetSheetNo);
        JsonDate = new SheetNoBean.SheetNoJsonDataBean();
    }

    public class SheetNoJsonDataBean{
        public String trans_no;//"PD"单据类型
        public String branch_no;//"0001"分支机构
    }

}
