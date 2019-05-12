package com.eshop.jinxiaocun.base.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.Utils;
import com.eshop.jinxiaocun.db.DBHelper;
import com.eshop.jinxiaocun.lingshou.bean.GetSystemBeanResult;
import com.eshop.jinxiaocun.login.SystemSettingActivity;
import com.eshop.jinxiaocun.utils.AidlUtil;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.ConfigureParamSP;
import com.eshop.jinxiaocun.utils.CrashHandler;
import com.eshop.jinxiaocun.utils.MyUtils;

import java.util.List;
import java.util.Stack;

public class Application extends android.app.Application{

    private static Stack<Activity> activityStack;
    private static Application singleton;
    public static String IMEI;
    public static Context mContext;

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

//        NetManager.init(this);
        Utils.init(this);

        singleton = this;
        IMEI = PhoneUtils.getIMEI();
        getMachineIMEI();
        //收集错误信息并保存在本地
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());

        String serialNo = Build.SERIAL;
        if(serialNo.length()>7){
            Config.DeviceID = Build.SERIAL.substring(serialNo.length()-7);
        }else{
            Config.DeviceID = serialNo;
        }
        Config.VersionName = MyUtils.getVerName(this);
        Config.VersionCode = MyUtils.getVersionCode(this);
        Config.DBHelper = DBHelper.getInstance(this);
        Config.BluetoothAddress = ConfigureParamSP.getInstance().getValue(this,
                ConfigureParamSP.KEY_BLUETOOTHADDRESS,Config.BluetoothAddress);
        Config.IP = ConfigureParamSP.getInstance().getValue(this,ConfigureParamSP.KEY_SERVERURL,Config.IP);
        Config.IP_POIN = ConfigureParamSP.getInstance().getValue(this,ConfigureParamSP.KEY_SERVERPORT,Config.IP_POIN);

        //打印配置
        Config.mPrintSize =ConfigureParamSP.getInstance().getValue(this,
                ConfigureParamSP.KEY_PRINT_SIZE,Config.mPrintSize);
        Config.mPrintNumber =ConfigureParamSP.getInstance().getValue(this,
                ConfigureParamSP.KEY_PRINT_NUMBER,Config.mPrintNumber);
        Config.mPrintOrderName =ConfigureParamSP.getInstance().getValue(this,
                ConfigureParamSP.KEY_PRINT_ORDER_NAME,Config.mPrintOrderName);
        Config.mPrintPageHeader =ConfigureParamSP.getInstance().getValue(this,
                ConfigureParamSP.KEY_PRINT_PAGE_HEADER,Config.mPrintPageHeader);
        Config.mPrintPageFoot =ConfigureParamSP.getInstance().getValue(this,
                ConfigureParamSP.KEY_PRINT_PAGE_FOOT,Config.mPrintPageFoot);
        Config.isPrinterCardNo = ConfigureParamSP.getInstance().getValue(this,
                ConfigureParamSP.KEY_PRINTER_CARD_NO,Config.isPrinterCardNo);
        Config.isPrinterUserName = ConfigureParamSP.getInstance().getValue(this,
                ConfigureParamSP.KEY_PRINTER_USER_NAME,Config.isPrinterUserName);
        Config.isPrinterUserTel = ConfigureParamSP.getInstance().getValue(this,
                ConfigureParamSP.KEY_PRINTER_USER_TEL,Config.isPrinterUserTel);
        Config.isPrinterCashier = ConfigureParamSP.getInstance().getValue(this,
                ConfigureParamSP.KEY_PRINTER_CASHIER,Config.isPrinterCashier);


        FileUtils.createOrExistsDir(Config.updateFile);
        FileUtils.createOrExistsDir(Config.databasePath);
        FileUtils.createOrExistsDir(Config.crashPath);
        FileUtils.createOrExistsDir(Config.logPath);
        FileUtils.createOrExistsFile(Config.logFilePath);

        //sunmi机器打印机，通过aidl方式链接，初始化打印机
        AidlUtil.getInstance().connectPrinterService(this);
        //设备类型
        Config.DEVICE_TYPE = ConfigureParamSP.getInstance().getValue(this,ConfigureParamSP.KEY_DEVICE_TYPE,1);


    }

    public static String getIMEI() {
        return IMEI;
    }

    public void getMachineIMEI() {
        IMEI = PhoneUtils.getIMEI();
    }

    public static void setIMEI(String IMEI) {
        Application.IMEI = IMEI;
    }

    // Returns the application instance
    public static Application getInstance() {
        return singleton;
    }

    /**
     * add Activity 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.gc();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
