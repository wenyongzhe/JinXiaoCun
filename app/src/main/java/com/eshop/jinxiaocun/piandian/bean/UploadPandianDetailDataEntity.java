package com.eshop.jinxiaocun.piandian.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/10
 * Desc:
 */

public class UploadPandianDetailDataEntity extends BaseBean {

    public List<UploadPandianDetailDataEntity.UploadPandianDetail> JsonData;

    public UploadPandianDetailDataEntity(){
        setStrCmd(WebConfig.W_PD_DETAIL);
    }

    public static class UploadPandianDetail{
        public String sheet_no; //盘点单号
        public String item_no;//编码
        public float in_price;//进价
        public float sale_price;//销价
        public int check_qty;//盘点数量
        public String item_barcode;//批次号
        public String produce_date;//生产日期
        public String valid_date;//有效日期
        public int as_dealflag;//1：表示该单结束
    }

}
