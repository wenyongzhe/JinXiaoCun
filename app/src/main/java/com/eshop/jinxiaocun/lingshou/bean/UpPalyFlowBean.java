package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.base.bean.SaleFlowBean;
import com.eshop.jinxiaocun.utils.WebConfig;

import java.util.ArrayList;
import java.util.List;

public class UpPalyFlowBean extends BaseBean{

    private List<PlayFlowBean> JsonData;

    public UpPalyFlowBean() {
        setStrCmd(WebConfig.W_PAYFLOW);
        JsonData = new ArrayList<>();
    }

    public List<PlayFlowBean> getJsonData() {
        return JsonData;
    }

    public void setJsonData(List<PlayFlowBean> jsonData) {
        JsonData = jsonData;
    }


}
