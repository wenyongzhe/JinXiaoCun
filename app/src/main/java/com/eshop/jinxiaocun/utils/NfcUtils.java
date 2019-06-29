package com.eshop.jinxiaocun.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.eshop.jinxiaocun.BuildConfig;
import com.landicorp.android.eptapi.card.MifareDriver;
import com.landicorp.android.eptapi.exception.RequestException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class NfcUtils {
    //nfc
    public static NfcAdapter mNfcAdapter;
    public static IntentFilter[] mIntentFilter = null;
    public static PendingIntent mPendingIntent = null;
    public static String[][] mTechList = null;

    /**
     * 构造函数，用于初始化nfc
     */
    public NfcUtils(Activity activity) {
        mNfcAdapter = NfcCheck(activity);
        NfcInit(activity);
    }

    /**
     * 检查NFC是否打开
     */
    public static NfcAdapter NfcCheck(Activity activity) {
        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        if (mNfcAdapter == null) {
            return null;
        } else {
            if (!mNfcAdapter.isEnabled()) {
                Intent setNfc = new Intent(Settings.ACTION_NFC_SETTINGS);
                activity.startActivity(setNfc);
            }
        }
        return mNfcAdapter;
    }

    /**
     * 初始化nfc设置
     */
    public static void NfcInit(Activity activity) {
        mPendingIntent = PendingIntent.getActivity(activity, 0, new Intent(activity, activity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter filter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter filter2 = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        try {
            filter.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            e.printStackTrace();
        }
        mIntentFilter = new IntentFilter[]{filter, filter2};
        mTechList = null;
    }

    /**
     * 读取NFC的数据
     */
    public static String readNFCFromTag(Intent intent) throws UnsupportedEncodingException {
//        return resolveIntent(intent);
        Parcelable[] rawArray = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawArray != null) {
            NdefMessage mNdefMsg = (NdefMessage) rawArray[0];
            NdefRecord mNdefRecord = mNdefMsg.getRecords()[0];
            if (mNdefRecord != null) {
                String readResult = new String(mNdefRecord.getPayload(), "UTF-8");
                return readResult;
            }
        }
        return "";
    }

    // 读卡
    static MifareClassic mfc;
    public static String resolveIntent(Intent intent) {
            // 3) Get an instance of the TAG from the NfcAdapter
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        // 4) Get an instance of the Mifare classic card from this TAG
        // intent
        mfc = MifareClassic.get(tagFromIntent);
        if(mfc!=null){
            //Toast.makeText(Application.mContext, "检测到卡片,读卡中。。。", Toast.LENGTH_SHORT).show();
            try{
                mfc.connect();
                boolean auth = false;

                int bCount = mfc.getBlockCountInSector(2);
                int bIndex = mfc.sectorToBlock(2);
                auth = mfc.authenticateSectorWithKeyA(2,MifareClassic.KEY_DEFAULT);//验证密码
                if (auth){
                    byte[] data = mfc.readBlock(bIndex);
                    String ms = ByteArrayToListString(data);
                    return ms;
                }
            } catch (Exception e){
                if(BuildConfig.DEBUG){
                    e.printStackTrace();
                }
                return "";
            }
        }
        return "";
    }

    //认证扇区所需数据
    final static int blockNo = 8;
    final static int sectorNo = 2;
    final static String keyA = "FFFFFFFFFFFF";
    final static byte[] key = hexStringToByte(keyA);
    //获取认证结果监听器
    static MifareDriver.OnResultListener authListener = new MifareDriver.OnResultListener() {
        @Override
        public void onSuccess() {
            try {
                mRFDriver.authBlock(blockNo, MifareDriver.KEY_A, key, new MifareDriver.OnResultListener() {
                    @Override
                    public void onSuccess() {
                        try {
                            mRFDriver.readBlock(blockNo, mReadListener);
                        } catch (RequestException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFail(int i) {

                    }

                    @Override
                    public void onCrash() {

                    }
                });

            } catch (RequestException e) {
                e.printStackTrace();
            }



        }
        @Override
        public void onFail(int arg0) {
        }
        @Override
        public void onCrash() {
        }
    };

    static Handler mHandler;
    static MifareDriver mRFDriver;
    public static String readMifareCard(MifareDriver rFDriver, Handler mHandler) {
        //认证扇区
        try {
            NfcUtils.mHandler = mHandler;
            mRFDriver = rFDriver;
            mRFDriver.authSector(sectorNo, MifareDriver.KEY_A, key, authListener);
        } catch (RequestException e) {
            e.printStackTrace();
        }



        return "";
    }

    public static MifareDriver.OnReadListener mReadListener = new MifareDriver.OnReadListener() {
        @Override
        public void onSuccess(byte[] bytes) {
            String cardId = ByteArrayToListString(bytes);
            Message msg = new Message();
            msg.obj = cardId.substring(0,cardId.indexOf("-"));
            NfcUtils.mHandler.sendMessage(msg);
        }

        @Override
        public void onFail(int i) {
        }

        @Override
        public void onCrash() {
        }
    };

    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString().trim();
    }


    /**
     * 往nfc写入数据
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void writeNFCToTag(String data, Intent intent) throws IOException, FormatException {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        NdefRecord ndefRecord = NdefRecord.createTextRecord(null, data);
        NdefRecord[] records = {ndefRecord};
        NdefMessage ndefMessage = new NdefMessage(records);
        ndef.writeNdefMessage(ndefMessage);
    }

    /**
     * 读取nfcID
     */
    public static String readNFCId(Intent intent) throws UnsupportedEncodingException {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String id = ByteArrayToHexString(tag.getId());
        return id;
    }

    /**
     * 将字节数组转换为字符串
     */
    private static String ByteArrayToHexString(byte[] inarray) {
        int i, j, in;
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String out = "";

        for (j = 0; j < inarray.length; ++j) {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }

    /**
     * 将字节数组转换字符数组
     */
    public static String ByteArrayToListString(byte[] inarray) {
        int i, j, in;
        String out = "";

        for (j = 0; j < inarray.length; ++j) {
            in = (int) inarray[j];
            if(in != 0){
                i = (in >> 4) & 0x0f;
                out += ((in-48)+"");
            }
//            else{
//                return out;
//            }
        }
        return out;
    }
}
