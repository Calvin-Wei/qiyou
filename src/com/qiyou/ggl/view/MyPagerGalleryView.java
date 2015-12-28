/**
 * 
 */
package com.qiyou.ggl.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.tsz.afinal.FinalBitmap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnTouchListener;

/**
 * @齐游
 * @2015-12-27
 * @author 谞臣 无限滚动广告栏组件
 */
@SuppressWarnings("deprecation")
public class MyPagerGalleryView extends Gallery implements
		android.widget.AdapterView.OnItemClickListener,
		android.widget.AdapterView.OnItemSelectedListener, OnTouchListener {
	// 显示的Activity
	private Context mContext;
	// 条目单击事件接口
	private MyOnItemClickListener mMyOnItemClickListener;
	// 图片切换时间
	private int mSwitchTime;
	// 自动滚动的定时器
	private Timer mTimer;
	// 圆点容器
	private LinearLayout mOvalLayout;
	// 当前选中的数组索引
	private int curIndex = 0;
	// 上次选中的数组索引
	private int oldIndex = 0;
	// 园点选中时的背景ID
	private int mFocusedId;
	// 园点正常时的背景ID
	private int mNormalId;
	// 图片资源ID组
	private int[] mAdsId;
	// 图片网络路径数组
	private String[] mUris;
	// ImageView组
	private List<ImageView> listImgs;
	// 广告条上面的textView控件
	private TextView adgallerytxt;
	// 广告条上的文字数组
	private String[] txtViewpager;

	public MyPagerGalleryView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyPagerGalleryView(Context context) {
		super(context);
	}

	public MyPagerGalleryView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 
	 * @param context
	 *            显示的Activity，不能为null
	 * @param mris
	 *            图片的网络路径数组，为空时加载adsId
	 * @param adsId
	 *            图片组资源ID，测试用
	 * @param switchTime
	 *            图片切换时间 ，0为不自动切换
	 * @param ovalLayout
	 *            园点容器，可为空
	 * @param focusedId
	 *            圆点选中时的背景ID，圆点容器可为空 0
	 * @param normalId
	 *            园点正常时的背景ID，园点容器为空 0
	 * @param adgallerytxt
	 *            广告条上面的textView控件
	 * @param txtViewpager
	 *            广告条上的每一条文字的数组
	 */
	public void start(Context context, String[] mris, int[] adsId,
			int switchTime, LinearLayout ovalLayout, int focusedId,
			int normalId, TextView adgallerytxt, String[] txtViewpager) {
		this.mContext = context;
		this.mUris = mris;
		this.mAdsId = adsId;
		this.mSwitchTime = switchTime;
		this.mOvalLayout = ovalLayout;
		this.mFocusedId = focusedId;
		this.mNormalId = normalId;
		this.adgallerytxt = adgallerytxt;
		this.txtViewpager = txtViewpager;

		initImages();// 初始化广告栏图片组
		setAdapter(new AdAdapter());// 配置广告栏自动切换配置器
		this.setOnItemClickListener(this);// 组件点击监听器
		this.setOnTouchListener(this);// 触摸监听器
		this.setOnItemSelectedListener(this);// 组件被选中监听器
		this.setSoundEffectsEnabled(false);// 声音不启用
		this.setAnimationDuration(700);// 每0.7秒切换
		this.setUnselectedAlpha(1);// 圆点没有选中时的透明度
		// 间距设置
		setSpacing(0);
		// 取靠近中间图片数组的整数倍
		setSelection((getCount() / 2 / listImgs.size()) * listImgs.size());
		setFocusableInTouchMode(true);// 设置焦点
		// 设置广告栏圆点
		initOvalLayout();
		startTimer();// 开始自动滚动
	}

	/**
	 * 初始化广告栏上的图片组
	 */
	private void initImages() {
		listImgs = new ArrayList<ImageView>();// ImageView组件图片组
		int len = mUris != null ? mUris.length : mAdsId.length;// 是否加载网络路径图片组
		for (int i = 0; i < len; i++) {
			ImageView imageView = new ImageView(mContext);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);// 缩放格式--将图片不暗比例扩大缩小，到当前view中显示
			imageView.setLayoutParams(new Gallery.LayoutParams(
					Gallery.LayoutParams.MATCH_PARENT,
					Gallery.LayoutParams.MATCH_PARENT));// 设置ImageView组件的大小
			if (mUris == null) {// 本地加载图片
				imageView.setImageResource(mAdsId[i]);// 为ImageView设置要显示的图片
			} else {
				/**
				 * android Afinal开源框架中的FinalBitmap组件,
				 * imageView加载bitmap时无需考虑bitmap加载过程中的oom(内存溢出)
				 * 和android容器快速滑动时候出现的图片错位等现象,FinalBitmap可以配置线程加载数量，
				 * 缓存大小，缓存路径，加载显示动画等.FinalBitmap可以自定义下载器，用来扩展其他协议显示网络图片
				 * 也可以自定义birmap显示器,在ImageView显示图片的时候播放动画等(默认是渐变动画显示).
				 */
				FinalBitmap.create(mContext)
						.display(imageView, mUris[i], imageView.getWidth(),
								imageView.getHeight(), null, null);
			}
			listImgs.add(imageView);// 向ImageView组添加
		}
	}

	/**
	 * 初始化广告栏上的圆点
	 */
	private void initOvalLayout() {
		if (mOvalLayout != null && listImgs.size() < 2) {// 如果广告栏中只有一个图片,不显示园点
			mOvalLayout.removeAllViews();
			mOvalLayout.getLayoutParams().height = 0;
		} else if (mOvalLayout != null) {
			mOvalLayout.removeAllViews();
			// 设置圆点的大小(也可是是圆点窗口的其他值0.7可更改)
			int Ovalheight = (int) (mOvalLayout.getLayoutParams().height * 0.7);
			// 设置圆点的左右外边距(也可更改)
			int Ovalmargin = (int) (mOvalLayout.getLayoutParams().height * 0.2);
			android.widget.LinearLayout.LayoutParams layoutParams = new android.widget.LinearLayout.LayoutParams(
					Ovalheight, Ovalheight);
			layoutParams.setMargins(Ovalmargin, 0, Ovalmargin, 0);
			for (int i = 0; i < listImgs.size(); i++) {
				View v = new View(mContext);// 圆点
				v.setLayoutParams(layoutParams);// 将已经设置好的圆点大小和边距
				v.setBackgroundResource(mNormalId);// 设置圆点状态为正常状态
				mOvalLayout.addView(v);
			}
			mOvalLayout.getChildAt(0).setBackgroundResource(mFocusedId);
		}
	}

	/**
	 * 
	 * @齐游
	 * @2015-12-27
	 * @author 谞臣 设置广告栏自动循环的适配器
	 */
	class AdAdapter extends BaseAdapter {

		/**
		 * 有一张图片是不能进行滚动 或者就在int范围内
		 */
		public int getCount() {
			if (listImgs.size() < 2) {
				return listImgs.size();
			}
			return Integer.MAX_VALUE;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			return listImgs.get(position % listImgs.size());// 返回ImageView
		}

	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		int kEvent;
		if (isScrollingLeft(e1, e2)) {// 检查是否往左滑动
			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
		} else {// 检查是否向右滑动
			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(kEvent, null);
		return true;
	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > (e1.getX() + 50);
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

	/**
	 * 触摸事件
	 */
	public boolean onTouch(View v, MotionEvent event) {
		// Action_up手势结束和手势释放，Action_Cancel手势失败(失败情况是自动滚动)
		if (MotionEvent.ACTION_UP == event.getAction()
				|| MotionEvent.ACTION_CANCEL == event.getAction()) {
			startTimer();// 开始自动滚动任务
		} else {
			stopTimer();// 停止自动滚动任务
		}
		return false;
	}

	/**
	 * 图片切换事件
	 */
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {

		curIndex = position % listImgs.size();// 当前广告栏图片的索引
		if (mOvalLayout != null && listImgs.size() > 1) {// 有圆点容器，且图片数量大于1张
			mOvalLayout.getChildAt(oldIndex).setBackgroundResource(mNormalId);// 圆点取消
			mOvalLayout.getChildAt(curIndex).setBackgroundResource(mFocusedId);// 圆点选中
			int page = curIndex + 1;// 当前是第几个图片
			adgallerytxt.setText("" + page);
			oldIndex = curIndex;
		}
	}

	public void onNothingSelected(AdapterView<?> parent) {
	}

	/**
	 * 组件被点击事件
	 */
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (mMyOnItemClickListener != null) {
			mMyOnItemClickListener.onItemClick(curIndex);
		}
	}

	/**
	 * 设置组件点击事件监听器
	 * 
	 * @param listener
	 */
	public void setMyOnItemClickListener(MyOnItemClickListener listener) {
		mMyOnItemClickListener = listener;
	}

	/**
	 * 
	 * @齐游
	 * @2015-12-27
	 * @author 谞臣 组件点击事件监听器接口.
	 */
	public interface MyOnItemClickListener {
		/**
		 * 
		 * @param curIndex
		 *            当前条目在数组中的下标
		 */
		void onItemClick(int curIndex);
	}

	/**
	 * 停止自动滚动任务
	 */
	public void stopTimer() {
		if (mTimer != null) {
			mTimer.cancel();// 关闭定时器
			mTimer = null;
		}
	}

	/**
	 * 开始自动滚动任务,前提，图片数量大于1
	 */
	public void startTimer() {
		if (mTimer == null && listImgs.size() > 1 && mSwitchTime > 0) {
			mTimer = new Timer();
			mTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					handler.sendMessage(handler.obtainMessage(1));
				}
			}, mSwitchTime, mSwitchTime);
		}
	}

	/**
	 * 处理定时滚动任务
	 */
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// 如果没有间距(spacing)会导致OnKeyDown()失效
			// 如果onKeyDown()失效前先调用onScroll(null,1,0)滑动刷新处理
			onScroll(null, null, 1, 0);
			onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
		}
	};
}
