package com.eshop.jinxiaocun.pifaxiaoshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

import java.util.List;

public class DanJuMainBeanResult extends BaseResult{

    public List<DanJuMainBeanResultJson> JsonData;
    

    public List<DanJuMainBeanResultJson> getJsonData() {
        return JsonData;
    }

    public void setJsonData(List<DanJuMainBeanResultJson> jsonData) {
        JsonData = jsonData;
    }
}
