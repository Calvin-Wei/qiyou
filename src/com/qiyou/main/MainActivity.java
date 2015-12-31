package com.qiyou.main;


import com.qiyou.ggl.network.NetUtils;
import com.qiyou.ggl.view.MyPagerGalleryView;
import com.qiyou.ggl.view.MyPagerGalleryView.MyOnItemClickListener;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

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

	/**
	 * 网络监听
	 */
	NetReceiver mReceiver = new NetReceiver();
	IntentFilter mFilter = new IntentFilter();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/**
		 * android开源框架，这里用于加载网络图片
		 */
//		FinalBitmap.create(this);
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