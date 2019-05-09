package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class CheckSaleManBean extends BaseBean {

    private CheckSaleManData JsonData;

    public CheckSaleManBean() {
        setStrCmd(WebConfig.checksaleman);
        JsonData = new CheckSaleManData();
    }

    public CheckSaleManData getJsonData() {
        return JsonData;
    }

    public void setJsonData(CheckSaleManData jsonData) {
        JsonData = jsonData;
    }

    public class CheckSaleManData {
        private String as_branchNo;//0001” // 门店机构
        private String as_saleId;//

        public String getAs_branchNo() {
            return as_branchNo;
        }

        public void setAs_branchNo(String as_branchNo) {
            this.as_branchNo = as_branchNo;
        }

        public String getAs_saleId() {
            return as_saleId;
        }

        public void setAs_saleId(String as_saleId) {
            this.as_saleId = as_saleId;
        }
    }
}
