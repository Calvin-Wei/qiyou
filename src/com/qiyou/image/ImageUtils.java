/**
 * 
 */
package com.qiyou.image;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * @齐游
 * @2015-12-29
 * @author 谞臣 图片工具类
 */
public class ImageUtils {
	public static Bitmap resizeImageByWidth(Bitmap defaultBitmap,
			int targetWidth) {
		int rawWidth = defaultBitmap.getWidth();
		int rawHeight = defaultBitmap.getHeight();
		float targetHeight = targetWidth * (float) rawHeight / (float) rawWidth;
		float scaleWidth = targetWidth / (float) rawWidth;
		float scaleHeight = targetHeight / (float) rawHeight;
		// Matrix是一个3x3的矩阵,用来进行图像处理
		Matrix localMatrix = new Matrix();
		localMatrix.postScale(scaleHeight, scaleWidth);
		return Bitmap.createBitmap(defaultBitmap, 0, 0, rawWidth, rawHeight,
				localMatrix, true);
	}
}
