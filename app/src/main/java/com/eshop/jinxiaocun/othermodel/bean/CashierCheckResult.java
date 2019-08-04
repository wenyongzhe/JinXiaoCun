package com.eshop.jinxiaocun.othermodel.bean;

import java.io.Serializable;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/8/4
 * Desc: 收银对账 返回的数据
 */
public class CashierCheckResult implements Serializable {
    private String RepDetail; //报表显示打印内容

    public String getRepDetail() {
        return RepDetail;
    }

    public void setRepDetail(String repDetail) {
        RepDetail = repDetail;
    }
}
