package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class BillDiscountBean extends BaseBean {

    private BillDiscountInfoData JsonData;

    public BillDiscountBean() {
        setStrCmd(WebConfig.BillDiscount);
        JsonData = new BillDiscountInfoData();
    }

    public BillDiscountInfoData getJsonData() {
        return JsonData;
    }

    public void setJsonData(BillDiscountInfoData jsonData) {
        JsonData = jsonData;
    }

    public class BillDiscountInfoData {
        private String as_branchNo;//0001” // 门店机构
        private String as_flowno;//”10010001” //POS ID
        private String as_type ;//“A”// A 金额折，D 折扣
        private String as_discount;//100 //折扣或金额
        private String cashier_id;//”1001”//收款员ID
        private String cashier_pw;//””//收款员密码

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

        public String getAs_type() {
            return as_type;
        }

        public void setAs_type(String as_type) {
            this.as_type = as_type;
        }

        public String getAs_discount() {
            return as_discount;
        }

        public void setAs_discount(String as_discount) {
            this.as_discount = as_discount;
        }

        public String getCashier_id() {
            return cashier_id;
        }

        public void setCashier_id(String cashier_id) {
            this.cashier_id = cashier_id;
        }

        public String getCashier_pw() {
            return cashier_pw;
        }

        public void setCashier_pw(String cashier_pw) {
            this.cashier_pw = cashier_pw;
        }
    }
}
