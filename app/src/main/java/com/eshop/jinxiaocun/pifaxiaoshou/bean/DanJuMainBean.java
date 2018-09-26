package com.eshop.jinxiaocun.pifaxiaoshou.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

public class DanJuMainBean extends BaseBean{

    public DanJuJsonData JsonData;


    public DanJuMainBean() {
        setStrCmd(WebConfig.QrySheetHead);
        JsonData = new DanJuJsonData();
    }


    public class DanJuJsonData {

        public String pos_id;
        public String branchNo;
        public String sheettype;//单据类型
        public String begintime;//开始时间
        public String endtime;//结束时间
        public String checkflag;//审核标志
        public int pagenum;//每页数量
        public int page;//页码
        public String operid;//操作员


    }

}
