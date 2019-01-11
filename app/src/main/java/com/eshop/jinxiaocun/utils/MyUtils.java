package com.eshop.jinxiaocun.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.eshop.jinxiaocun.base.view.Application;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 描述  工具类
 */

public class MyUtils {
    //保存图片的文件夹名
    public final static String SAVE_DIR = "ImageCache";

    /**
     * 判断String是否为空
     *
     * @return
     */
    public static boolean isStringisNull(String str) {
        if (str == null || str.trim().equals("") || "null".equalsIgnoreCase(str)) {
            return true;
        }
        return false;
    }

    //网络是否打开
    public static boolean isOpenNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) Application.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connManager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }
        return true;
    }

    //判断String是否为Json字符串
    public static boolean isJsonString(String json) {
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        } catch (JsonParseException e) {
            return false;
        }
    }

    /**
     * 产生0～max的随机整数 包括0 不包括max
     *
     * @param max
     * 随机数的上限
     * @return
     */
    public static int getRandom(int max) {
        return new Random().nextInt(max);
    }

    /**
     * 图片的服务地址有一下几个，从上到下表示优先级：
     * 1.登录获取的系统参数中有个   System_Image_Url  这个字段
     * 2.如果手动设置了ip地址  为这个手动的地址
     * 3.选择了远程地址  为远程地址
     * 4.默认地址
     */

//    public static String getImageUrl(String url){
//
//        if(url.startsWith("http://")){
//            return url;
//        }else {
//            if(ConfigureParam.paramDataBean!=null&&!TextUtils.isEmpty(ConfigureParam.paramDataBean.System_Image_Url)){
//                return ConfigureParam.paramDataBean.System_Image_Url+url;
//            }
//            if(!TextUtils.isEmpty(url)){
//                return RequestConst.getUrl()+url;
//            }
//        }
//        return  RequestConst.getUrl();
//
//    }


    //将file转换了Bitmap
    public static Bitmap fileToBitmap(File file) throws IOException {
        InputStream input = new FileInputStream(file);
        byte[] byt = new byte[input.available()];
        input.read(byt);
        //去除前五个字节  copyOfRange复制一个数组 从五个字节copy
        byte[]  new_bts= Arrays.copyOfRange(byt, 5, byt.length);
        return BitmapFactory.decodeByteArray(new_bts,0,new_bts.length);
    }

    //将file转换了Bitmap
    public static Bitmap fileToBitmap2(File file) throws IOException {
        InputStream input = new FileInputStream(file);
        byte[] byt = new byte[input.available()];
        input.read(byt);
        //去除前五个字节  copyOfRange复制一个数组 从五个字节copy
        return BitmapFactory.decodeByteArray(byt,0,byt.length);
    }

    // 将InputStream转换成Bitmap
    public static Bitmap inputStream2Bitmap(InputStream is) {
        return BitmapFactory.decodeStream(is);
    }

    // 将InputStream转换成byte[]
    public static byte[] inputStreamToBytes(InputStream is) {
        String str = "";
        byte[] readByte = new byte[1024];
        int readCount = -1;
        try {
            while ((readCount = is.read(readByte, 0, 1024)) != -1) {
                str += new String(readByte).trim();
            }
            return str.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    public static String isExistDir(String saveDir) throws IOException {
        // 下载位置
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
            if (!downloadFile.mkdirs()) {
                downloadFile.createNewFile();
            }
            String savePath = downloadFile.getAbsolutePath();
            return savePath;
        }
        return null;
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    public static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }


    public static int stringToInt(String values,int defaultValues){

        if(isStringisNull(values)){
            return  defaultValues;
        }
        try {
            return  Integer.parseInt(values);
        }catch (Exception e){
            return  defaultValues;
        }

    }

    public static float stringToFloat(String values,float defaultValues){
        if(isStringisNull(values)){
            return  defaultValues;
        }
        try {
            return  Float.parseFloat(values);
        }catch (Exception e){
            return  defaultValues;
        }
    }

    //保存两位小数
    public static float saveTwofloor(float values){
        return Float.parseFloat( new DecimalFormat("##0.00").format(values));
    }

    public static String getString(int resId) {
        return Application.mContext.getString(resId);
    }

    /**
     * 在子线程也可以调用
     */
    public static void showToast(int message, Context paramContext) {
        Toast.makeText(paramContext, getString(message), Toast.LENGTH_LONG).show();
    }
    /**
     * 在子线程也可以调用
     */
    public static void showToast(String message, Context paramContext) {
        Toast.makeText(paramContext, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 在子线程也可以调用
     */
    public static void showToastShort(String message, Context paramContext) {
        Toast.makeText(paramContext, message, Toast.LENGTH_SHORT).show();
    }

    public static void closeKeyboard(Activity context, EditText editText){
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setSoftInputShownOnFocus;
            setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setSoftInputShownOnFocus.setAccessible(true);
            setSoftInputShownOnFocus.invoke(editText, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前本地apk的版本
     *
     * @param mContext
     * @return
     */
    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    public static String getTimeYYMMDD() {
        SimpleDateFormat sdf = new SimpleDateFormat("yymmdd");
        return sdf.format(new Date());
    }


    /**
     * 类型转换函数 安全
     */
    public final static String convertToString(Object object,String defaultvalut){

        if(object==null || "".equals(object.toString().trim())||
                "null".equals(object.toString().trim())){
            return defaultvalut;
        }

        try{
            return String.valueOf(object.toString().trim());
        }catch (Exception e){
            return defaultvalut;
        }
    }

    /**
     * 类型转换函数 安全
     */
    public final static float convertToFloat(Object object,float defaultvalut){

        if(object==null || "".equals(object.toString().trim())){
            return defaultvalut;
        }
        try{
            return Float.valueOf(object.toString().trim());
        }catch (Exception e){
            try{
                return Double.valueOf(object.toString().trim()).floatValue();
            }catch (Exception ex){
                return defaultvalut;
            }

        }
    }

    /**
     * 类型转换函数 安全
     */
    public final static double convertToDouble(Object object,double defaultvalut){

        if(object==null || "".equals(object.toString().trim())){
            return defaultvalut;
        }
        try{
            return Double.valueOf(object.toString().trim());
        }catch (Exception e){
            return defaultvalut;
        }
    }

    /**
     * 类型转换函数 安全
     */
    public final static int convertToInt(Object value,int defaultValue){
        if (value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }
//        无符号整形（unsigned int）变量的取值范围为：0～4294967295 ；
//        而整形（int）变量的取值范围为：2147483648～2147483647 .
        try{
            return Integer.parseInt(value.toString().trim());
        }catch (Exception e){
            try{
                return Double.valueOf(value.toString().trim()).intValue();
            }catch (Exception e1){
                return defaultValue;
            }
        }

    }
    /**
     * 类型转换函数 安全
     */
    public final static Long convertToLong(Object value,Long defaultValue){
        if (value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        //最大值是9223372036854775807。
        try{
            return Long.parseLong(value.toString().trim());
        }catch (Exception e){
            try{
                return Double.valueOf(value.toString().trim()).longValue();
            }catch (Exception e1){
                return defaultValue;
            }
        }
    }

    public final static String formatFlowNo(String flowno){
        flowno = String.format("%05d", Integer.decode(flowno)+1);
        flowno = Config.branch_no+Config.posid+DateUtility.getCurrentDateYYMMdd() + flowno;
        return flowno;
    }

    /**
     * 首先进行入参检查防止出现空指针异常
     * 如果两个参数都为空，则返回true
     * 如果有一项为空，则返回false
     * 接着对第一个list进行遍历，如果某一项第二个list里面没有，则返回false
     * 还要再将两个list反过来比较，因为可能一个list是两一个list的子集
     * 如果成功遍历结束，返回true
     * @param l0
     * @param l1
     * @return
     */
    public static boolean isListEqual(List l0, List l1){
        if (l0 == l1)
            return true;
        if (l0 == null && l1 == null)
            return true;
        if (l0 == null || l1 == null)
            return false;
        if (l0.size() != l1.size())
            return false;
        for (Object o : l0) {
            if (!l1.contains(o))
                return false;
        }
        for (Object o : l1) {
            if (!l0.contains(o))
                return false;
        }
        return true;
    }

    private static long lastClickTime = 0;
    //防止重复点击 事件间隔，在这里我定义的是1000毫秒
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD >= 100 && timeD <= 1250) {
            lastClickTime=0;
            return true;
        } else {
            lastClickTime = time;
            return false;

        }

    }

}
