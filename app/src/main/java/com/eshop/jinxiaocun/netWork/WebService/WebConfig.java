package com.eshop.jinxiaocun.netWork.WebService;

public class WebConfig {
    private static final int TIME_OUT = 5 * 1000 * 60;
    private static String WSDL_URI = "";
    private static String NameSpace = "";
    private static final String LOGIN_CMD = "POS-LOGIN";


    public static String getLoginCmd() {
        return LOGIN_CMD;
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
