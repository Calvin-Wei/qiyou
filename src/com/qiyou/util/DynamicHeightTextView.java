/**
 * 
 */
package com.qiyou.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.TextView;

/**
 * @齐游
 * @2016-2-4
 * @author 谞臣
 * @category 动态高度的文本
 */
public class DynamicHeightTextView extends TextView {
	private double mHeightRatio;

	public DynamicHeightTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DynamicHeightTextView(Context context) {
		super(context);
	}

	public void setHeightRatio(double ratio) {
		if (ratio != mHeightRatio) {
			mHeightRatio = ratio;
			requestLayout();
		}
	}

	public double getHeightRatio() {
		return mHeightRatio;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (mHeightRatio > 0.0) {
			int width = MeasureSpec.getSize(widthMeasureSpec);
			int height = (int) (width * mHeightRatio);
			setMeasuredDimension(width, height);
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
}
