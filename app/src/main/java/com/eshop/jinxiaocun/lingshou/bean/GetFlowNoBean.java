package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class GetFlowNoBean extends BaseBean{

    private GetFlowNoJsonData JsonData;

    public GetFlowNoBean() {
        setStrCmd(WebConfig.GetFlowNo);
        JsonData = new GetFlowNoJsonData();
    }

    public GetFlowNoJsonData getJsonData() {
        return JsonData;
    }

    public void setJsonData(GetFlowNoJsonData jsonData) {
        JsonData = jsonData;
    }

    public class GetFlowNoJsonData {

        private String branchNo;//" :”0001” // 门店机构
        private String posId;// " : “1001”,  //POSID号

        public String getBranchNo() {
            return branchNo;
        }

        public void setBranchNo(String branchNo) {
            this.branchNo = branchNo;
        }

        public String getPosId() {
            return posId;
        }

        public void setPosId(String posId) {
            this.posId = posId;
        }
    }

}
