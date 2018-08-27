package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class SellSubBean extends BaseBean{

    private SellSubJsonData JsonData;

    public SellSubBean() {
        setStrCmd(WebConfig.GetFlowNo);
        JsonData = new SellSubJsonData();
    }


    public SellSubJsonData getJsonData() {
        return JsonData;
    }

    public void setJsonData(SellSubJsonData jsonData) {
        JsonData = jsonData;
    }

    public class SellSubJsonData {

        private String as_branchNo;//" :”0001” // 门店机构
        private String as_flowno;//结账流水

        public String getAs_branchNo() {
            return as_branchNo;
        }

        public void setAs_branchNo(String as_branchNo) {
            this.as_branchNo = as_branchNo;
        }

        public String getAs_flowno() {
            return as_flowno;
        }

        public void setAs_flowno(String as_flowno) {
            this.as_flowno = as_flowno;
        }
    }
}
