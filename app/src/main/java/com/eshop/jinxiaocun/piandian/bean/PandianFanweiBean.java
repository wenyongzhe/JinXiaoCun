package com.eshop.jinxiaocun.piandian.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * @Author Lu An
 * 创建时间  2018/8/27 0027
 * 描述
 */

public class PandianFanweiBean extends BaseBean {
    public PandianFanweiBean.PandianFanweiJsonData JsonData;
    public PandianFanweiBean() {
        setStrCmd(WebConfig.R_PD_RANGE);
        JsonData = new PandianFanweiBean.PandianFanweiJsonData();
    }
    public class PandianFanweiJsonData {
//        public String BeginTime;//开始时间
//        public String EndTime;//结束时间
//        public String CheckFlag;//审核标志
//        public String PageNum;//每页数量
//        public String Page; //页码


    }

}
