package com.eshop.jinxiaocun.huiyuan.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

import java.util.List;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/18
 * Desc: 消费查询返回数据
 */

public class ExpenseCheckResult extends BaseResult {

    private List<ExpenseCheckResultItem> JsonData;

    public List<ExpenseCheckResultItem> getJsonData() {
        return JsonData;
    }

    public void setJsonData(List<ExpenseCheckResultItem> jsonData) {
        JsonData = jsonData;
    }
}
