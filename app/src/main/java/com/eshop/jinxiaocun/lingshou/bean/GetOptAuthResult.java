package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

public class GetOptAuthResult extends BaseResult {


    private String Isgrant;//" :1// ‘3’密码错误，‘2’没有权限， ‘1’有权限
    private String operId;//1001” //收款员ID
    private String limitdiscount;//:80 //折扣最小额度
    private String Savediscount;// : 60//折扣最大额度

    public String getIsgrant() {
        return Isgrant;
    }

    public void setIsgrant(String isgrant) {
        Isgrant = isgrant;
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

    public String getSavediscount() {
        return Savediscount;
    }

    public void setSavediscount(String savediscount) {
        Savediscount = savediscount;
    }
}
