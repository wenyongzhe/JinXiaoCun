package com.eshop.jinxiaocun.utils;

public class WebConfig {
    private static final int TIME_OUT = 5 * 1000 * 60;
    private static String WSDL_URI = "http://120.79.209.140:8080/MobilePos/API.asmx/";
    private static String NameSpace = "http://120.79.209.140:8080";
    private static String PostData = "PostData";
    private static String GetData = "GetData";
    public static final String POS_LOGIN = "RT-LOGIN";
    public static final String POS_REG = "POS-REG";
    public static final String GetPLUInfo = "GetPLUInfo";//精确查询商品信息
    public static final String GetPLULikeInfo = "GetPLULikeInfo";//模糊查询商品信息
    public static final String QrySheetHead = "QrySheetHead";//单据主表查询
    public static final String QrySheetDetail= "QrySheetHead";//单据明细查询;
    public static final String GetFlowNo = "GetFlowNo";
    public static final String GetPluPrice = "GetPluPrice";//销售商品取价
    public static final String R_PD_RANGE = "R-PD-RANGE";//取盘点范围
    public static final String R_PD_CLS = "R-PD-CLS";//取盘点范围

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
        return WSDL_URI+PostData;
    }

    public static String getGetWsdlUri() {
        return WSDL_URI+GetData;
    }

    public static String getNameSpace() {
        return NameSpace;
    }


}
