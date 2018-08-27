package com.eshop.jinxiaocun.piandian.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * @Author Lu An
 * 创建时间  2018/8/27 0027
 * 描述
 */

public class PandianLeibieBean extends BaseBean {
    public PandianLeibieBean.PandianLeibieJsonData JsonData;
    public PandianLeibieBean() {
        setStrCmd(WebConfig.R_PD_CLS);
        JsonData = new PandianLeibieBean.PandianLeibieJsonData();
    }
    public class PandianLeibieJsonData {
        public String as_branchNo;////门店号
        public String as_posId;////POS ID
        public String as_type;//'1'类别 '0' 品牌
        public String as_clsorbrno;//指定的类型或者品牌
    }
}
