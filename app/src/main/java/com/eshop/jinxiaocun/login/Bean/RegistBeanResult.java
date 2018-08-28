package com.eshop.jinxiaocun.login.Bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;
import com.eshop.jinxiaocun.utils.WebConfig;

public class RegistBeanResult extends BaseResult{

    private RegistJson jsonData;

    public RegistJson getJsonData() {
        return jsonData==null? new RegistJson():jsonData;
    }

    public void setJsonData(RegistJson jsonData) {
        this.jsonData = jsonData;
    }

    public class RegistJson{
        private String result; //成功Y N:失败
        private String message; //提示信息
        private String branch_no; //门店号
        private String posid; //pos id
        private String soft_name; //软件名称

        public String getResult() {
            return result==null? WebConfig.DEFAULT_STRING:result;
        }

        public String getMessage() {
            return message==null? WebConfig.DEFAULT_STRING:message;
        }

        public String getBranch_no() {

            return branch_no==null? WebConfig.DEFAULT_STRING:branch_no;
        }

        public String getPosid() {
            return posid==null?WebConfig.DEFAULT_STRING:posid;
        }

        public String getSoft_name() {

            return soft_name==null?WebConfig.DEFAULT_STRING:soft_name;
        }

    }

}
