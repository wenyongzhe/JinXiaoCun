package com.eshop.jinxiaocun.lingshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class GetSystemInfoBean extends BaseBean{

    private SystemInfoData JsonData;

    public GetSystemInfoBean() {
        setStrCmd(WebConfig.GetSystemInfo);
        JsonData = new SystemInfoData();
    }


    public SystemInfoData getJsonData() {
        return JsonData;
    }

    public void setJsonData(SystemInfoData jsonData) {
        JsonData = jsonData;
    }

    public class SystemInfoData {

        private String BranchNo;//”:””   //机构号
        private String POSId;//”:””
        private String UserId;//”:””    //操作员

        public String getBranchNo() {
            return BranchNo;
        }

        public void setBranchNo(String branchNo) {
            BranchNo = branchNo;
        }

        public String getPOSId() {
            return POSId;
        }

        public void setPOSId(String POSId) {
            this.POSId = POSId;
        }

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String userId) {
            UserId = userId;
        }
    }
}
