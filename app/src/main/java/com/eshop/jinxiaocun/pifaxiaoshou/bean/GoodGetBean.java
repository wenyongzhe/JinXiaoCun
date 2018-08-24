package com.eshop.jinxiaocun.pifaxiaoshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class GoodGetBean extends BaseBean{

    private GoodGetJsonData JsonData;


    public GoodGetBean() {
        setStrCmd(WebConfig.GetPLUInfo);
        JsonData = new GoodGetJsonData();
    }

    public GoodGetJsonData getJsonData() {
        return JsonData;
    }

    public void setJsonData(GoodGetJsonData jsonData) {
        JsonData = jsonData;
    }

    public class GoodGetJsonData {
        private String as_branchNo;// 门店机构
        private String as_item_no;// 商品编码

        public String getAs_branchNo() {
            return as_branchNo;
        }

        public void setAs_branchNo(String as_branchNo) {
            this.as_branchNo = as_branchNo;
        }

        public String getAs_item_no() {
            return as_item_no;
        }

        public void setAs_item_no(String as_item_no) {
            this.as_item_no = as_item_no;
        }
    }

}
