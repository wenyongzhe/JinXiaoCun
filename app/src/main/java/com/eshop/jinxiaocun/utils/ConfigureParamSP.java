package com.eshop.jinxiaocun.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ConfigureParamSP {
	private final String FILE_NAME = "CONFIGURE";
	public final static String KEY_SERVERURL = "ServerUrl";
	public final static String KEY_SERVERPORT = "ServerPORT";
	public final static String KEY_BRANCH_NO = "branch_no";
	public final static String KEY_POSID = "posid";

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
