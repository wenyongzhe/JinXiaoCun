package com.eshop.jinxiaocun.othermodel.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * @Author Lu An
 * 创建时间  2018/10/9 0009
 * 描述
 */

public class SheetCheckBean extends BaseBean{

    public SheetCheckBean.SheetCheck JsonData;

    public SheetCheckBean(){
        setStrCmd(WebConfig.SheetCheck);
        JsonData = new SheetCheckBean.SheetCheck();
    }

    public class SheetCheck {
        public String trans_no;  //单据类型
        public String branchNo; //门店机构
        public String Sheet_No;//单据号
        public String oper_id; //操作员
    }


}
