package com.eshop.jinxiaocun.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import com.eshop.jinxiaocun.base.Application;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Author 卢安
 * 创建时间  2018/3/26 0026
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



}
