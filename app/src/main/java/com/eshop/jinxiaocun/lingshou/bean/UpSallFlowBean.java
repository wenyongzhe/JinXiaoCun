package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.base.bean.SaleFlowBean;
import com.eshop.jinxiaocun.utils.WebConfig;

import java.util.ArrayList;
import java.util.List;

public class UpSallFlowBean extends BaseBean{

    private List<SaleFlowBean> JsonData;

    public UpSallFlowBean() {
        setStrCmd(WebConfig.W_SALEFLOW);
        JsonData = new ArrayList<>();
    }

    public List<SaleFlowBean> getJsonData() {
        return JsonData;
    }

    public void setJsonData(List<SaleFlowBean> jsonData) {
        JsonData = jsonData;
    }
}
