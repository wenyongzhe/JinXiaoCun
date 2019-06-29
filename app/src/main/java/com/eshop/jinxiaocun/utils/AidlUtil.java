package com.eshop.jinxiaocun.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.bean.TableItem;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.landicorp.android.eptapi.device.Printer;
import com.landicorp.android.eptapi.exception.RequestException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import woyou.aidlservice.jiuiv5.ICallback;
import woyou.aidlservice.jiuiv5.IWoyouService;


public class AidlUtil {
    private static final String SERVICE＿PACKAGE = "woyou.aidlservice.jiuiv5";
    private static final String SERVICE＿ACTION = "woyou.aidlservice.jiuiv5.IWoyouService";

    private IWoyouService woyouService;
    private static AidlUtil mAidlUtil = new AidlUtil();
    private Context context;

    private AidlUtil() {
    }

    public static AidlUtil getInstance() {
        return mAidlUtil;
    }

    /**
     * 连接服务
     *
     * @param context context
     */
    public void connectPrinterService(Context context) {
        this.context = context.getApplicationContext();
        Intent intent = new Intent();
        intent.setPackage(SERVICE＿PACKAGE);
        intent.setAction(SERVICE＿ACTION);
        context.getApplicationContext().startService(intent);
        context.getApplicationContext().bindService(intent, connService, Context.BIND_AUTO_CREATE);
    }

    /**
     * 断开服务
     *
     * @param context context
     */
    public void disconnectPrinterService(Context context) {
        if (woyouService != null) {
            context.getApplicationContext().unbindService(connService);
            woyouService = null;
        }
    }

    public boolean isConnect() {
        return true;
//        return woyouService != null;
    }

    private ServiceConnection connService = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            woyouService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            woyouService = IWoyouService.Stub.asInterface(service);
            AidlUtil.getInstance().initPrinter();
        }
    };

    public ICallback generateCB(final PrinterCallback printerCallback){
        return new ICallback.Stub(){


            @Override
            public void onRunResult(boolean isSuccess) throws RemoteException {

            }

            @Override
            public void onReturnString(String result) throws RemoteException {
                printerCallback.onReturnString(result);
            }

            @Override
            public void onRaiseException(int code, String msg) throws RemoteException {

            }

            @Override
            public void onPrintResult(int code, String msg) throws RemoteException {

            }
        };
    }

    /**
     * 设置打印浓度
     */
    private int[] darkness = new int[]{0x0600, 0x0500, 0x0400, 0x0300, 0x0200, 0x0100, 0,
            0xffff, 0xfeff, 0xfdff, 0xfcff, 0xfbff, 0xfaff};

    public void setDarkness(int index) {
        if (woyouService == null) {
            Toast.makeText(context, R.string.toast_2, Toast.LENGTH_LONG).show();
            return;
        }

        int k = darkness[index];
        try {
            woyouService.sendRAWData(ESCUtil.setPrinterDarkness(k), null);
            woyouService.printerSelfChecking(null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取得打印机系统信息，放在list中
     *
     * @return list
     */
    public List<String> getPrinterInfo(PrinterCallback printerCallback1, PrinterCallback printerCallback2) {
        if (woyouService == null) {
            Toast.makeText(context, R.string.toast_2, Toast.LENGTH_LONG).show();
            return null;
        }

        List<String> info = new ArrayList<>();
        try {
            woyouService.getPrintedLength(generateCB(printerCallback1));
            woyouService.getPrinterFactory(generateCB(printerCallback2));
            info.add(woyouService.getPrinterSerialNo());
            info.add(woyouService.getPrinterModal());
            info.add(woyouService.getPrinterVersion());
            info.add(printerCallback1.getResult());
            info.add(printerCallback2.getResult());
            //info.add(woyouService.getServiceVersion());
            PackageManager packageManager = context.getPackageManager();
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(SERVICE＿PACKAGE, 0);
                if(packageInfo != null){
                    info.add(packageInfo.versionName);
                    info.add(packageInfo.versionCode+"");
                }else{
                    info.add("");info.add("");
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 初始化打印机
     */
    public void initPrinter() {
        if (woyouService == null) {
            Toast.makeText(context, R.string.toast_2, Toast.LENGTH_LONG).show();
            return;
        }

        try {
            woyouService.printerInit(null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印二维码
     */
    public void printQr(String data, int modulesize, int errorlevel) {
        if (woyouService == null) {
            Toast.makeText(context, R.string.toast_2, Toast.LENGTH_LONG).show();
            return;
        }


        try {
			woyouService.setAlignment(1, null);
            woyouService.printQRCode(data, modulesize, errorlevel, null);
            woyouService.lineWrap(3, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印条形码
     */
    public void printBarCode(String data, int symbology, int height, int width, int textposition) {
        if (woyouService == null) {
            Toast.makeText(context, R.string.toast_2, Toast.LENGTH_LONG).show();
            return;
        }


        try {
            woyouService.printBarCode(data, symbology, height, width, textposition, null);
            woyouService.lineWrap(3, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印文字
     */
    String strPrintTemp = "";
    public void printText(String content, float size, boolean isBold, boolean isUnderLine) {
        strPrintTemp += content;

//        if (woyouService == null) {
//            Toast.makeText(context, R.string.toast_2, Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        try {
//            if (isBold) {
//                woyouService.sendRAWData(ESCUtil.boldOn(), null);
//            } else {
//                woyouService.sendRAWData(ESCUtil.boldOff(), null);
//            }
//
//            if (isUnderLine) {
//                woyouService.sendRAWData(ESCUtil.underlineWithOneDotWidthOn(), null);
//            } else {
//                woyouService.sendRAWData(ESCUtil.underlineOff(), null);
//            }
//
//            woyouService.printTextWithFont(content, null, size, null);
//            //woyouService.lineWrap(3, null);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }

    }

    /**
     * POS 签购单打印
     */
    public void print(){
        final String mes = strPrintTemp;
        strPrintTemp = "";
        /** 1、创建 Printer.Progress 实例 */
        Printer.Progress progress = new Printer.Progress() {
            /** 2、在 Printer.Progress 的 doPrint 方法中设置签购单的打印样式 */
            @Override
            public void doPrint(Printer printer) throws Exception {
                /** 设置打印格式 */
                Printer.Format format = new Printer.Format();
                /** 中文字符打印,此处使用 16x16 点,1 倍宽&&1 倍高
                 */
                format.setHzScale(Printer.Format.HZ_SC1x1);
                format.setHzSize(Printer.Format.HZ_DOT24x24);
                printer.setFormat(format);
                printer.printText(mes);
                printer.feedLine(5);
            }
            @Override
            public void onFinish(int code) {
                /** Printer.ERROR_NONE 即打印成功 */
                if (code == Printer.ERROR_NONE) {
                    AlertUtil.showToast("打印成功!");
                }
                else {
                    AlertUtil.showToast("[打印失败]"+getErrorDescription(code));
                }
            }
            /** 根据错误码获取相应错误提示
             * @param code 错误码
             * @return 错误提示
             */
            public String getErrorDescription(int code) {
                switch(code) {
                    case Printer.ERROR_PAPERENDED: return "Paper-out, the operation is invalid this time";
                    case Printer.ERROR_HARDERR: return "Hardware fault, can not find HP signal";
                    case Printer.ERROR_OVERHEAT: return "Overheat";
                    case Printer.ERROR_BUFOVERFLOW: return "The operation buffer mode position is out of range";
                    case Printer.ERROR_LOWVOL: return "Low voltage protect";
                    case Printer.ERROR_PAPERENDING: return "Paper-out, permit the latter operation";
                    case Printer.ERROR_MOTORERR: return "The printer core fault (too fast or too slow)";
                    case Printer.ERROR_PENOFOUND: return "Automatic positioning did not find the alignment position, the paper back to its original position";
                    case Printer.ERROR_PAPERJAM: return "paper got jammed";
                    case Printer.ERROR_NOBM: return "Black mark not found";
                    case Printer.ERROR_BUSY: return "The printer is busy";
                    case Printer.ERROR_BMBLACK: return "Black label detection to black signal";
                    case Printer.ERROR_WORKON: return "The printer power is open";
                    case Printer.ERROR_LIFTHEAD: return "Printer head lift";
                    case Printer.ERROR_LOWTEMP: return "Low temperature protect";
                }
                return "unknown error ("+code+")";
            }
            @Override
            public void onCrash() {
            }
        };
        /** 3、启动打印 */
        try {
            progress.start();
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    /*
    *打印图片
     */
    public void printBitmap(Bitmap bitmap) {
        if (woyouService == null) {
            Toast.makeText(context, R.string.toast_2, Toast.LENGTH_LONG).show();
            return;
        }

        try {
            woyouService.setAlignment(1, null);
            woyouService.printBitmap(bitmap, null);
            woyouService.lineWrap(3, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     *  打印图片和文字按照指定排列顺序
     */
    public void printBitmap(Bitmap bitmap, int orientation) {
        if (woyouService == null) {
            Toast.makeText(context,"服务已断开！", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            if(orientation == 0){
                woyouService.printBitmap(bitmap, null);
                woyouService.printText("横向排列\n", null);
                woyouService.printBitmap(bitmap, null);
                woyouService.printText("横向排列\n", null);
            }else{
                woyouService.printBitmap(bitmap, null);
                woyouService.printText("\n纵向排列\n", null);
                woyouService.printBitmap(bitmap, null);
                woyouService.printText("\n纵向排列\n", null);
            }
            woyouService.lineWrap(3, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印表格
     */
    public void printTable(LinkedList<TableItem> list) {
        if (woyouService == null) {
            Toast.makeText(context, R.string.toast_2, Toast.LENGTH_LONG).show();
            return;
        }

        try {
            for (TableItem tableItem : list) {
                Log.i("kaltin", "printTable: "+tableItem.getText()[0]+tableItem.getText()[1]+tableItem.getText()[2]);
                woyouService.printColumnsString(tableItem.getText(), tableItem.getWidth(), tableItem.getAlign(), null);
            }
            woyouService.lineWrap(3, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /*
    * 空打三行！
     */
    public void print3Line(){
        if (woyouService == null) {
            Toast.makeText(context, R.string.toast_2, Toast.LENGTH_LONG).show();
            return;
        }

        try {
            woyouService.lineWrap(3, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /*
    * 空行几行打
     */
    public void printEmptyLine(int emptyLineNumber){
//        for(int i=0; i<emptyLineNumber; i++){
//            strPrintTemp += "\n";
//        }

//        if (woyouService == null) {
//            Toast.makeText(context, R.string.toast_2, Toast.LENGTH_LONG).show();
//            return;
//        }
//        try {
//            woyouService.lineWrap(emptyLineNumber, null);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
    }


    public void sendRawData(byte[] data) {
        if (woyouService == null) {
            Toast.makeText(context, R.string.toast_2, Toast.LENGTH_LONG).show();
            return;
        }

        try {
            woyouService.sendRAWData(data, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendRawDatabyBuffer(byte[] data, ICallback iCallback){
        if (woyouService == null) {
            Toast.makeText(context, R.string.toast_2, Toast.LENGTH_LONG).show();
            return;
        }

        try {
            woyouService.enterPrinterBuffer(true);
            woyouService.sendRAWData(data, iCallback);
            woyouService.exitPrinterBufferWithCallback(true, iCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
