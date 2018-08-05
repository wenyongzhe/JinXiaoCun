package com.eshop.jinxiaocun.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.EditText;

import com.eshop.jinxiaocun.R;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 通用的类
 * Created by zwei on 2016/12/7.
 */

public class CommonUtility {

    private static CommonUtility utility;
    private static SoundPool soundPool;
    private static HashMap<String, Integer> listSoundID = new HashMap<String, Integer>();

    public static CommonUtility getInstance() {
        if (utility == null)
            utility = new CommonUtility();

        return utility;
    }

    /**
     * 说明: 初始化声音文件
     * 时间：2017/6/9 10:45
     * 作者: zwei
     */
    public void InitSound(Context context)
    {
        if (soundPool == null) {
            if (Build.VERSION.SDK_INT > 21) {
                SoundPool.Builder builder = new SoundPool.Builder();
                //传入音频数量
                builder.setMaxStreams(2);
                //AudioAttributes是一个封装音频各种属性的方法
                AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
                //设置音频流的合适的属性
                attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
                //加载一个AudioAttributes
                builder.setAudioAttributes(attrBuilder.build());
                soundPool = builder.build();
            } else {
                soundPool = new SoundPool(5, AudioManager.STREAM_SYSTEM, 5);
            }
        }

        listSoundID.put("overplan", soundPool.load(context, R.raw.overplan, 1));
        listSoundID.put("overstock", soundPool.load(context, R.raw.overstock, 1));
    }


   /**
    * 说明: 释放声音资源文件
    * 时间：2017/6/9 10:46
    * 作者: zwei
    */
    public void ReleaseSound()
    {
        if (soundPool != null)
            soundPool.release();
    }

    //数量超出计划数
    public void PlayOverPlan(){ soundPool.play(listSoundID.get("overplan"), 1, 1, 0, 0, 1);}

    //数量超出库存数
    public void PlayOverStock(){ soundPool.play(listSoundID.get("overstock"), 1, 1, 0, 0, 1);}

    /**
     * 获取网络连接状态
     */
    public boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public Point getDisplaySize(Context context) {
        Point size = new Point();
        Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if (Build.VERSION.SDK_INT < 13) {
            size.x = display.getWidth();
            size.y = display.getHeight();
        } else {
            display.getSize(size);
        }
        return size;
    }
    // / <summary>
    // / 获取程序版本
    // / </summary>
    // / <returns>程序版本</returns>
    public String GetAppVersion(Context context){
        PackageInfo packinfo;
        String version="";
        try {
            packinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String versionname = packinfo.versionName;
            version = versionname;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            version="1.0";
        }
        return version;
    }

    public boolean IsDecimal(String str)
    {
        Pattern p = Pattern.compile("([1-9]+[0-9]*|0)(\\\\.[\\\\d]+)?");
        Matcher m = p.matcher(str);
        if(m.matches() ){
            return true;
        }
        return false;
    }

    public InputFilter lengthFilter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {

            if (dest.length() == 0 && source.equals(".")) {
                return "0.";
            }
            String dValue = dest.toString();
            String[] splitArray = dValue.split("\\.");
            if (splitArray.length > 1) {
                String dotValue = splitArray[1];
                int DECIMAL_DIGITS = 6;
                if (dotValue.length() == DECIMAL_DIGITS) {
                    return "";
                }
            }
            return null;
        }

    };

    /**
     * 判断sd卡是否存在
     *
     * @return
     */
    public boolean sdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public boolean isAvaiableSpace(int sizeMb) {
        boolean ishasSpace = false;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            String sdcard = Environment.getExternalStorageDirectory().getPath();
            StatFs statFs = new StatFs(sdcard);
            long blockSize = statFs.getBlockSizeLong ();
            long blocks = statFs.getAvailableBlocksLong();
            long availableSpare = (blocks * blockSize) / (1024 * 1024);
            if (availableSpare > sizeMb) {
                ishasSpace = true;
            }
        }
        return ishasSpace;
    }

    public String getMD5(String val) throws NoSuchAlgorithmException {

        if (TextUtils.isEmpty(val)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(val.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public void closeKeyboard(Activity context, EditText editText){
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
     * 获取屏幕宽度
     * @param context 上下文对象
     * @return 屏幕宽度
     */
    public DisplayMetrics getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }
}
