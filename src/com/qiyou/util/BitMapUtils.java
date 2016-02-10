/**
 * 
 */
package com.qiyou.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * @齐游
 * @2016-2-4
 * @author 谞臣
 * @category图片处理
 */
public class BitMapUtils {
	public BitMapUtils() {
	}

	/**
	 * @param data
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeBitmap(byte[] data, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, 0, data.length, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		options.inPurgeable = true;
		options.inInputShareable = false;
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(data, 0, data.length, options);
	}

	/**
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}
}
