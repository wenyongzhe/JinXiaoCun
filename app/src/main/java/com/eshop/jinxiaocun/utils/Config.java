package com.eshop.jinxiaocun.utils;

import android.os.Environment;

import com.eshop.jinxiaocun.db.DBHelper;
import com.eshop.jinxiaocun.huiyuan.bean.MemberCheckResultItem;

import java.util.ArrayList;
import java.util.HashMap;

public class Config {

    public static String UserCode = "";
    public static String UserName = "1001";
    public static String PassWord ="1001";
    public static String UserId = "1001";

    public static String DeviceID = "";
//    public static String IP="192.168.10.67";
    public static String IP="120.79.209.140";
    public static String IP_POIN="8113";
    public static String DB_NAME="EWESHOP";
    public static String DB_USER_NAME ="sa";
    public static String DB_PASSWORD ="123456";
    public static String VersionName = "";
    public static int VersionCode = 1;
    public static  String saleMan = "";
    public static String branch_no = "";//门店号
    public static String posid = "";
    public static String soft_name = "";//软件名称
    public static String jigou_no = "";//机构号 (登录返回 便要设置值)
    public static String PerNum = "50";//每页条数
    public static String intValue = ""; //收银权限 1:允许
    public static String strgrant = "";//:”100001” //操作权限 1字节表示一种类型

    // request参数
    public static final int REQ_QR_CODE = 11002; // // 打开扫描界面请求码
    public static final int REQ_PERM_CAMERA = 11003; // 打开摄像头
    public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";

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
    public final static int MESSAGE_PANDIANPIHAOHUOQU_OK = 13;
    public final static int MESSAGE_PANDIANPIHAOHUOQU_ERROR = 14;
    public final static int MESSAGE_QRYCLASSINFO = 15;
    public final static int MESSAGE_GETCLASSPLUINFO = 16;
    public final static int MESSAGE_SHEETNO_OK = 17;
    public final static int MESSAGE_SHEETNO_ERROR = 18;
    public final static int MESSAGE_FAIL = 19;
    public final static int MESSAGE_SUCCESS = 20;
    public final static int RESULT_SUCCESS= 21;
    public final static int RESULT_FAIL= 22;
    public final static int MESSAGE_UP_SALL_FLOW = 21;
    public final static int MESSAGE_PICI = 22;
    public final static int MESSAGE_GETPLU_PRICE = 23;
    public final static int MESSAGE_UP_PLAY_FLOW = 24;
    public final static int MESSAGE_SELL_SUB = 25;
    public final static int MESSAGE_MONEY = 26;
    public final static int MESSAGE_GOODS_INFOR_FAIL = 27;
    public final static int MESSAGE_RESULT_SUCCESS= 28;
    public final static int MESSAGE_INTENT_ZHEKOU = 29;
    public final static int MESSAGE_INTENT_YIJIA = 30;
    public final static int RESULT_SELECT_GOODS = 200;
    public final static int RESULT_SELECT_GOODS_QUERY = 201;
    public final static int MESSAGE_GET_PAY_MODE = 31;
    public final static int MESSAGE_GET_OPT_AUTH = 32;
    public final static int MESSAGE_BILL_DISCOUNT = 33;
    public final static int MESSAGE_BILL_DISCOUNT_RETURN = 34;
    public final static int MESSAGE_GET_PRICE_SUCCESS = 35;
    public final static int MESSAGE_GET_PRICE_FAIL = 36;
    public final static int MESSAGE_SELECT_PAY_RETURN = 37;
    public final static int MESSAGE_CAPTURE_RETURN = 38;
    public final static int MESSAGE_NET_PAY_RETURN = 39;
    public final static int MESSAGE_start_query = 40;
    public final static int MESSAGE_VIP_PAY_RESULT = 41;
    public final static int MESSAGE_SELECT_RETURN = 42;
    public final static int MESSAGE_GET_SYSTEM_INFO_RETURN = 43;
    public final static int RESULT_PAY_CANCLE = 44;
    public final static int MESSAGE_PAY_MAN = 45;
    public final static int SHOW_PROGRESS = 46;
    public final static int DISS_PROGRESS = 47;
    public final static int JIE_ZHUANG = 48;
    public final static int SAVE_MEMBER_ID = 49;
    public final static int YING_YE_YUAN = 50;


    public final static String GRANT_BILLDIS_COUNT = "4";//整单折扣
    public final static String GRANT_ITEM_CAHNGE_PRICE = "10";//单品议价
    public final static String GRANT_ITEM_DISCOUNT = "3";//单笔折扣
    public final static String GRANT_ITEM_JINE= "-1";//取整笔议价最高折让金额 和 单笔议价最高折让金额

    public static double zhendanYiJialimit = 0.000;//整笔议价最高金额
    public static double danbiYiJialimit = 0.000;// 单笔议价最高金额
    public static int mYiJiaPermission = 2;// 议价权限  ‘3’密码错误，‘2’没有权限， ‘1’有权限
    public static double zhendanZheKoulimit = 0.000;//整笔折扣最高金额
    public static double danbiZheKoulimit = 0.000;// 单笔折扣最高金额

    //流水号
    public static String FlowNo = branch_no + DeviceID + MyUtils.getTimeYYMMDD();

    public static DBHelper DBHelper;

    public static String SHEET_NO="SheetNo";

    //sql
    public static String UP_MAIN_DANJU = "upMainDanJu";
    public static String UP_DETAIL_DANJU = "upDetailDanJu";
    public static final String PANDIAN_DETAIL_GOODS = "PANDIANDETAILGOODS";
    public static final String GETCLASSPLURESULT ="GetClassPluResult";
    public static final String DANJUMAINBEANRESULTITEM ="DanJuMainBeanResultItem";
    public static final String BILLGLIDENO ="billGlideNO";
    public static final String LINSHOU ="LinShou";
    public static final String LINSHOU_MAIN ="LinShou_main";
    //门店组
    public static String ShopGroup = "";
    //记录连接的蓝牙地址
    public static String BluetoothAddress = "";

    public static String mPrintSize = "58";//打印的尺寸
    public static String mPrintNumber = "1";//打印的份数
    public static String mPrintOrderName = "";//打印的单据名称
    public static String mPrintPageHeader = "";//打印的页眉
    public static String mPrintPageFoot = "欢迎光临，谢谢惠顾！";//打印的页脚
    public static boolean isPrinterCardNo;//是否打印卡号
    public static boolean isPrinterUserName;//是否打印客户姓名
    public static boolean isPrinterUserTel;//是否打印客户联系方式
    public static boolean isPrinterCashier;//是否打印收银员



    //下载文件路径
    public static final String download_dir = "eshop";
    public static String DB_URL ="jdbc:jtds:sqlserver://" + Config.IP + Config.IP_POIN + Config.DB_NAME + ";charset=UTF-8;";

    public final static String updateFile = Config.getSdCardPath() + "/"+download_dir+"/Update/";
    public final static String databasePath = Config.getSdCardPath() + "/"+download_dir+"/Database/";
    public final static String crashPath = Config.getSdCardPath() + "/"+download_dir+"/Crash/";
    public final static String logPath = Config.getSdCardPath() + "/"+download_dir+"/log/";
    public final static String logFilePath = Config.getSdCardPath() + "/"+download_dir+"/log/log.txt";

    //结算后记得清空
    public static MemberCheckResultItem mMemberInfo;

    public static String getSdCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static HashMap<String, String> PayType = new HashMap<>();


    public enum YwType{
        //业务类型
        YH,//要货
        PO,//采购订单
        PI,//采购入仓
        MO,//配送出库
        MI,//配送入库
        SS,//批发订单
        SO,//批发出库
        RI,//批发退货
        PD,//盘点批次号
        CR,//盘点单号
    }

}
