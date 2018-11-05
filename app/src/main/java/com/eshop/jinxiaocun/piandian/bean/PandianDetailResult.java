package com.eshop.jinxiaocun.piandian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/11/5
 * Desc:
 */

public class PandianDetailResult implements Serializable{


    private int totalCount;
    private int nowCount;
    private List<PandianDetailBeanResult> detailData;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getNowCount() {
        return nowCount;
    }

    public void setNowCount(int nowCount) {
        this.nowCount = nowCount;
    }

    public List<PandianDetailBeanResult> getDetailData() {
        return detailData;
    }

    public void setDetailData(List<PandianDetailBeanResult> detailData) {
        this.detailData = detailData;
    }
}
