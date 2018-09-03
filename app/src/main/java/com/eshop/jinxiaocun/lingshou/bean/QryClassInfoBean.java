package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class QryClassInfoBean extends BaseBean{

    private QryClassInfoJsonData JsonData;

    public QryClassInfoBean() {
        setStrCmd(WebConfig.QryClassInfo);
        JsonData = new QryClassInfoJsonData();
    }

    public QryClassInfoJsonData getJsonData() {
        return JsonData;
    }

    public void setJsonData(QryClassInfoJsonData jsonData) {
        JsonData = jsonData;
    }

    public class QryClassInfoJsonData {

        private String as_branchNo;//" :”0001” // 门店机构
        private String as_posId;// " : “1001”,  //POSID号
        private String as_type;//类别 1 品牌：0
        private String as_clsorbrno;//查询类别或编码

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

        public String getAs_type() {
            return as_type;
        }

        public void setAs_type(String as_type) {
            this.as_type = as_type;
        }

        public String getAs_clsorbrno() {
            return as_clsorbrno;
        }

        public void setAs_clsorbrno(String as_clsorbrno) {
            this.as_clsorbrno = as_clsorbrno;
        }
    }

}
