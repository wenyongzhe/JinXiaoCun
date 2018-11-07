package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

import java.util.List;

public class GetPayModeResult extends BaseResult {

    private String pay_way;// " :”1001” //支付方式
    private String pay_name;//1001” //支付名称
    private String rate;//:1 //汇率
    private String pay_memo;// " :””//备注

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPay_memo() {
        return pay_memo;
    }

    public void setPay_memo(String pay_memo) {
        this.pay_memo = pay_memo;
    }
}
