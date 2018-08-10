package com.eshop.jinxiaocun.netWork.WebService;

public class WebConfig {
    private static final int TIME_OUT = 5 * 1000 * 60;
    private static String WSDL_URI = "";
    private static String NameSpace = "";
    private static final String POS_LOGIN = "POS-LOGIN";
    private static final String POS_REG = "POS-REG";
    private static final String GetPLUInfo = "GetPLUInfo";

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

    public static String getWsdlUri() {
        return WSDL_URI;
    }

    public static String getNameSpace() {
        return NameSpace;
    }
}
