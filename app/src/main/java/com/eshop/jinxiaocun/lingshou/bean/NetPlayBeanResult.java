package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

public class NetPlayBeanResult extends BaseResult {

    private String return_code;//”:” 000000” // 通迅结果 000000成功
    private String return_msg;//”:”” //错误提示信息
    private String payType;//”:” ALIPAY” //支付类型 WECHAT:微信 ALIPAY:支付宝
    private String trade_no;//”:”201800012001” //平台交易ID

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }
}
