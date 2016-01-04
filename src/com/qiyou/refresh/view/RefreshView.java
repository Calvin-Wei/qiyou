/**
 * 
 */
package com.qiyou.refresh.view;

import java.util.Map;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;

/**
 * @齐游
 * @2016-1-4
 * @author 谞臣 绘制下拉刷新动画
 */
public class RefreshView extends Drawable implements Drawable.Callback,
		Animatable {
	private static final float SCALE_START_PERCENT = 0.5f;// 开始比例百分比为0.5
	private static final int ANIMATION_DURATION = 1000;// 动画持续时间为1秒

	private static final float SIDE_CLOUDS_INITIAL_SCALE = 1.05f;// 边缘云彩初始规模
	private static final float SIDE_CLOUDS_FINAL_SCALE = 1.55f;// 边缘云彩最终规模

	private static final float CENTER_CLOUDS_INITIAL_SCALE = 0.8f;// 中间云彩初始规模
	private static final float CENTER_CLOUDS_FINAL_SCALE = 1.30f;// 中间最终云彩规模
	// 加速动画
	private static final Interpolator ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();

	private static final int LOADING_ANIMATION_COEFFICIENT = 80;// 载入动画率
	private static final int SLOW_DOWN_ANIMATION_COEFFICIENT = 6;// 降速动画率

	private static final int WIND_SET_AMOUNT = 10;// 风速设置
	private static final int Y_SIDE_CLOUDS_SLOW_DOWN_COF = 4;// 边缘云彩Y坐标(降速)
	private static final int X_SIDE_CLOUDS_SLOW_DOWN_COF = 2;// 边缘云彩X坐标(降速)
	private static final int MIN_WIND_LINE_WIDTH = 50;// 最低风宽度
	private static final int MAX_WIND_LINE_WIDTH = 300;// 最大风宽度
	private static final int MIN_WIND_X_OFFSET = 1000;// 风x坐标
	private static final int MAX_WIND_X_OFFSET = 2000;// 风y坐标
	private static final int RANDOM_Y_COEFFICIENT = 5;// 随机y

	private Context mContext;
	private PullToRefreshView mParent;
	private Matrix mMatrix;
	private Matrix mAdditionalMatrix;
	private Animation mAnimation;

	private int mTop;
	private int mScreenWidth;
	private boolean mInverseDirection;

	// KEY: Y position, Value: X offset 风的x,y坐标
	/**
	 * 注释待完成
	 */
	private Map<Float, Float> mWinds;
	private Paint mWindPaint;
	private float mWindLineWidth;
	private boolean mNewWindSet;

	private int mJetWidthCenter;
	private int mJetHeightCenter;
	private float mJetTopOffset;
	private int mFrontCloudHeightCenter;
	private int mFrontCloudWidthCenter;
	private int mRightCloudsWidthCenter;
	private int mRightCloudsHeightCenter;
	private int mLeftCloudsWidthCenter;
	private int mLeftCloudsHeightCenter;

	private float mPercent = 0.0f;

	private Bitmap mJet;
	private Bitmap mFrontClouds;
	private Bitmap mLeftClouds;
	private Bitmap mRightClouds;

	private boolean isRefreshing = false;
	private float mLoadingAnimationTime;
	private float mLastAnimationTime;

	private Random mRandom;
	private boolean mEndOfRefreshing;

	private enum AnimationPart {
		FIRST, SECOND, THIRD, FOURTH
	}

	public void start() {
		// TODO Auto-generated method stub

	}

	public void stop() {
		// TODO Auto-generated method stub

	}

	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	public void invalidateDrawable(Drawable who) {

	}

	public void scheduleDrawable(Drawable who, Runnable what, long when) {
		// TODO Auto-generated method stub

	}

	public void unscheduleDrawable(Drawable who, Runnable what) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

}
