package com.eshop.jinxiaocun.pifaxiaoshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

import java.util.List;

public class DanJuMainBeanResult extends BaseResult{

    public List<DanJuMainBeanResultItem> JsonData;
    

    public List<DanJuMainBeanResultItem> getJsonData() {
        return JsonData;
    }

    public void setJsonData(List<DanJuMainBeanResultItem> jsonData) {
        JsonData = jsonData;
    }
}
