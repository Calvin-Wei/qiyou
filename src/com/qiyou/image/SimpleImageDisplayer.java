/**
 * 
 */
package com.qiyou.image;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;

/**
 * @齐游
 * @2015-12-29
 * @author 谞臣
 * 图片的显示方式
 */
public class SimpleImageDisplayer implements BitmapDisplayer {

	private int targetWidth;

	public SimpleImageDisplayer(int targetWidth) {
		this.targetWidth = targetWidth;
	}

	public Bitmap display(Bitmap bitmap, ImageView imageView,
			LoadedFrom loadedFrom) {
		if (bitmap != null) {
			bitmap = ImageUtils.resizeImageByWidth(bitmap, targetWidth);
		}
		imageView.setImageBitmap(bitmap);
		return bitmap;
	}

}
