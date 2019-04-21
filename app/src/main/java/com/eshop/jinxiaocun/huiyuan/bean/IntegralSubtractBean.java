package com.eshop.jinxiaocun.huiyuan.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/21
 * Desc:  积分冲减参数
 */

public class IntegralSubtractBean extends BaseBean {

    public IntegralSubtractParam JsonData;

    public IntegralSubtractBean() {
        setStrCmd(WebConfig.VipPointManage);
        JsonData = new IntegralSubtractParam();
    }

    public class IntegralSubtractParam {
        public int as_type;//业务类型 --1：储值消费，2:积分奖励，3：积分冲减 ,4:储值冲正
        public String as_vipNo;   //会员卡号
        public String oper_id;  //操作人员
        public String branch_no;     //机构编码
        public String as_flow_no; //小票号
        public String adec_consume_num; //本单积分值
        public String adec_consume_amt;  //本单消费金额
        public String adec_sav_amtnumeric;  //储值消费金额
        public String as_card_pass;//卡密码
        public String memo;    //备注

    }


}
