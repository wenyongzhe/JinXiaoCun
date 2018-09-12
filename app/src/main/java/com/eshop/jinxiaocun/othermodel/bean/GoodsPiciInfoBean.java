package com.eshop.jinxiaocun.othermodel.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * @Author Lu An
 * 创建时间  2018/9/12 0012
 * 描述 获取商品批次信息参数
 */

public class GoodsPiciInfoBean extends BaseBean {

    public GoodsPiciInfoBean.GoodsPiciJsonData JsonData;

    public GoodsPiciInfoBean(){
        setStrCmd(WebConfig.R_PLU_BATCH);
        JsonData = new GoodsPiciInfoBean.GoodsPiciJsonData();
    }

    public class GoodsPiciJsonData{
        public String as_branchNo; //门店号
        public String as_posid; //机号
        public String as_item_no;//商品编码
    }

}
