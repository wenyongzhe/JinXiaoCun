package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class GetPayModeBean extends BaseBean {

    private GetPayModeData JsonData;

    public GetPayModeBean() {
        setStrCmd(WebConfig.GetPayMode);
        JsonData = new GetPayModeData();
    }

    public GetPayModeData getJsonData() {
        return JsonData;
    }

    public void setJsonData(GetPayModeData jsonData) {
        JsonData = jsonData;
    }

    public class GetPayModeData {
        private String as_branchNo;//0001” // 门店机构

        public String getAs_branchNo() {
            return as_branchNo;
        }

        public void setAs_branchNo(String as_branchNo) {
            this.as_branchNo = as_branchNo;
        }
    }
}
