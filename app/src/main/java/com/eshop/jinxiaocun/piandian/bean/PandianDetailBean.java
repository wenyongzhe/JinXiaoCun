package com.eshop.jinxiaocun.piandian.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/8/29
 * Desc:
 */

public class PandianDetailBean extends BaseBean {

    public PandianDetailBean.PandianDetailJsonData JsonData;

    public PandianDetailBean(){
        setStrCmd(WebConfig.R_PD_DETAIL);
        JsonData = new PandianDetailBean.PandianDetailJsonData();
    }

    public class PandianDetailJsonData{
        public String sheet_no;//盘点批号
        public int perNum;//每页取的数量
        public int pageNum;//页码
    }


}
