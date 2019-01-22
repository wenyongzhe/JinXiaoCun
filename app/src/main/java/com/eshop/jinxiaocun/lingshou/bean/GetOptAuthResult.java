package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

public class GetOptAuthResult extends BaseResult {


    private int isgrant;//" :1// ‘3’密码错误，‘2’没有权限， ‘1’有权限
    private String operId;//1001” //收款员ID
    private double limitdiscount;//:80 //折扣最小额度
    private double savediscount;// : 60//折扣最大额度

    public int getIsgrant() {
        return isgrant;
    }

    public void setIsgrant(int isgrant) {
        this.isgrant = isgrant;
    }

    public double getSavediscount() {
        return savediscount;
    }

    public void setSavediscount(double savediscount) {
        this.savediscount = savediscount;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public double getLimitdiscount() {
        return limitdiscount;
    }

    public void setLimitdiscount(double limitdiscount) {
        this.limitdiscount = limitdiscount;
    }

}
