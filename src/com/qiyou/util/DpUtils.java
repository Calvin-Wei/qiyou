/**
 * 
 */
package com.qiyou.util;

import android.content.Context;

/**
 * @齐游
 * @2016-2-12
 * @author 谞臣
 * @category 像素转换工具类
 */
public class DpUtils {
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
