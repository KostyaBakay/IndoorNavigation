package com.example.fooandbar.ibeacon.utils;

import android.content.Context;

import com.example.fooandbar.ibeacon.data.PreferencesManager;

public class PreferencesUtil {

	public static String readName(Context context) {
		return PreferencesManager.from(context).getString(Const.USER_NAME);
	}

	public static void writeName(Context context, String name) {
		PreferencesManager.from(context).setString(Const.USER_NAME, name).commit();
	}

	public static String readId(Context context) {
		return PreferencesManager.from(context).getString(Const.USER_ID);
	}

	public static void writeId(Context context, String user_id) {
		PreferencesManager.from(context).setString(Const.USER_ID, user_id).commit();
	}
}
