package com.qiyou.ui.view;

import com.qiyou.R;
import com.qiyou.adapter.Adapter_GridView;
import com.qiyou.ggl.network.NetUtils;
import com.qiyou.ggl.view.MyPagerGalleryView;
import com.qiyou.ggl.view.MyPagerGalleryView.MyOnItemClickListener;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends FragmentActivity {

	// 广告栏控件
	private MyPagerGalleryView gallery;
	// 圆点容器
	private LinearLayout ovalLayout;
	/**
	 * 本地图片数组
	 */
	private int[] imageId = new int[] { R.drawable.image03, R.drawable.image01,
			R.drawable.image02 };
	// 当前先加载本地图片
	private String[] txtViewpager = { "1111111111111111111111111111111111",
			"2222222222222222222222222222222222",
			"3333333333333333333333333333333333",
			"4444444444444444444444444444444444" };

	// 搜索文本框
	private TextView search_edit;

	/**
	 * 网络监听
	 */
	NetReceiver mReceiver = new NetReceiver();
	IntentFilter mFilter = new IntentFilter();

	// 分类的九宫格
	private GridView gridView;
	// 九宫格按钮
	private Adapter_GridView adapter_GridView;
	private int[] pic_path_classify = { R.drawable.icon_train,
			R.drawable.icon_plane, R.drawable.icon_jingdian,
			R.drawable.icon_around, R.drawable.icon_ticket,
			R.drawable.icon_hotel, R.drawable.icon_together };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		search_edit = (TextView) findViewById(R.id.search_edit);

		search_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 点击搜索框进入搜索框
				Intent intent = new Intent(MainActivity.this,
						SearchTextActivity.class);
				startActivity(intent);
			}
		});

		/**
		 * android开源框架，这里用于加载网络图片
		 */
		// FinalBitmap.create(this);
		gallery = (MyPagerGalleryView) findViewById(R.id.adgallery);
		ovalLayout = (LinearLayout) findViewById(R.id.ovalLayout);// 圆点组件
		gallery.start(this, imageId, 3000, ovalLayout, R.drawable.dot_focused,
				R.drawable.dot_normal, txtViewpager);
		/**
		 * 点击事件监听
		 */
		gallery.setMyOnItemClickListener(new MyOnItemClickListener() {

			public void onItemClick(int curIndex) {
				Toast.makeText(MainActivity.this, "点击图片为:" + curIndex,
						Toast.LENGTH_SHORT).show();
			}
		});
		// 注册监听网络状态的广播
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mReceiver, mFilter);

		gridView = (GridView) findViewById(R.id.my_gridview);
		gridView.setBackgroundColor(Color.CYAN);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter_GridView = new Adapter_GridView(getApplicationContext(),
				pic_path_classify);
		gridView.setAdapter(adapter_GridView);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
	}

	/**
	 * 
	 * @齐游
	 * @2015-12-27
	 * @author 谞臣 ，监听当前网络状态
	 */
	public class NetReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
				boolean isConnected = NetUtils.isNetworkConnected(context);
				Log.e("网络状态：", "" + isConnected);
				Log.e("wifi状态：", "" + NetUtils.isWifiConnected(context));
				Log.e("移动网络状态：", "" + NetUtils.isMobileConnected(context));
				Log.e("网络连接类型：", "" + NetUtils.getConnectedType(context));
				if (isConnected) {
					Toast.makeText(context, "已经连接网络", Toast.LENGTH_LONG).show();
					doIsConnected();
				} else {
					Toast.makeText(context, "已经断开网络", Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	/**
	 * 如果网络开启中 加载网络数据
	 */
	private void doIsConnected() {
		gallery.start(getApplicationContext(), imageId, 3000, ovalLayout,
				R.drawable.dot_focused, R.drawable.dot_normal, txtViewpager);
		// 添加组件单击事件的监听
		gallery.setMyOnItemClickListener(new MyOnItemClickListener() {

			public void onItemClick(int curIndex) {
				// Toast.makeText(MainActivity.this, "点击的图片下标为:" + curIndex,
				// Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	protected void onDestroy() {
		// 取消注册广播接收器
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		gallery.stopTimer();
		super.onStop();
	}

	@Override
	protected void onRestart() {
		gallery.startTimer();
		super.onRestart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}