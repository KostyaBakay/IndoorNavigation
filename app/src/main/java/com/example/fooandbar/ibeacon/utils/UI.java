package com.example.fooandbar.ibeacon.utils;

import android.content.res.Resources;
import android.view.View;

public class UI {

	/**
	 * This method showed views
	 * @param views views
	 */
	public static void show(View... views) {
		for (View view : views) view.setVisibility(View.VISIBLE);
	}

	/**
	 * This method hided views
	 * @param views views
	 */
	public static void hide(View... views) {
		for (View view : views) view.setVisibility(View.GONE);
	}

	/**
	 * This method convert dp to px
	 * @param dp dp
	 * @return pixels
	 */
	public static int dpToPx(int dp) {
		return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
	}

	/**
	 * This method convert px to dp
	 * @param px pixels
	 * @return dp
	 */
	public static int pxToDp(int px) {
		return (int) (px / Resources.getSystem().getDisplayMetrics().density);
	}

}
