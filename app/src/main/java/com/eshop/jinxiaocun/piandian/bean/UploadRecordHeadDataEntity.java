package com.eshop.jinxiaocun.piandian.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/10
 * Desc:
 */

public class UploadRecordHeadDataEntity extends BaseBean {

    public UploadRecordHeadDataEntity.UploadRecordHead JsonData;

    public UploadRecordHeadDataEntity(){
        setStrCmd(WebConfig.W_PD_MASTER);
        JsonData = new UploadRecordHeadDataEntity.UploadRecordHead();
    }

    public class UploadRecordHead{
        public String sheet_no; //盘点单号 通过getsheetno获取
        public String check_no;//盘点批次号
        public String trans_no;//"CY"  单据类型
        public String branch_no;//盘点门店
        public int oper_range;//10 //盘点类型
        public String oper_id;//操作员
        public String oper_date; //操作时间
        public String memo; //备注
    }

}
