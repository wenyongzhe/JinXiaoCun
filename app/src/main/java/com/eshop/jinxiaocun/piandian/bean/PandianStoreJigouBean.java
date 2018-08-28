package com.eshop.jinxiaocun.piandian.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * @Author Lu An
 * 创建时间  2018/8/28 0028
 * 描述
 */

public class PandianStoreJigouBean extends BaseBean {

    public PandianStoreJigouBean.PandianStoreJigouJsonData JsonData;
    public PandianStoreJigouBean() {
        setStrCmd(WebConfig.R_PD_BRANCH);
        JsonData = new PandianStoreJigouBean.PandianStoreJigouJsonData();
    }
    public class PandianStoreJigouJsonData {
        public String as_branchNo;//门店号
        public String as_posId; //POS ID
        public String trans_no;//PI 单据类型
        public String branch_type; //
    }
}
