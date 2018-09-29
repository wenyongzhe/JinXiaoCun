package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class GetOptAuthBean extends BaseBean{

    private GetOptAuthJsonData JsonData;

    public GetOptAuthBean() {
        setStrCmd(WebConfig.GetOptAuth);
        JsonData = new GetOptAuthJsonData();
    }


    public GetOptAuthJsonData getJsonData() {
        return JsonData;
    }

    public void setJsonData(GetOptAuthJsonData jsonData) {
        JsonData = jsonData;
    }

    public class GetOptAuthJsonData {

        private String as_branchNo;//:”0001” // 门店机构
        private String as_operId;//10010001” //操作员
        private String as_passwd; //密码
        private String ai_grant;//:1//权限序列

        public String getAs_branchNo() {
            return as_branchNo;
        }

        public void setAs_branchNo(String as_branchNo) {
            this.as_branchNo = as_branchNo;
        }

        public String getAs_operId() {
            return as_operId;
        }

        public void setAs_operId(String as_operId) {
            this.as_operId = as_operId;
        }

        public String getAs_passwd() {
            return as_passwd;
        }

        public void setAs_passwd(String as_passwd) {
            this.as_passwd = as_passwd;
        }

        public String getAi_grant() {
            return ai_grant;
        }

        public void setAi_grant(String ai_grant) {
            this.ai_grant = ai_grant;
        }
    }
}
