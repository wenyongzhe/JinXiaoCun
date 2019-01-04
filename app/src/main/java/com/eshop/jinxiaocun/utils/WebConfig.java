package com.eshop.jinxiaocun.utils;

public class WebConfig {
    public static final String DEFAULT_STRING = "123";
    private static final int TIME_OUT = 5 * 1000 * 60;
//    private static String WSDL_URI = "http://120.79.209.140:8080/MobilePos/API.asmx/";
    private static String NameSpace = "http://120.79.209.140:8080";
    private static String PostData = "PostData";
    private static String GetData = "GetData";
    public static final String POS_LOGIN = "POS-LOGIN";
    public static final String POS_REG = "POS-REG";
    public static final String GetPLUInfo = "GetPLUInfo";//精确查询商品信息
    public static final String GetPLULikeInfo = "GetPLULikeInfo";//模糊查询商品信息
    public static final String QrySheetHead = "QrySheetHead";//单据主表查询
    public static final String QrySheetDetail= "QrySheetDetail";//单据明细查询;
    public static final String SaveSheetHead = "SaveSheetHead";//上传单据主表
    public static final String SaveSheetDetail = "SaveSheetDetail";//上传单据明细
    public static final String SheetSave = "SheetSave";//保存业务单据
    public static final String GetFlowNo = "GetFlowNo";
    public static final String GetPluPrice = "GetPluPrice";//销售商品取价
    public static final String R_PD_RANGE = "R-PD-RANGE";//取盘点范围
    public static final String R_PD_CLS = "R-PD-CLS";//取盘点范围
    public static final String R_PD_BRANCH = "R-PD-BRANCH";//取盘点门店机构
    public static final String RT_ASK_BATCH= "RT-ASK-BATCH";//盘点批号生成
    public static final String GetBatchNo= "GetBatchNo";//盘点批号获取
    public static final String R_PD_DETAIL= "R-PD-DETAIL";//盘点明细
    public static final String QryClassInfo= "QryClassInfo";//查询商品分类
    public static final String GetClassPluInfo= "GetClassPluInfo";//查询分类商品 getclassplunfo
    public static final String GetSheetNo= "GetSheetNo";//获取业务单据号
    public static final String W_PD_MASTER= "W-PD-MASTER";//上传盘点单记录头
    public static final String W_PD_DETAIL= "W-PD-DETAIL";//上传盘点明细
    public static final String W_SALEFLOW= "W-SALEFLOW";//上传销售流水
    public static final String W_PAYFLOW= "W-PayFlow";//上传付款
    public static final String R_PLU_BATCH= "R-PLU-BATCH";//获取商品批次信息
    public static final String QryPluStock= "QryPluStock";//商品库存查询
    public static final String GetCustomerInfo= "GetCustomerInfo";//客户信息
    public static final String BillDiscount= "BillDiscount";//整单折扣、议价
    public static final String GetPayMode= "GetPayMode";//付款方式
    public static final String GetSupplierInfo= "GetSupplierInfo";//获取供应商
    public static final String GetBranchInfo= "GetBranchInfo";//仓库、分部获取
    public static final String SellSub= "SellSub";//结算
    public static final String GetOptAuth= "GetOptAuth";//打折权限
    public static final String SheetCheck= "SheetCheck";//业务单据审核
    public static final String QrySheetPluPrice= "QrySheetPluPrice";//单据商品取价
    public static final String QryVouSheetHead= "QryVouSheetHead";//引用单据查询
    public static final String RT_WZF_PAY= "RT-WZF-PAY";//网络支付扣款（微信 支付宝）
    public static final String SellVipPay= "SellVipPay";//会员支付
    public static final String GetSystemInfo = "GetSystemInfo";//参数（抹零，卡密码，四舍五入等）


    public static String getPosLogin() {
        return POS_LOGIN;
    }

    public static String getGetPLUInfo() {
        return GetPLUInfo;
    }

    public static String getPosReg() {
        return POS_REG;
    }

    public static int getTimeOut() {
        return TIME_OUT;
    }

    public static String getPostWsdlUri() {

        return "http://"+Config.IP+":"+Config.IP_POIN+"/MobilePos/API.asmx/"+PostData;
    }

    public static String getGetWsdlUri() {

        return "http://"+Config.IP+":"+Config.IP_POIN+"/MobilePos/API.asmx/"+GetData;
    }

    public static String getNameSpace() {
        return NameSpace;
    }


}
