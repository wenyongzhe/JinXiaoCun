package com.eshop.jinxiaocun.piandian.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/8/29
 * Desc:
 */

public class PandianPihaoHuoquBean extends BaseBean {

    public PandianPihaoHuoquBean.PandianPihaoHuoquJsonData JsonData;

    public PandianPihaoHuoquBean(){
        setStrCmd(WebConfig.GetBatchNo);
        JsonData = new PandianPihaoHuoquBean.PandianPihaoHuoquJsonData();
    }

    public class PandianPihaoHuoquJsonData{
        public String sheet_no;//获取所有可填%
        public String trans_no;//单据标识
        public int PerNum;//每页显示数量
        public int PageNum;//页码
        public String approveflag;//审核标识
        public String branch_no; //机构号(保留)

    }

}
