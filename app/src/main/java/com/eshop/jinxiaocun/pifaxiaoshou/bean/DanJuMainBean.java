package com.eshop.jinxiaocun.pifaxiaoshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class DanJuMainBean extends BaseBean{

    public DanJuJsonData JsonData;


    public DanJuMainBean() {
        setStrCmd(WebConfig.QrySheetHead);
        JsonData = new DanJuJsonData();
    }


    public class DanJuJsonData {
        public String BeginTime;//开始时间
        public String EndTime;//结束时间
        public String CheckFlag;//审核标志
        public String PageNum;//每页数量
        public String Page; //页码


    }

}
