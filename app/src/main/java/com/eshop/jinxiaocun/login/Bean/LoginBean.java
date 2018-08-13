package com.eshop.jinxiaocun.login.Bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class LoginBean extends BaseBean{

    private LoginJsonData JsonData;

    public LoginBean() {
        setStrCmd(WebConfig.getPosLogin());
        JsonData = new LoginJsonData();
    }

    public LoginJsonData getJsonData() {
        return JsonData;
    }

    public void setJsonData(LoginJsonData jsonData) {
        JsonData = jsonData;
    }

    public class LoginJsonData {

        private String as_branch_no; //门店号
        private String as_operid;   //操作员
        private String as_operpsw; //密码

        public void setAs_branch_no(String as_branch_no) {
            this.as_branch_no = as_branch_no;
        }


        public void setAs_operid(String as_operid) {
            this.as_operid = as_operid;
        }


        public void setAs_operpsw(String as_operpsw) {
            this.as_operpsw = as_operpsw;
        }
    }

}
