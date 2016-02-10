/**
 * 
 */
package com.qiyou.util;

import com.qiyou.R;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @齐游
 * @2016-2-6
 * @author 谞臣 载入动画
 */
public class LoadingAnim {
	public static void ininLoadingView(View view) {
		ImageView loadingImageView = (ImageView) view
				.findViewById(R.id.loading);
		TextView loadingTextView = (TextView) view
				.findViewById(R.id.loading_text);
		loadingImageView.setBackgroundResource(R.anim.loading);
		final AnimationDrawable animationDrawable = (AnimationDrawable) loadingImageView
				.getBackground();
		loadingImageView.post(new Runnable() {

			@Override
			public void run() {
				animationDrawable.start();
			}
		});
	}

	public static void ininLoading(Activity activity) {
		ImageView loadingImageView = (ImageView) activity
				.findViewById(R.id.loading);
		TextView loadingTextView = (TextView) activity
				.findViewById(R.id.loading_text);
		loadingImageView.setBackgroundResource(R.anim.loading);
		final AnimationDrawable animationDrawable = (AnimationDrawable) loadingImageView
				.getBackground();
		loadingImageView.post(new Runnable() {

			@Override
			public void run() {
				animationDrawable.start();
			}
		});
	}
}
