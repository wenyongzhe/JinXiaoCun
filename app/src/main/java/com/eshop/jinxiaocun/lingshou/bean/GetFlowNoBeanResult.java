package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

import java.util.List;

public class GetFlowNoBeanResult extends BaseResult{

    private FlowNoJson jsonData;

    public FlowNoJson getJsonData() {
        return jsonData;
    }

    public void setJsonData(FlowNoJson jsonData) {
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
