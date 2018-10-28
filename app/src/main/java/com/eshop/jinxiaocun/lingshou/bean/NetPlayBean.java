package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class NetPlayBean extends BaseBean{

    private NetPlayJsonData JsonData;

    public NetPlayBean() {
        setStrCmd(WebConfig.RT_WZF_PAY);
        JsonData = new NetPlayJsonData();
    }


    public NetPlayJsonData getJsonData() {
        return JsonData;
    }

    public void setJsonData(NetPlayJsonData jsonData) {
        JsonData = jsonData;
    }

    public class NetPlayJsonData {

        private String as_flowno;//”:”10010001” //结账流水
        private String as_branchNo;//" :”0001” // 门店机构
        private String as_posid;//”:”1001” //POSID
        private String order_title;//”:”支付标题”
        private String total_amount;//”:10 //支付金额(退款有用)
        private String pay_amount;//”:10 //付款金额(负数为退款)
        private String pay_type;//”:” ALIPAY” //支付类型 WECHAT:微信 ALIPAY:支付宝
        private String auth_code;//”:”1234567899” //授权码

        public String getAs_flowno() {
            return as_flowno;
        }

        public void setAs_flowno(String as_flowno) {
            this.as_flowno = as_flowno;
        }

        public String getAs_branchNo() {
            return as_branchNo;
        }

        public void setAs_branchNo(String as_branchNo) {
            this.as_branchNo = as_branchNo;
        }

        public String getAs_posid() {
            return as_posid;
        }

        public void setAs_posid(String as_posid) {
            this.as_posid = as_posid;
        }

        public String getOrder_title() {
            return order_title;
        }

        public void setOrder_title(String order_title) {
            this.order_title = order_title;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getPay_amount() {
            return pay_amount;
        }

        public void setPay_amount(String pay_amount) {
            this.pay_amount = pay_amount;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getAuth_code() {
            return auth_code;
        }

        public void setAuth_code(String auth_code) {
            this.auth_code = auth_code;
        }
    }
}
