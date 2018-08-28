package com.eshop.jinxiaocun.piandian.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * @Author Lu An
 * 创建时间  2018/8/28 0028
 * 描述
 */

public class PandianPihaoCreateBean extends BaseBean {

    public PandianPihaoCreateBean.PandianPihaoCreateJsonData JsonData;
    public PandianPihaoCreateBean() {
        setStrCmd(WebConfig.RT_ASK_BATCH);
        JsonData = new PandianPihaoCreateBean.PandianPihaoCreateJsonData();
    }
    public class PandianPihaoCreateJsonData {
        public String as_sheetno;//盘点批次号
        public String as_branch_no;//门店号
        public String as_oper_range;//盘点范围
        public String as_check_cls; //盘点类别
        public String as_oper_id; //操作员ID
        public String as_oper_date;//操作日期
        public String as_memo; //备注
    }
}
