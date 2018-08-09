package com.eshop.jinxiaocun.netWork.WebService;

public class WebConfig {
    private static final int TIME_OUT = 5 * 1000 * 60;
    private static String WSDL_URI = "";
    private static String NameSpace = "";
    private static final String LOGIN_METH = "POS-LOGIN";


    public static String getLoginMeth() {
        return LOGIN_METH;
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
