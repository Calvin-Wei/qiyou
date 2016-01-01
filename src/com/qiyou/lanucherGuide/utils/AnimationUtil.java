/**
 * 
 */
package com.qiyou.lanucherGuide.utils;

import com.qiyou.R;

import android.app.Activity;
import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;

/**
 * @齐游
 * @2015-12-29
 * @author 谞臣
 * 
 */
public class AnimationUtil {
	/**
	 * 
	 * @return
	 */
	public static LayoutAnimationController getListAnimTranslate() {
		AnimationSet set = new AnimationSet(true);
		Animation animation = new AlphaAnimation(0.0f, 1.0f);// 透明度动画
		animation.setDuration(500);
		set.addAnimation(animation);
		// 位移动画()
		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(800);// 持续时间
		set.addAnimation(animation);
		LayoutAnimationController controller = new LayoutAnimationController(
				set, 0.5f);
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		return controller;
	}

	/**
	 * 退出Activity时的动画
	 * 
	 * @param context
	 */
	public static void finishActivityAnimation(Context context) {
		((Activity) context).finish();
		((Activity) context).overridePendingTransition(R.anim.zoom_enter,
				R.anim.zoom_exit);
	}

	/**
	 * Zoom动画
	 * 
	 * @param context
	 */
	public static void activityZoomAnimation(Context context) {
		((Activity) context).overridePendingTransition(R.anim.zoom_enter,
				R.anim.zoom_exit);
	}
}
