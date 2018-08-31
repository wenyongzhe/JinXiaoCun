package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

public class SellSubBeanResult extends BaseResult {

    private SellSubJsonData jsonData;


    public SellSubJsonData getJsonData() {
        return jsonData;
    }

    public void setJsonData(SellSubJsonData jsonData) {
        jsonData = jsonData;
    }

    public class SellSubJsonData {

        private String as_branchNo;//" :”0001” // 门店机构
        private String as_flowno;// " : “1001”,  //POSID号

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

    }

}
