package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

import java.util.List;

public class GetPayModeResult extends BaseResult {

    private String Pay_way;// " :”1001” //支付方式
    private String Pay_name;//1001” //支付名称
    private String Rate;//:1 //汇率
    private String Pay_memo;// " :””//备注

    public String getPay_way() {
        return Pay_way;
    }

    public void setPay_way(String pay_way) {
        Pay_way = pay_way;
    }

    public String getPay_name() {
        return Pay_name;
    }

    public void setPay_name(String pay_name) {
        Pay_name = pay_name;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getPay_memo() {
        return Pay_memo;
    }

    public void setPay_memo(String pay_memo) {
        Pay_memo = pay_memo;
    }

}
