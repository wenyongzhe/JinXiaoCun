package com.eshop.jinxiaocun.base.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.Utils;
import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.db.DBHelper;
import com.eshop.jinxiaocun.lingshou.bean.GetOptAuthResult;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.CrashHandler;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.util.Stack;

import okhttp3.Response;

public class Application extends android.app.Application implements INetWorResult {

    private static Stack<Activity> activityStack;
    private static Application singleton;
    public static String IMEI;
    public static Context mContext;
    private ILingshouScan mLingShouScanImp;

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
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        String serialNo = Build.SERIAL;
        if(serialNo.length()>7){
            Config.DeviceID = Build.SERIAL.substring(serialNo.length()-7);
        }else{
            Config.DeviceID = serialNo;
        }
        Config.VersionName = MyUtils.getVerName(this);
        Config.VersionCode = MyUtils.getVersionCode(this);
        Config.DBHelper = DBHelper.getInstance(this);

        FileUtils.createOrExistsDir(Config.updateFile);
        FileUtils.createOrExistsDir(Config.databasePath);
        FileUtils.createOrExistsDir(Config.crashPath);
        FileUtils.createOrExistsDir(Config.logPath);
        FileUtils.createOrExistsFile(Config.logFilePath);

        getLimit();
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

    @Override
    public void handleResule(int flag, Object o) {
        Intent intent;
        switch (flag) {
            case Config.MESSAGE_OK:
                break;
            case Config.MESSAGE_ERROR:

                break;
            case Config.MESSAGE_GET_OPT_AUTH:

                break;
        }
    }

    IJsonFormat mJsonFormatImp = new JsonFormatImp();
    private void getLimit(){
        mLingShouScanImp = new LingShouScanImp(this);
        //-1：取整笔议价最高折让金额 和 单笔议价最高折让金额
        mLingShouScanImp.getOptAuth(Config.GRANT_ITEM_JINE,new IResponseListener(){
            @Override
            public void handleError(Object event) {
            }
            @Override
            public void handleResult(Response event, String result) {
            }
            @Override
            public void handleResultJson(String status, String msg, String jsonData) {
                try{
                    if(status.equals(Config.MESSAGE_OK+"")){
                        GetOptAuthResult mGetOptAuthResult =  mJsonFormatImp.JsonToBean(jsonData,GetOptAuthResult.class);
                        Config.zhendanYiJialimit = mGetOptAuthResult.getLimitdiscount();
                        Config.danbiYiJialimit = mGetOptAuthResult.getSavediscount();
                    }else{
                        AlertUtil.showAlert(mContext, "提示", "请求失败");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    AlertUtil.showAlert(mContext, "提示", "请求失败");
                }
            }
        });
        //单笔折扣
        mLingShouScanImp.getOptAuth(Config.GRANT_ITEM_DISCOUNT,new IResponseListener(){
            @Override
            public void handleError(Object event) {
            }
            @Override
            public void handleResult(Response event, String result) {
            }
            @Override
            public void handleResultJson(String status, String msg, String jsonData) {
                try{
                    if(status.equals(Config.MESSAGE_OK+"")){
                        GetOptAuthResult mGetOptAuthResult =  mJsonFormatImp.JsonToBean(jsonData,GetOptAuthResult.class);
                        Config.zhendanZheKoulimit = mGetOptAuthResult.getLimitdiscount();
                        Config.danbiZheKoulimit = mGetOptAuthResult.getSavediscount();
                    }else{
                        AlertUtil.showAlert(mContext, "提示", "请求失败");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    AlertUtil.showAlert(mContext, "提示", "请求失败");
                }
            }
        });
    }
}
