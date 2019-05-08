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
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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
//    String resolveIntent(Intent intent) {
//        String action = intent.getAction();
//        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
//            Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
//            MifareClassic mfc = MifareClassic.get(tagFromIntent);
//            MifareClassCard mifareClassCard = null;
//            String authKey = "a2xQ61";//这是卡片的1扇区的密码（实际根据自己的卡片所设置的扇区和密码）
//            try {
//                mfc.connect();
//                boolean auth = false;
//                int secCount = mfc.getSectorCount();
//                mifareClassCard = new MifareClassCard(secCount);
//                int bCount = 0;
//                int bIndex = 0;
//                //连接NFC卡后到开始循环读取扇区
//                for (int j = 0; j < secCount; j++) {
//                    MifareSector mifareSector = new MifareSector();
//                    mifareSector.sectorIndex = j;
//                    if (j == 1 && !TextUtils.isEmpty(authKey)) {
//                        auth = mfc.authenticateSectorWithKeyA(j, hexStringToByte(str2HexStr(authKey)));//需用str2HexStr方法 将服务器传的卡密码string转16进制
//                    } else {
//                        auth = mfc.authenticateSectorWithKeyA(j, MifareClassic.KEY_DEFAULT);//默认密码
//                    }
//                    if (j >= 2) { //这里因为我只需要1扇区的，所以后面直接break了的遍历直接break了
//                        break;
//                    }
//                    mifareSector.authorized = auth;
//                    if (auth) {
//                        bCount = mfc.getBlockCountInSector(j);
//                        bCount = Math.min(bCount, MifareSector.BLOCKCOUNT);
//                        bIndex = mfc.sectorToBlock(j);
//                        for (int i = 0; i < bCount; i++) {
//                            byte[] data = mfc.readBlock(bIndex);
//                            MifareBlock mifareBlock = new MifareBlock(data);
//                            mifareBlock.blockIndex = bIndex;
//                            bIndex++;
//                            mifareSector.blocks[i] = mifareBlock;
//
//                        }
//                        mifareClassCard.setSector(mifareSector.sectorIndex, mifareSector);
//                    } else {
//                    }
//                }
//                ArrayList<String> blockData = new ArrayList<String>();
//                int blockIndex = 0;
//                //遍历每个扇区及对应的区块
//                for (int i = 0; i < secCount; i++) {
//                    MifareSector mifareSector = mifareClassCard.getSector(i);
//                    for (int j = 0; j < MifareSector.BLOCKCOUNT; j++) {
//                        MifareBlock mifareBlock = mifareSector.blocks[j];
//                        byte[] data = mifareBlock.getData();
//                        if (i == 1 && j == 0) {//我使用的卡内数据 写在1扇区 0块 ,拿到所需值返回即可
////                            Log.e("扇区：" + blockIndex, "" + new String(data).trim());
//                            return new String(data).trim();
//                        }
//                        blockData.add("Block " + blockIndex++ + " : "
//                                + Converter.getHexString(data, data.length));
//                    }
//                }
//            } catch (IOException e) {
//                Log.e("IOException", e.toString());
//            } finally {
//                if (mifareClassCard != null) {
//                    mifareClassCard.debugPrint();
//                }
//            }
//        }// End of method
//        return "";
//    }

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
}
