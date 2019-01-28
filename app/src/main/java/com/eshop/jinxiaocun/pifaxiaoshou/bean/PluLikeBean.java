package com.eshop.jinxiaocun.pifaxiaoshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class PluLikeBean extends BaseBean{

    private GoodGetJsonData JsonData;


    public PluLikeBean() {
        setStrCmd(WebConfig.GetPLULikeInfo);
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
        private String as_searchstr;//查询关键词、编码、助词码、附加码等模糊查询

        public String getAs_searchstr() {
            return as_searchstr;
        }

        public void setAs_searchstr(String as_searchstr) {
            this.as_searchstr = as_searchstr;
        }

        public String getAs_branchNo() {
            return as_branchNo;
        }

        public void setAs_branchNo(String as_branchNo) {
            this.as_branchNo = as_branchNo;
        }
    }

}
