package com.eshop.jinxiaocun.utils;

import android.os.Environment;

import com.eshop.jinxiaocun.db.DBHelper;

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
    public static String VersionName = "";
    public static int VersionCode = 1;

    public static String branch_no = "-1";//门店号
    public static String posid = "-1";
    public static String soft_name = "-1";//软件名称

    public final static int MESSAGE_OK = 0;
    public final static int MESSAGE_ERROR = -1;
    public final static int MESSAGE_INTENT = 2;
    public final static int MESSAGE_REFLASH = 3;
    public final static int MESSAGE_GOODS_INFOR = 4;
    public final static int MESSAGE_SHEET_DETAIL = 5;
    public final static int MESSAGE_FLOW_NO = 6;
    public final static int MESSAGE_PANDIANLEIBIE_OK = 7;
    public final static int MESSAGE_PANDIANLEIBIE_ERROR = 8;
    public final static int MESSAGE_PANDIANSTOREJIGOU_OK = 9;
    public final static int MESSAGE_PANDIANSTOREJIGOU_ERROR = 10;
    public final static int MESSAGE_PANDIANPIHAOCREATE_OK = 11;
    public final static int MESSAGE_PANDIANPIHAOCREATE_ERROR = 12;

    //流水号
    public static String FlowNo = branch_no + DeviceID + MyUtils.getTimeYYMMDD();

    public static DBHelper DBHelper;

    public static String SHEET_NO="SheetNo";

    //sql
    public static String UP_MAIN_DANJU = "upMainDanJu";
    public static String UP_DETAIL_DANJU = "upDetailDanJu";
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

}
