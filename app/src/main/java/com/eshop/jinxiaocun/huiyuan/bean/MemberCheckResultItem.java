package com.eshop.jinxiaocun.huiyuan.bean;

import java.io.Serializable;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/18
 * Desc:
 */

public class MemberCheckResultItem implements Serializable{

    private String CardNo_TelNo="";    //会员卡号,手机号,电话号码
    private String CardName="";   //--卡名称
    private String CardType="";  //--卡类型
    private String CardState="";  //--卡状态
    private String BirthDay="";  //--生日
    private float Vip_accnum; //--剩余积分
    private float Tot_accnum;//--累计积分
    private float Residual_amt; //--余额
    private String Saving_flag="";//--是否储值 (是1  否0)
    private String Acc_Flag="";//--是否积分 (是0  否1)
    private String Password="";    //密码
    private String ValidDate="";//有效期
    private String vip_tel;//电话号码
    private String mobile;//手机号码
    private String vip_sex;// 性别
    private String memo;//备注

    public String getCardNo_TelNo() {
        return CardNo_TelNo;
    }

    public void setCardNo_TelNo(String cardNo_TelNo) {
        CardNo_TelNo = cardNo_TelNo;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String cardName) {
        CardName = cardName;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getCardState() {
        return CardState;
    }

    public void setCardState(String cardState) {
        CardState = cardState;
    }

    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String birthDay) {
        BirthDay = birthDay;
    }

    public float getVip_accnum() {
        return Vip_accnum;
    }

    public void setVip_accnum(float vip_accnum) {
        Vip_accnum = vip_accnum;
    }

    public float getTot_accnum() {
        return Tot_accnum;
    }

    public void setTot_accnum(float tot_accnum) {
        Tot_accnum = tot_accnum;
    }

    public float getResidual_amt() {
        return Residual_amt;
    }

    public void setResidual_amt(float residual_amt) {
        Residual_amt = residual_amt;
    }

    public String getSaving_flag() {
        return Saving_flag;
    }

    public void setSaving_flag(String saving_flag) {
        Saving_flag = saving_flag;
    }

    public String getAcc_Flag() {
        return Acc_Flag;
    }

    public void setAcc_Flag(String acc_Flag) {
        Acc_Flag = acc_Flag;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getValidDate() {
        return ValidDate;
    }

    public void setValidDate(String validDate) {
        ValidDate = validDate;
    }

    public String getVip_tel() {
        return vip_tel;
    }

    public void setVip_tel(String vip_tel) {
        this.vip_tel = vip_tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVip_sex() {
        return vip_sex;
    }

    public void setVip_sex(String vip_sex) {
        this.vip_sex = vip_sex;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
