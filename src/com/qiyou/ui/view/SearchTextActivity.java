/**
 * 
 */
package com.qiyou.ui.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import com.qiyou.R;
import com.qiyou.adapter.Adapter_ListView_text;
import com.qiyou.http.CU_JSONResolve;
import com.qiyou.http.GetHttp;
import com.qiyou.xListView.XListView;
import com.qiyou.xListView.XListView.IXListViewListener;
import com.zdp.aseo.content.AseoZdpAseo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @齐游
 * @2016-1-20
 * @author 谞臣
 * @category 搜索界面(显示多个旅游景点的信息)
 */
public class SearchTextActivity extends Activity implements OnTouchListener,
		IXListViewListener {

	// 显示旅游景点的列表
	private XListView listView;
	// 整个顶部的搜索控件，用于隐藏和显示底部的整个控件
	private LinearLayout ll_search;
	// 返回按钮
	private ImageView iv_back;

	private EditText ed_search;

	private AnimationSet animationSet;
	/** 第一次按下屏幕时的Y坐标 */
	float fist_down_Y = 0;
	/** 请求数据的页数 */
	private int pageIndex = 0;
	/** 存储网络返回的数据 */
	private HashMap<String, Object> hashMap;
	/** 存储网络返回的数据中的data字段 */
	private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_text);
		initView();
		// 请求网络数据
		new SearchTask().execute();
	}

	private void initView() {
//		AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
		ll_search = (LinearLayout) findViewById(R.id.ll_choice);
		ed_search = (EditText) findViewById(R.id.ed_Searchware);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		listView = (XListView) findViewById(R.id.listView_text);
		listView.setOnTouchListener(this);
		listView.setXListViewListener(this);
		// 设置可以进行下拉加载的功能
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(true);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// 事件的y坐标
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 第一次按下时的坐标
			fist_down_Y = y;
			break;
		case MotionEvent.ACTION_MOVE:
			// 向上滑动，隐藏搜索栏
			if (fist_down_Y - y > 250 && ll_search.isShown()) {
				if (animationSet != null) {
					animationSet = null;
				}
				animationSet = (AnimationSet) AnimationUtils.loadAnimation(
						this, R.anim.up_out);
				ll_search.startAnimation(animationSet);
				ll_search.setY(-100);
				ll_search.setVisibility(View.GONE);
			}
			// 向下滑动，显示搜索栏
			if (y - fist_down_Y > 250 && !ll_search.isShown()) {
				if (animationSet != null) {
					animationSet = null;
				}
				animationSet = (AnimationSet) AnimationUtils.loadAnimation(
						this, R.anim.down_in);
				ll_search.startAnimation(animationSet);
				ll_search.setY(0);
				ll_search.setVisibility(View.VISIBLE);
			}
			break;

		}
		return false;
	}

	private class SearchTask extends
			AsyncTask<Void, Void, HashMap<String, Object>> {

		ProgressDialog dialog = null;

		@Override
		protected void onPreExecute() {
			if (dialog == null) {
				dialog = ProgressDialog.show(SearchTextActivity.this, "",
						"正在加载...");
				dialog.show();
			}

		}

		@Override
		protected HashMap<String, Object> doInBackground(Void... arg0) {
			String url = "";
			if (pageIndex == 0) {
				url = "";
			} else {
				url = ""
						+ pageIndex;
			}
			// 请求数据，返回json
			String json = GetHttp.RequstGetHttp(url);
			// 第一层的数组类型字段
			String[] LIST1_field = { "data" };

			// 第二层的对象类型字段
			String[] STR2_field = { "id", "name", "price", "sale_num",
					"address", "pic" };
			ArrayList<String[]> aL_STR2_field = new ArrayList<String[]>();
			// 第二层的对象类型字段放入第一层的数组类型字段中
			aL_STR2_field.add(STR2_field);
			// 解析返回的json
			hashMap = CU_JSONResolve.parseHashMap2(json, null, LIST1_field,
					aL_STR2_field);
			return hashMap;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(HashMap<String, Object> result) {

			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
				dialog = null;
			}

			// 如果网络数据请求失败，那么显示默认的数据
			if (result != null && result.get("data") != null) {
				// 得到data字段的数据
				arrayList
						.addAll((Collection<? extends HashMap<String, Object>>) result
								.get("data"));
				listView.setAdapter(new Adapter_ListView_text(
						SearchTextActivity.this, arrayList));
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						Intent intent = new Intent(SearchTextActivity.this,
								SpotActivity.class);
						startActivity(intent);
					}
				});

			} else {
				listView.setAdapter(new Adapter_ListView_text(
						SearchTextActivity.this));
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						Intent intent = new Intent(SearchTextActivity.this,
								SpotActivity.class);
						startActivity(intent);
					}
				});
			}

			// 停止刷新和加载
			onLoad();

		}

	}

	/**
	 * 刷新
	 */
	@Override
	public void onRefresh() {
		// 刷新数据
		pageIndex = 0;
		arrayList.clear();
		new SearchTask().execute();
		// 停止刷新和加载
		onLoad();
	}

	/**
	 * 加载更多
	 */
	@Override
	public void onLoadMore() {
		pageIndex += 1;
		if (pageIndex >= 4) {
			Toast.makeText(this, "已经最后一页了", Toast.LENGTH_SHORT).show();
			onLoad();
			return;
		}
		new SearchTask().execute();
	}

	/**
	 * 停止刷新和加载
	 */
	private void onLoad() {
		listView.stopRefresh();
		// 停止刷新加载更多
		listView.stopLoadMore();
		// 设置最后一次刷新时间
		listView.setRefreshTime(getCurrentTime(System.currentTimeMillis()));
	}

	/**
	 * 简单的时间格式
	 */
	public static SimpleDateFormat mDateFormat = new SimpleDateFormat();

	public static String getCurrentTime(long time) {
		if (0 == time) {
			return "";
		}
		return mDateFormat.format(new Date(time));

	}

}
