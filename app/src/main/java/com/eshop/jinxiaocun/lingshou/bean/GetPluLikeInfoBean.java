package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class GetPluLikeInfoBean extends BaseBean{

    private GetPluLikeInfoJsonData JsonData;

    public GetPluLikeInfoBean() {
        setStrCmd(WebConfig.GetPLULikeInfo);
        JsonData = new GetPluLikeInfoJsonData();
    }

    public GetPluLikeInfoJsonData getJsonData() {
        return JsonData;
    }

    public void setJsonData(GetPluLikeInfoJsonData jsonData) {
        JsonData = jsonData;
    }

    public class GetPluLikeInfoJsonData {

        private String as_branchNo;//" :”0001” // 门店机构
        private String as_searchstr;// “123456”//查询关键词、编码、助词码、附加码等模糊查询

        public String getAs_branchNo() {
            return as_branchNo;
        }

        public void setAs_branchNo(String as_branchNo) {
            this.as_branchNo = as_branchNo;
        }

        public String getAs_searchstr() {
            return as_searchstr;
        }

        public void setAs_searchstr(String as_searchstr) {
            this.as_searchstr = as_searchstr;
        }
    }

}
