package com.eshop.jinxiaocun.piandian.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

import java.util.List;

/**
 * @Author Lu An
 * 创建时间  2018/8/28 0028
 * 描述
 */

public class PandianStoreJigouBeanResult extends BaseResult {
    private List<PandianStoreJigouBeanResultItem> jsonData;

    public List<PandianStoreJigouBeanResultItem> getJsonData() {
        return jsonData;
    }

    public void setJsonData(List<PandianStoreJigouBeanResultItem> jsonData) {
        this.jsonData = jsonData;
    }
}
