package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

public class VipPayBeanResult extends BaseResult{

    private VipPayJson jsonData;

    public VipPayJson getJsonData() {
        return jsonData;
    }

    public void setJsonData(VipPayJson jsonData) {
        jsonData = jsonData;
    }

    public class VipPayJson{
        private String FlowNo;

        public String getFlowNo() {
            return FlowNo;
        }

        public void setFlowNo(String flowNo) {
            FlowNo = flowNo;
        }
    }
}
