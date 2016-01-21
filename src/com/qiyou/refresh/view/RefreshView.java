/**
 * 
 */
package com.qiyou.refresh.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.qiyou.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;

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
	 * 
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

	/**
	 * 
	 * 动画的四个阶段
	 */
	private enum AnimationPart {
		FIRST, SECOND, THIRD, FOURTH
	}

	public RefreshView(Context context, PullToRefreshView parent) {
		mContext = context;
		mParent = parent;
		mMatrix = new Matrix();
		mAdditionalMatrix = new Matrix();
		mWinds = new HashMap<>();
		mRandom = new Random();
		mWindPaint = new Paint();// 窗口绘制
		mWindPaint.setColor(mContext.getResources().getColor(
				android.R.color.white));
		mWindPaint.setStrokeWidth(3);// 窗口边距为3
		mWindPaint.setAlpha(50);// 窗口渐变50

		initiateDimens();
		createBitmaps();
		setupAnimations();
	}

	/**
	 * 设置动画
	 */
	private void setupAnimations() {
		mAnimation = new Animation() {
			@Override
			public void applyTransformation(float interpolatedTime,
					@NonNull Transformation t) {
				setLoadingAnimationTime(interpolatedTime);
			}
		};
		mAnimation.setRepeatCount(Animation.INFINITE);
		mAnimation.setRepeatMode(Animation.REVERSE);
		mAnimation.setInterpolator(ACCELERATE_DECELERATE_INTERPOLATOR);
		mAnimation.setDuration(ANIMATION_DURATION);

	}

	/**
	 * 创建位图
	 */
	private void createBitmaps() {
		mLeftClouds = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.clouds_left);// 左侧云彩
		mRightClouds = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.clouds_right);// 右侧云彩
		mFrontClouds = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.clouds_center);// 中间的云彩
		mJet = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.airplane);// 飞机

		mJetWidthCenter = mJet.getWidth() / 2;
		mJetHeightCenter = mJet.getHeight() / 2;
		mFrontCloudWidthCenter = mFrontClouds.getWidth() / 2;
		mFrontCloudHeightCenter = mFrontClouds.getHeight() / 2;

		mRightCloudsWidthCenter = mRightClouds.getWidth() / 2;
		mRightCloudsHeightCenter = mRightClouds.getHeight() / 2;
		mLeftCloudsWidthCenter = mLeftClouds.getWidth() / 2;
		mLeftCloudsHeightCenter = mLeftClouds.getHeight() / 2;
	}

	/**
	 * 初始化窗口的大小
	 */
	private void initiateDimens() {
		// 窗口的宽度
		mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
		mJetTopOffset = mParent.getTotalDragDistance() * 0.5f;
		mTop = -mParent.getTotalDragDistance();
	}

	// 设置顶部和底部
	public void offsetTopAndBottom(int offset) {
		mTop += offset;
		invalidateSelf();
	}

	/**
	 * 无效的视图 绘图的时候需要重绘(在这一点应该无效 )
	 */
	public void invalidateDrawable(Drawable who) {
		final Callback callback = getCallback();
		if (callback != null) {
			callback.invalidateDrawable(this);
		}
	}

	/**
	 * 安排哪个Drawable对象来调用哪一个线程来执行多少时间
	 */
	public void scheduleDrawable(Drawable who, Runnable what, long when) {
		final Callback callback = getCallback();
		if (callback != null) {
			callback.scheduleDrawable(this, what, when);
		}
	}

	/**
	 * 不安排哪个Drawable对象做什么
	 */
	public void unscheduleDrawable(Drawable who, Runnable what) {
		final Callback callback = getCallback();
		if (callback != null) {
			callback.unscheduleDrawable(this, what);
		}
	}

	/**
	 * 动画取决于当前正在刷新的界面
	 * 
	 * @param endOfRefreshing
	 */
	public void setEndOfRefreshing(boolean endOfRefreshing) {
		mEndOfRefreshing = endOfRefreshing;
	}

	@Override
	public void draw(Canvas canvas) {
		final int saveCount = canvas.save();

		canvas.drawColor(mContext.getResources().getColor(
				R.color.sky_background));

		if (isRefreshing) {
			while (mWinds.size() < WIND_SET_AMOUNT) {
				float y = (float) (mParent.getTotalDragDistance() / (Math
						.random() * RANDOM_Y_COEFFICIENT));
				float x = random(MIN_WIND_X_OFFSET, MAX_WIND_X_OFFSET);

				if (mWinds.size() > 1) {
					y = 0;
					while (y == 0) {
						float tmp = (float) (mParent.getTotalDragDistance() / (Math
								.random() * RANDOM_Y_COEFFICIENT));

						for (Map.Entry<Float, Float> wind : mWinds.entrySet()) {
							if (Math.abs(wind.getKey() - tmp) > mParent
									.getTotalDragDistance()
									/ RANDOM_Y_COEFFICIENT) {
								y = tmp;
							} else {
								y = 0;
								break;
							}
						}
					}
				}

				mWinds.put(y, x);
				drawWind(canvas, y, x);
			}

			if (mWinds.size() >= WIND_SET_AMOUNT) {
				for (Map.Entry<Float, Float> wind : mWinds.entrySet()) {
					drawWind(canvas, wind.getKey(), wind.getValue());
				}
			}

			if (mInverseDirection && mNewWindSet) {
				mWinds.clear();
				mNewWindSet = false;
				mWindLineWidth = random(MIN_WIND_LINE_WIDTH,
						MAX_WIND_LINE_WIDTH);
			}

			mLastAnimationTime = mLoadingAnimationTime;
		}

		drawJet(canvas);
		drawSideClouds(canvas);
		drawCenterClouds(canvas);

		canvas.restoreToCount(saveCount);
	}

	/**
	 * 绘制云彩的载入动画
	 * 
	 * @param canvas
	 *            画布
	 * @param y
	 * @param x
	 */
	private void drawWind(Canvas canvas, float y, float xOffset) {
		float cof = (mScreenWidth + xOffset)
				/ (LOADING_ANIMATION_COEFFICIENT / SLOW_DOWN_ANIMATION_COEFFICIENT);
		float time = mLoadingAnimationTime;

		if (mLastAnimationTime - mLoadingAnimationTime > 0) {
			mInverseDirection = true;
			time = (LOADING_ANIMATION_COEFFICIENT / SLOW_DOWN_ANIMATION_COEFFICIENT)
					- mLoadingAnimationTime;
		} else {
			mNewWindSet = true;
			mInverseDirection = false;
		}

		float x = (mScreenWidth - (time * cof)) + xOffset - mWindLineWidth;
		float xEnd = x + mWindLineWidth;

		canvas.drawLine(x, y, xEnd, y, mWindPaint);
	}

	/**
	 * 绘制边缘的云彩
	 * 
	 * @param canvas
	 */
	private void drawSideClouds(Canvas canvas) {
		Matrix matrixLeftClouds = mMatrix;
		Matrix matrixRightClouds = mAdditionalMatrix;
		matrixLeftClouds.reset();
		matrixRightClouds.reset();

		float dragPercent = Math.min(1f, Math.abs(mPercent));

		boolean overdrag = false;

		if (mPercent > 1.0f) {
			overdrag = true;
		}

		float scale;
		float scalePercentDelta = dragPercent - SCALE_START_PERCENT;
		if (scalePercentDelta > 0) {
			float scalePercent = scalePercentDelta
					/ (1.0f - SCALE_START_PERCENT);
			scale = SIDE_CLOUDS_INITIAL_SCALE
					+ (SIDE_CLOUDS_FINAL_SCALE - SIDE_CLOUDS_INITIAL_SCALE)
					* scalePercent;
		} else {
			scale = SIDE_CLOUDS_INITIAL_SCALE;
		}

		float dragYOffset = mParent.getTotalDragDistance()
				* (1.0f - dragPercent);

		int cloudsVisiblePosition = mParent.getTotalDragDistance() / 2
				- mLeftCloudsHeightCenter;

		boolean needMoveCloudsWithContent = false;
		if (dragYOffset < cloudsVisiblePosition) {
			needMoveCloudsWithContent = true;
		}

		float offsetRightX = mScreenWidth - mRightClouds.getWidth();
		float offsetRightY = (needMoveCloudsWithContent ? mParent
				.getTotalDragDistance() * dragPercent - mLeftClouds.getHeight()
				: dragYOffset)
				+ (overdrag ? mTop : 0);

		float offsetLeftX = 0;
		float offsetLeftY = (needMoveCloudsWithContent ? mParent
				.getTotalDragDistance() * dragPercent - mLeftClouds.getHeight()
				: dragYOffset)
				+ (overdrag ? mTop : 0);

		if (isRefreshing) {
			if (checkCurrentAnimationPart(AnimationPart.FIRST)) {
				offsetLeftY += getAnimationPartValue(AnimationPart.FIRST)
						/ Y_SIDE_CLOUDS_SLOW_DOWN_COF;
				offsetRightX -= getAnimationPartValue(AnimationPart.FIRST)
						/ X_SIDE_CLOUDS_SLOW_DOWN_COF;
			} else if (checkCurrentAnimationPart(AnimationPart.SECOND)) {
				offsetLeftY += getAnimationPartValue(AnimationPart.SECOND)
						/ Y_SIDE_CLOUDS_SLOW_DOWN_COF;
				offsetRightX -= getAnimationPartValue(AnimationPart.SECOND)
						/ X_SIDE_CLOUDS_SLOW_DOWN_COF;
			} else if (checkCurrentAnimationPart(AnimationPart.THIRD)) {
				offsetLeftY -= getAnimationPartValue(AnimationPart.THIRD)
						/ Y_SIDE_CLOUDS_SLOW_DOWN_COF;
				offsetRightX += getAnimationPartValue(AnimationPart.THIRD)
						/ X_SIDE_CLOUDS_SLOW_DOWN_COF;
			} else if (checkCurrentAnimationPart(AnimationPart.FOURTH)) {
				offsetLeftY -= getAnimationPartValue(AnimationPart.FOURTH)
						/ X_SIDE_CLOUDS_SLOW_DOWN_COF;
				offsetRightX += getAnimationPartValue(AnimationPart.FOURTH)
						/ Y_SIDE_CLOUDS_SLOW_DOWN_COF;
			}
		}

		matrixRightClouds.postScale(scale, scale, mRightCloudsWidthCenter,
				mRightCloudsHeightCenter);
		matrixRightClouds.postTranslate(offsetRightX, offsetRightY);

		matrixLeftClouds.postScale(scale, scale, mLeftCloudsWidthCenter,
				mLeftCloudsHeightCenter);
		matrixLeftClouds.postTranslate(offsetLeftX, offsetLeftY);

		canvas.drawBitmap(mLeftClouds, matrixLeftClouds, null);
		canvas.drawBitmap(mRightClouds, matrixRightClouds, null);
	}

	/**
	 * 绘制中间的云彩
	 * 
	 * @param canvas
	 */
	private void drawCenterClouds(Canvas canvas) {
		Matrix matrix = mMatrix;
		matrix.reset();
		float dragPercent = Math.min(1f, Math.abs(mPercent));

		float scale;
		float overdragPercent = 0;
		boolean overdrag = false;

		if (mPercent > 1.0f) {
			overdrag = true;
			overdragPercent = Math.abs(1.0f - mPercent);
		}

		float scalePercentDelta = dragPercent - SCALE_START_PERCENT;
		if (scalePercentDelta > 0) {
			float scalePercent = scalePercentDelta
					/ (1.0f - SCALE_START_PERCENT);
			scale = CENTER_CLOUDS_INITIAL_SCALE
					+ (CENTER_CLOUDS_FINAL_SCALE - CENTER_CLOUDS_INITIAL_SCALE)
					* scalePercent;
		} else {
			scale = CENTER_CLOUDS_INITIAL_SCALE;
		}

		float parallaxPercent = 0;
		boolean parallax = false;
		float dragYOffset = mParent.getTotalDragDistance() * dragPercent;
		int startParallaxHeight = mParent.getTotalDragDistance()
				- mFrontCloudHeightCenter;

		if (dragYOffset > startParallaxHeight) {
			parallax = true;
			parallaxPercent = dragYOffset - startParallaxHeight;
		}

		float offsetX = (mScreenWidth / 2) - mFrontCloudWidthCenter;
		float offsetY = dragYOffset
				- (parallax ? mFrontCloudHeightCenter + parallaxPercent
						: mFrontCloudHeightCenter) + (overdrag ? mTop : 0);

		float sx = overdrag ? scale + overdragPercent / 4 : scale;
		float sy = overdrag ? scale + overdragPercent / 2 : scale;

		if (isRefreshing && !overdrag) {
			if (checkCurrentAnimationPart(AnimationPart.FIRST)) {
				sx = scale
						- (getAnimationPartValue(AnimationPart.FIRST) / LOADING_ANIMATION_COEFFICIENT)
						/ 8;
			} else if (checkCurrentAnimationPart(AnimationPart.SECOND)) {
				sx = scale
						- (getAnimationPartValue(AnimationPart.SECOND) / LOADING_ANIMATION_COEFFICIENT)
						/ 8;
			} else if (checkCurrentAnimationPart(AnimationPart.THIRD)) {
				sx = scale
						+ (getAnimationPartValue(AnimationPart.THIRD) / LOADING_ANIMATION_COEFFICIENT)
						/ 6;
			} else if (checkCurrentAnimationPart(AnimationPart.FOURTH)) {
				sx = scale
						+ (getAnimationPartValue(AnimationPart.FOURTH) / LOADING_ANIMATION_COEFFICIENT)
						/ 6;
			}
			sy = sx;
		}

		matrix.postScale(sx, sy, mFrontCloudWidthCenter,
				mFrontCloudHeightCenter);
		matrix.postTranslate(offsetX, offsetY);

		canvas.drawBitmap(mFrontClouds, matrix, null);
	}

	private void drawJet(Canvas canvas) {
		Matrix matrix = mMatrix;
		matrix.reset();

		float dragPercent = mPercent;
		float rotateAngle = 0;

		if (dragPercent > 1.0f && !mEndOfRefreshing) {
			rotateAngle = (dragPercent % 1) * 10;
			dragPercent = 1.0f;
		}

		float offsetX = ((mScreenWidth * dragPercent) / 2) - mJetWidthCenter;

		float offsetY = mJetTopOffset + (mParent.getTotalDragDistance() / 2)
				* (1.0f - dragPercent) - mJetHeightCenter;

		if (isRefreshing) {
			if (checkCurrentAnimationPart(AnimationPart.FIRST)) {
				offsetY -= getAnimationPartValue(AnimationPart.FIRST);
			} else if (checkCurrentAnimationPart(AnimationPart.SECOND)) {
				offsetY -= getAnimationPartValue(AnimationPart.SECOND);
			} else if (checkCurrentAnimationPart(AnimationPart.THIRD)) {
				offsetY += getAnimationPartValue(AnimationPart.THIRD);
			} else if (checkCurrentAnimationPart(AnimationPart.FOURTH)) {
				offsetY += getAnimationPartValue(AnimationPart.FOURTH);
			}
		}

		matrix.setTranslate(offsetX, offsetY);

		if (dragPercent == 1.0f) {
			matrix.preRotate(rotateAngle, mJetWidthCenter, mJetHeightCenter);
		}

		canvas.drawBitmap(mJet, matrix, null);
	}

	/**
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public float random(int min, int max) {
		return mRandom.nextInt((max - min) + 1) + min;
	}

	/**
	 * 需要一个特殊的值来区别是那一部分的动画
	 * 
	 * @param part
	 * @return
	 */
	private float getAnimationPartValue(AnimationPart part) {
		switch (part) {
		case FIRST: {
			return mLoadingAnimationTime;
		}
		case SECOND: {
			return getAnimationTimePart(AnimationPart.FOURTH)
					- (mLoadingAnimationTime - getAnimationTimePart(AnimationPart.FOURTH));
		}
		case THIRD: {
			return mLoadingAnimationTime
					- getAnimationTimePart(AnimationPart.SECOND);
		}
		case FOURTH: {
			return getAnimationTimePart(AnimationPart.THIRD)
					- (mLoadingAnimationTime - getAnimationTimePart(AnimationPart.FOURTH));
		}
		default:
			return 0;
		}
	}

	/**
	 * 检查当前的动画是那一部分
	 * 
	 * @param part
	 * @return
	 */
	private boolean checkCurrentAnimationPart(AnimationPart part) {
		switch (part) {
		case FIRST: {
			return mLoadingAnimationTime < getAnimationTimePart(AnimationPart.FOURTH);
		}
		case SECOND:
		case THIRD: {
			return mLoadingAnimationTime < getAnimationTimePart(part);
		}
		case FOURTH: {
			return mLoadingAnimationTime > getAnimationTimePart(AnimationPart.THIRD);
		}
		default:
			return false;
		}
	}

	/**
	 * 每一部分动画的动画时间长度
	 * 
	 * @param part
	 * @return
	 */
	private int getAnimationTimePart(AnimationPart part) {
		switch (part) {
		case SECOND: {
			return LOADING_ANIMATION_COEFFICIENT / 2;
		}
		case THIRD: {
			return getAnimationTimePart(AnimationPart.FOURTH) * 3;
		}
		case FOURTH: {
			return LOADING_ANIMATION_COEFFICIENT / 4;
		}
		default:
			return 0;
		}
	}

	@Override
	public void setAlpha(int alpha) {

	}

	@Override
	public void setColorFilter(ColorFilter cf) {

	}

	public void setPercent(float percent) {
		mPercent = percent;
	}

	public void resetOriginals() {
		setPercent(0);
	}

	@Override
	protected void onBoundsChange(@NonNull Rect bounds) {
		super.onBoundsChange(bounds);
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

	public boolean isRunning() {
		return false;
	}

	/**
	 * 开始动画
	 */
	@Override
	public void start() {
		mAnimation.reset();
		isRefreshing = true;
		mParent.startAnimation(mAnimation);
		mLastAnimationTime = 0;
		mWinds.clear();
		mWindLineWidth = random(MIN_WIND_LINE_WIDTH, MAX_WIND_LINE_WIDTH);
	}

	/**
	 * 终止动画
	 */
	@Override
	public void stop() {
		mParent.clearAnimation();
		isRefreshing = false;
		mEndOfRefreshing = false;
		resetOriginals();
	}

	// 设置载入动画的时间
	private void setLoadingAnimationTime(float loadingAnimationTime) {
		/** SLOW DOWN ANIMATION IN {@link #SLOW_DOWN_ANIMATION_COEFFICIENT} time */
		mLoadingAnimationTime = LOADING_ANIMATION_COEFFICIENT
				* (loadingAnimationTime / SLOW_DOWN_ANIMATION_COEFFICIENT);
		invalidateSelf();
	}
}
