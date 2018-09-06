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
        private String oper_id;//“1001”//操作员代码
        private String PerNum;//每页显示数量
        private String PageNum;//页码

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

        public String getAs_item_no() {
            return as_item_no;
        }

        public void setAs_item_no(String as_item_no) {
            this.as_item_no = as_item_no;
        }

        public String getOper_id() {
            return oper_id;
        }

        public void setOper_id(String oper_id) {
            this.oper_id = oper_id;
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
