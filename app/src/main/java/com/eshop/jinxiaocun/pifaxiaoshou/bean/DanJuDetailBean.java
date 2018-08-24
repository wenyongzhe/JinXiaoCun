package com.eshop.jinxiaocun.pifaxiaoshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class DanJuDetailBean extends BaseBean{

    private DanJuJsonData JsonData;


    public DanJuDetailBean() {
        setStrCmd(WebConfig.QrySheetDetail);
        JsonData = new DanJuJsonData();
    }

    public DanJuJsonData getJsonData() {
        return JsonData;
    }

    public void setJsonData(DanJuJsonData jsonData) {
        JsonData = jsonData;
    }

    public class DanJuJsonData {
        private String UserId;    //用户ID
        private String SheetType;  //单据类型
        private String SheetNo;   //单据号
        private String BranchNo;  //源仓库机构
        private String TBranchNo; //目标仓库机构

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String userId) {
            UserId = userId;
        }

        public String getSheetType() {
            return SheetType;
        }

        public void setSheetType(String sheetType) {
            SheetType = sheetType;
        }

        public String getSheetNo() {
            return SheetNo;
        }

        public void setSheetNo(String sheetNo) {
            SheetNo = sheetNo;
        }

        public String getBranchNo() {
            return BranchNo;
        }

        public void setBranchNo(String branchNo) {
            BranchNo = branchNo;
        }

        public String getTBranchNo() {
            return TBranchNo;
        }

        public void setTBranchNo(String TBranchNo) {
            this.TBranchNo = TBranchNo;
        }
    }

}
