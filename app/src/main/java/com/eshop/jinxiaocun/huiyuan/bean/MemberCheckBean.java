package com.eshop.jinxiaocun.huiyuan.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/18
 * Desc:  会员查询参数
 */

public class MemberCheckBean extends BaseBean{

    public MemberCheckData JsonData;
    public MemberCheckBean() {
        setStrCmd(WebConfig.QryHuiYuanInfo);
        JsonData = new MemberCheckData();
    }
    public static class MemberCheckData{
        public String CardNo;//卡号
        public String BranchNo;//机构号
        public String UserId;//操作员

    }
}
