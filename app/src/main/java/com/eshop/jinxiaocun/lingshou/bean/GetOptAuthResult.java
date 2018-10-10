package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

public class GetOptAuthResult extends BaseResult {


    private String isgrant;//" :1// ‘3’密码错误，‘2’没有权限， ‘1’有权限
    private String operId;//1001” //收款员ID
    private String limitdiscount;//:80 //折扣最小额度
    private String savediscount;// : 60//折扣最大额度

    public String getIsgrant() {
        return isgrant;
    }

    public void setIsgrant(String isgrant) {
        this.isgrant = isgrant;
    }

    public String getSavediscount() {
        return savediscount;
    }

    public void setSavediscount(String savediscount) {
        this.savediscount = savediscount;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getLimitdiscount() {
        return limitdiscount;
    }

    public void setLimitdiscount(String limitdiscount) {
        this.limitdiscount = limitdiscount;
    }

}
