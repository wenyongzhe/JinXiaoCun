package com.eshop.jinxiaocun.huiyuan.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/21
 * Desc: 积分兑换参数
 */

public class IntegralExchangeBean extends BaseBean {

    public IntegralExchangeParam JsonData;

    public IntegralExchangeBean() {
        setStrCmd(WebConfig.AccExchange);
        JsonData = new IntegralExchangeParam();
    }

    public class IntegralExchangeParam {
        public String Branch_No;     //机构编号
        public String OperInfo;        //操作人员
        public String CardNo_TelNo;    //会员卡号,手机号,电话号码
        public float JiFen;    //积分
        public String HYGifts;    //礼品信息(最多5条)
        public String Memo;        //备注
    }

}
