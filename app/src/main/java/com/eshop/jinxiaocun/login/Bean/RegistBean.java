package com.eshop.jinxiaocun.login.Bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.lingshou.bean.GetFlowNoBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class RegistBean extends BaseBean{

    private RegistJson JsonData;

    public RegistBean() {
        setStrCmd(WebConfig.POS_REG);
        JsonData = new RegistJson();
    }

    public RegistJson getJsonData() {
        return JsonData;
    }

    public void setJsonData(RegistJson jsonData) {
        JsonData = jsonData;
    }

    public class RegistJson{
        private String iDevID; //设备唯一ID号

        public String getiDevID() {
            return iDevID;
        }

        public void setiDevID(String iDevID) {
            this.iDevID = iDevID;
        }
    }
}
