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

	public static boolean canVerified(Context context) {
		return PreferencesManager.from(context).getBoolean(Const.CAN_VERIFIED);
	}

	public static void setVerified(Context context, boolean can_first_entry) {
		PreferencesManager.from(context).setBoolean(Const.CAN_VERIFIED, can_first_entry).commit();
	}
	public static boolean readSex(Context context) {
		return PreferencesManager.from(context).getBoolean(Const.IS_MALE);
	}

	public static void writeSex(Context context, boolean isMale) {
		PreferencesManager.from(context).setBoolean(Const.IS_MALE, isMale).commit();
	}
}
