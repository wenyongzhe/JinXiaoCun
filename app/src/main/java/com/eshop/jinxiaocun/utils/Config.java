package com.eshop.jinxiaocun.utils;

import android.os.Environment;

public class Config {

    public static String DeviceID = "";
    public final static String IP="192.168.10.67";
    public final static String IP_POIN=":1433/";
    public final static String DB_NAME="EWESHOP";
    public final static String USER_NAME="sa";
    public final static String PASSWORD="123456";
    //下载文件路径
    public static final String download_dir = "eshop";

    public final static String updateFile = Config.getSdCardPath() + "/"+download_dir+"/Update/";
    public final static String databasePath = Config.getSdCardPath() + "/"+download_dir+"/Database/";
    public final static String crashPath = Config.getSdCardPath() + "/"+download_dir+"/Crash/";
    public final static String logPath = Config.getSdCardPath() + "/"+download_dir+"/log/";
    public final static String logFilePath = Config.getSdCardPath() + "/"+download_dir+"/log/log.txt";

    public static String getSdCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

}
