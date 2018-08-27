package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

import java.util.List;

public class GetFlowNoBeanResult extends BaseResult{

    private List<FlowNoJson> jsonData;

    public List<FlowNoJson> getJsonData() {
        return jsonData;
    }

    public void setJsonData(List<FlowNoJson> jsonData) {
        jsonData = jsonData;
    }

    public class FlowNoJson{
        private String FlowNo;

        public String getFlowNo() {
            return FlowNo;
        }

        public void setFlowNo(String flowNo) {
            FlowNo = flowNo;
        }
    }
}
