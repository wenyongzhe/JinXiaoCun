package com.eshop.jinxiaocun.huiyuan.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/19
 * Desc: 会员充值参数
 */

public class MemberRechargeBean extends BaseBean {

    public MemberRechargeParam JsonData;

    public MemberRechargeBean() {
        setStrCmd(WebConfig.CardAddMoney);
        JsonData = new MemberRechargeParam();
    }

    public class MemberRechargeParam {
        public String CardNo;     //卡号
        public String BranchNo;  //机构号
        public String UserId;  //操作员
        public String FlowNo;   //流水号
        public float AddMoney;    //充值金额
        public float PayMoney;  //实际付款金额
        public String PayWay;       //支付方式，--人民币(RMB), 支付宝(ZFB), 微信(WXZ)
        public String Memo;  //备注

    }

}
