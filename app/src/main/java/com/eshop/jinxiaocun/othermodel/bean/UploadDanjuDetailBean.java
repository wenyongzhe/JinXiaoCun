package com.eshop.jinxiaocun.othermodel.bean;

import com.eshop.jinxiaocun.base.bean.BaseBean;
import com.eshop.jinxiaocun.utils.WebConfig;

import java.util.List;

/**
 * @Author Lu An
 * 创建时间  2018/9/19 0019
 * 描述
 */

public class UploadDanjuDetailBean extends BaseBean {

    public List<UploadDanjuDetailBean.UploadDanjuDetail> JsonData;

    public UploadDanjuDetailBean() {
        setStrCmd(WebConfig.SaveSheetDetail);
    }

    public static class UploadDanjuDetail {
        public String FLowID = "";
        public String POSID = "";
        public String BillNo = ""; //单据号
        public String SupplyCode = "";
        public String BarCode = "";//编码
        public String PluBatch = "";     //批次
        public String SelfCode = ""; //自编码15
        public String Name = "";//名称
        public String Unit = ""; //单位
        public String StateCheck = "";  //标记当前商品是否已盘点
        public int CheckNum = 0; //数量
        public int StockNum = 0;
        public int ReferenceNum = 0;  //参考数量
        public float BuyPrice = 0f;         //进价
        public float SalePrice = 0f;        //销价
        public float sub_amt = 0f;        //金额
        public String GoodsSeat = "";       //货架号
        public String MadeDate = "";//生产日期
        public String VaildDate = "";   //有效日期
        public int Enable_batch = 0;
        public int EndFlag = 0;
    }

}
