package com.eshop.jinxiaocun.utils;

import android.os.Environment;

public class Config {

    public static String UserCode = "";
    public static String UserName = "";

    public static String DeviceID = "";
    public static String IP="192.168.10.67";
    public static String IP_POIN=":1433/";
    public static String DB_NAME="EWESHOP";
    public static String USER_NAME="sa";
    public static String PASSWORD="123456";
    public static String httpURL = "";

    private static String WebUrl = "";
    private static String NameSpace = "";

    //门店组
    public static String ShopGroup = "";

    //下载文件路径
    public static final String download_dir = "eshop";
    public static String DB_URL ="jdbc:jtds:sqlserver://" + Config.IP + Config.IP_POIN + Config.DB_NAME + ";charset=UTF-8;";

    public final static String updateFile = Config.getSdCardPath() + "/"+download_dir+"/Update/";
    public final static String databasePath = Config.getSdCardPath() + "/"+download_dir+"/Database/";
    public final static String crashPath = Config.getSdCardPath() + "/"+download_dir+"/Crash/";
    public final static String logPath = Config.getSdCardPath() + "/"+download_dir+"/log/";
    public final static String logFilePath = Config.getSdCardPath() + "/"+download_dir+"/log/log.txt";

    public static String getSdCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String getWebUrl() {
        return WebUrl;
    }

    public static String getNameSpace() {
        return NameSpace;
    }

}
