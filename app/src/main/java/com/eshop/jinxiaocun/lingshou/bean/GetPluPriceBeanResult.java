package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.base.bean.BaseResult;
import com.eshop.jinxiaocun.utils.WebConfig;

public class GetPluPriceBeanResult extends BaseResult {

    private GetPluPriceJsonData JsonData;


    public GetPluPriceJsonData getJsonData() {
        return JsonData;
    }

    public void setJsonData(GetPluPriceJsonData jsonData) {
        JsonData = jsonData;
    }

    public class GetPluPriceJsonData {

        private String as_branchNo;//" :”0001” // 门店机构
        private String as_flowno;// " : “1001”,  //POSID号
        private String as_cardno;//会员卡号

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

        public String getAs_cardno() {
            return as_cardno;
        }

        public void setAs_cardno(String as_cardno) {
            this.as_cardno = as_cardno;
        }
    }

}
