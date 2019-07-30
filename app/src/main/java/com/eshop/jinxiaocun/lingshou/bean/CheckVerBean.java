package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class CheckVerBean extends BaseBean{

    public CheckVerBeanJsonData JsonData;

    public CheckVerBean() {
        setStrCmd(WebConfig.CheckVer);
        JsonData = new CheckVerBeanJsonData();
    }

    public CheckVerBeanJsonData getJsonData() {
        return JsonData;
    }

    public void setJsonData(CheckVerBeanJsonData jsonData) {
        JsonData = jsonData;
    }

    public class CheckVerBeanJsonData {

        private String nowver;

        public String getNowver() {
            return nowver;
        }

        public void setNowver(String nowver) {
            this.nowver = nowver;
        }
    }

}
