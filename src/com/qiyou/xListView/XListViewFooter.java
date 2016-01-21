/**
 * 
 */
package com.qiyou.xListView;

import com.qiyou.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * @齐游
 * @2016-1-20
 * @author 谞臣
 * @category 下拉刷新底部
 */
public class XListViewFooter extends LinearLayout {

	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;

	private Context mContext;

	private View mContextView;
	private View mProgressBar;

	private TextView mHintView;

	/**
	 * @param context
	 */
	public XListViewFooter(Context context) {
		super(context);
		initView(context);
	}

	public XListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * 设置状态
	 * 
	 * @param state
	 */
	public void setState(int state) {
		// 正常状态
		mProgressBar.setVisibility(View.INVISIBLE);
		mHintView.setVisibility(View.INVISIBLE);
		if (state == STATE_READY) {
			// 松开显示更多的查询结果
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_footer_hint_ready);
		} else if (state == STATE_LOADING) {
			// 加载状态
			mProgressBar.setVisibility(View.VISIBLE);
		} else {
			// 查看更多
			mHintView.setVisibility(View.GONE);
			mHintView.setText(R.string.xlistview_footer_hint_normal);
		}

	}

	/**
	 * 设置底部距离
	 * 
	 * @param height
	 */
	public void setBottomMargin(int height) {
		if (height < 0)
			return;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContextView
				.getLayoutParams();
		lp.bottomMargin = height;
		mContextView.setLayoutParams(lp);
	}

	/**
	 * 获得底部距离
	 * 
	 * @return
	 */
	public int getBottomMargin() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContextView
				.getLayoutParams();
		return lp.bottomMargin;
	}

	/**
	 * 正常状态
	 */
	public void normal() {
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
	}

	/**
	 * 载入状态
	 */
	public void loading() {
		mHintView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.GONE);
	}

	/**
	 * 显示底部
	 */
	public void hide() {
		LinearLayout.LayoutParams lp = (LayoutParams) mContextView
				.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContextView.setLayoutParams(lp);
	}
	
	/**
	 * 显示底部
	 */
	public void show() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContextView.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContextView.setLayoutParams(lp);
	}

	/**
	 * 初始化界面
	 * 
	 * @param context
	 */
	private void initView(Context context) {
		mContext = context;
		LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext)
				.inflate(R.layout.xlistview_footer, null);
		addView(moreView);
		moreView.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

		mContextView = moreView.findViewById(R.id.xlistview_footer_content);
		mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
		mHintView = (TextView) moreView
				.findViewById(R.id.xlistview_footer_hint_textview);
	}

}
