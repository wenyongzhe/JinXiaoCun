package com.eshop.jinxiaocun.othermodel.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * @Author Lu An
 * 创建时间  2018/9/26 0026
 * 描述  保存业务单据 参数
 */

public class SheetSaveBean extends BaseBean {

    public SheetSaveBean.SheetSave JsonData;

    public SheetSaveBean(){
        setStrCmd(WebConfig.SheetSave);
        JsonData = new SheetSaveBean.SheetSave();
    }

    public class SheetSave {
        public String trans_no;  //单据类型
        public String branchNo; //门店机构
        public String Sheet_No;//单据号
        public String oper_id; //操作员
    }

}
