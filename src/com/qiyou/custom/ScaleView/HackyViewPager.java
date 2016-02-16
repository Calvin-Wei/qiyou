package com.qiyou.custom.ScaleView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 
 * 
 * @QiYou
 * @2016-2-16
 * @author 谞臣
 * 
 */
public class HackyViewPager extends ViewPager {

	/**
	 * @param context
	 */
	public HackyViewPager(Context context) {
		super(context);
	}

	public HackyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 暂时不进行处理
	 */
	@Override
	public boolean onInterceptHoverEvent(MotionEvent event) {
		try {
			return super.onInterceptHoverEvent(event);
		} catch (IllegalArgumentException e) {
			return false;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

}
