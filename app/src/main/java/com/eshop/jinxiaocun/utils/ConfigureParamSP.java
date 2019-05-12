package com.eshop.jinxiaocun.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ConfigureParamSP {
	private final String FILE_NAME = "CONFIGURE";
	public final static String KEY_SERVERURL = "ServerUrl";
	public final static String KEY_SERVERPORT = "ServerPORT";
	public final static String KEY_BLUETOOTHADDRESS = "BluetoothAddress";
	public final static String KEY_IMMEDIATELY_PRINTER = "immediatelyPrinter";//保存后打印
	public final static String KEY_HASSAVEIP = "hasSaveIP";

	public final static String KEY_PRINT_SIZE = "PrintSize";//打印尺寸
	public final static String KEY_PRINT_NUMBER = "PrintNumber";//打印份数
	public final static String KEY_PRINT_ORDER_NAME = "PrintOrderName";//打印单据名称
	public final static String KEY_PRINT_PAGE_HEADER = "PrintPageHeader";//打印页眉
	public final static String KEY_PRINT_PAGE_FOOT = "PrintPageFoot";//打印页脚

	public final static String KEY_PRINTER_CARD_NO = "printerCardNo";//打印卡号
	public final static String KEY_PRINTER_USER_NAME = "printerUserName";//打印客户姓名
	public final static String KEY_PRINTER_USER_TEL = "printerUserTel";//打印客户联系方式
	public final static String KEY_PRINTER_CASHIER = "printerCashier";//打印收银员

	private static ConfigureParamSP instance;
	public static ConfigureParamSP getInstance() {
		if (null == instance) {
			instance = new ConfigureParamSP();
		}
		return instance;
	}

	public boolean saveValue(Context context, String key, String value) {
		Editor editor = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE).edit();
		editor.putString(key, value);

		return editor.commit();
	}

	public boolean saveValue(Context context, String key, int value) {
		Editor editor = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE).edit();
		editor.putInt(key, value);

		return editor.commit();
	}

	public boolean saveValue(Context context, String key, boolean value) {
		Editor editor = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE).edit();
		editor.putBoolean(key, value);

		return editor.commit();
	}

	public String getValue(Context context, String key, String defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.getString(key, defaultValue);
	}

	public int getValue(Context context, String key, int defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.getInt(key, defaultValue);
	}

	public boolean getValue(Context context, String key, boolean defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.getBoolean(key, defaultValue);
	}
}
