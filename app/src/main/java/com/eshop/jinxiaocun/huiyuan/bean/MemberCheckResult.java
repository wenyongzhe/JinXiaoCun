package com.eshop.jinxiaocun.huiyuan.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

import java.util.List;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/18
 * Desc:  会员查询返回的数据
 */

public class MemberCheckResult extends BaseResult {

    List<MemberCheckResultItem> JsonData;

    public List<MemberCheckResultItem> getJsonData() {
        return JsonData;
    }

    public void setJsonData(List<MemberCheckResultItem> jsonData) {
        JsonData = jsonData;
    }
}
