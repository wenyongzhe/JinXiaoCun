package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class GetClassPluInfoBean extends BaseBean{

    private GetClassPluInfoData JsonData;

    public GetClassPluInfoBean() {
        setStrCmd(WebConfig.GetClassPluInfo);
        JsonData = new GetClassPluInfoData();
    }

    public GetClassPluInfoData getJsonData() {
        return JsonData;
    }

    public void setJsonData(GetClassPluInfoData jsonData) {
        JsonData = jsonData;
    }

    public class GetClassPluInfoData {

        private String as_branchNo;//" :”0001” // 门店机构
        private String as_posId;// " : “1001”,  //POSID号
        private String as_cls;//查询类别
        private String PerNum;//每页显示数量
        private String PageNum;//页码


        public String getAs_branchNo() {
            return as_branchNo;
        }

        public void setAs_branchNo(String as_branchNo) {
            this.as_branchNo = as_branchNo;
        }

        public String getAs_posId() {
            return as_posId;
        }

        public void setAs_posId(String as_posId) {
            this.as_posId = as_posId;
        }

        public String getAs_cls() {
            return as_cls;
        }

        public void setAs_cls(String as_cls) {
            this.as_cls = as_cls;
        }

        public String getPerNum() {
            return PerNum;
        }

        public void setPerNum(String perNum) {
            PerNum = perNum;
        }

        public String getPageNum() {
            return PageNum;
        }

        public void setPageNum(String pageNum) {
            PageNum = pageNum;
        }
    }

}
