package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class VipPayBean extends BaseBean{

    private VipPayJsonData JsonData;

    public VipPayBean() {
        setStrCmd(WebConfig.SellVipPay);
        JsonData = new VipPayJsonData();
    }


    public VipPayJsonData getJsonData() {
        return JsonData;
    }

    public void setJsonData(VipPayJsonData jsonData) {
        JsonData = jsonData;
    }

    public class VipPayJsonData {

        private String as_type;//1业务类型 --1：储值消费，2:积分奖励，3：积分冲减 ,4:储值冲正
        private String as_vipNo;//”1234” //会员卡号
        private String oper_id;//”1001” //操作人员
        private String as_branchNo;//" :”0001” // 门店机构
        private String as_flowno;//”10010001” //结账流水
        private String adec_consume_num;//10 //本单积分值
        private String adec_consume_amt;//10 //本单消费金额
        private String adec_sav_amt;//10 //储值消费金额
        private String as_card_pass;//”123” //卡密码
        private String memo;//””  //备注

        public String getAs_type() {
            return as_type;
        }

        public void setAs_type(String as_type) {
            this.as_type = as_type;
        }

        public String getAs_vipNo() {
            return as_vipNo;
        }

        public void setAs_vipNo(String as_vipNo) {
            this.as_vipNo = as_vipNo;
        }

        public String getOper_id() {
            return oper_id;
        }

        public void setOper_id(String oper_id) {
            this.oper_id = oper_id;
        }

        public String getAs_branchNo() {
            return as_branchNo;
        }

        public void setAs_branchNo(String as_branchNo) {
            this.as_branchNo = as_branchNo;
        }

        public String getAs_flowno() {
            return as_flowno;
        }

        public void setAs_flowno(String as_flowno) {
            this.as_flowno = as_flowno;
        }

        public String getAdec_consume_num() {
            return adec_consume_num;
        }

        public void setAdec_consume_num(String adec_consume_num) {
            this.adec_consume_num = adec_consume_num;
        }

        public String getAdec_consume_amt() {
            return adec_consume_amt;
        }

        public void setAdec_consume_amt(String adec_consume_amt) {
            this.adec_consume_amt = adec_consume_amt;
        }

        public String getAdec_sav_amt() {
            return adec_sav_amt;
        }

        public void setAdec_sav_amt(String adec_sav_amt) {
            this.adec_sav_amt = adec_sav_amt;
        }

        public String getAs_card_pass() {
            return as_card_pass;
        }

        public void setAs_card_pass(String as_card_pass) {
            this.as_card_pass = as_card_pass;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
    }
}
