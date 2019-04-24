package com.eshop.jinxiaocun.huiyuan.bean;

import java.io.Serializable;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/21
 * Desc:
 */

public class IntegralExchangeGoodsResultItem implements Serializable {

    private String BarCode;  //编号
    private String SelfCode;  //自编码
    private String Name;      //名称
    private float Num;      //数量
    private float JiFen;   //积分
    private String BeginTime; //兑换起始时间
    private String EndTime;  //兑换结束时间
    private String BranchNo; //机构号
    private boolean isSelect;
    private float selectNum;

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String BarCode) {
        this.BarCode = BarCode;
    }

    public String getSelfCode() {
        return SelfCode;
    }

    public void setSelfCode(String selfCode) {
        SelfCode = selfCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public float getNum() {
        return Num;
    }

    public void setNum(float num) {
        Num = num;
    }

    public float getJiFen() {
        return JiFen;
    }

    public void setJiFen(float jiFen) {
        JiFen = jiFen;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getBranchNo() {
        return BranchNo;
    }

    public void setBranchNo(String branchNo) {
        BranchNo = branchNo;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public float getSelectNum() {
        return selectNum;
    }

    public void setSelectNum(float selectNum) {
        this.selectNum = selectNum;
    }
}
