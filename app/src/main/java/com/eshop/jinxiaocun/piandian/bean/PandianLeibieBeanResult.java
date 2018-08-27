package com.eshop.jinxiaocun.piandian.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

import java.util.List;

/**
 * @Author Lu An
 * 创建时间  2018/8/27 0027
 * 描述
 */

public class PandianLeibieBeanResult extends BaseResult {

    private List<PandianLeibieBeanResultItem> jsonData;

    public List<PandianLeibieBeanResultItem> getJsonData() {
        return jsonData;
    }

    public void setJsonData(List<PandianLeibieBeanResultItem> jsonData) {
        this.jsonData = jsonData;
    }
}
