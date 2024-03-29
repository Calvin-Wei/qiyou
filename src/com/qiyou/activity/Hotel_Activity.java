/**
 * 
 */
package com.qiyou.activity;

import java.util.ArrayList;

import com.qiyou.R;
import com.qiyou.MyView.SpotPopWindow;
import com.qiyou.MyView.SpotPopWindow.OnItemClickListener;
import com.qiyou.adapter.Adapter_ListView_Hotel;
import com.qiyou.adapter.Adapter_ListView_Team;
import com.qiyou.custom.ScaleView.HackyViewPager;
import com.qiyou.ui.view.BuynowActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @齐游
 * @2016-2-15
 * @author 谞臣
 * 
 */
public class Hotel_Activity extends Activity implements OnItemClickListener,
		OnClickListener {
	// nfc适配器
	NfcAdapter nfcAdapter;

	private HackyViewPager viewPager;
	private ArrayList<View> allListView;
	private int[] resId = { R.drawable.suba_hotel };
	private ImageView iv_spot_collection, buy_now;
	private SpotPopWindow popWindow;
	private LinearLayout all_choice_layout = null;
	boolean isClickBuy = false;
	private static boolean isCollection = false;
	private int position = 0;

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotel_show);
		// 得到保存的收藏信息
		getSaveCollection();
		initView();
		popWindow = new SpotPopWindow(this);
		popWindow.setOnItemClickListener(this);
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		// 获取默认的NFC控制器
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (nfcAdapter == null) {
//			Toast.makeText(this, "您的手机不支持NFC", Toast.LENGTH_SHORT).show();
		}

		listView = (ListView) findViewById(R.id.hotel_listView_Detail);
		listView.setFocusable(false);
		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		listView.setAdapter(new Adapter_ListView_Hotel(this));

		((ImageView) findViewById(R.id.hotel_iv_back)).setOnClickListener(this);
		buy_now = (ImageView) findViewById(R.id.hotel_buy_now);
		buy_now.setOnClickListener(this);
		iv_spot_collection = (ImageView) findViewById(R.id.iv_hotel_collection);
		iv_spot_collection.setOnClickListener(this);
		all_choice_layout = (LinearLayout) findViewById(R.id.hotel_all_choice_layout);
		initViewPager();
		// 如果当前景点已经收藏，显示收藏后的效果
		if (isCollection) {
			iv_spot_collection.setImageResource(R.drawable.second_2_collection);
		}
	}

	/**
			 * 
			 */
	@SuppressWarnings("deprecation")
	private void initViewPager() {
		if (allListView != null) {
			allListView.clear();
			allListView = null;
		}
		allListView = new ArrayList<View>();
		for (int i = 0; i < resId.length; i++) {
			View view = LayoutInflater.from(this).inflate(R.layout.pic_item,
					null);
			ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
			imageView.setImageResource(resId[i]);
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
				}
			});
			allListView.add(view);
		}
		viewPager = (HackyViewPager) findViewById(R.id.iv_hotel);
		ViewPagerAdapter adapter = new ViewPagerAdapter();
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				position = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		viewPager.setAdapter(adapter);
	}

	private class ViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return allListView.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (View) arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = allListView.get(position);
			container.addView(view);
			return view;
		}

	}

	/**
	 * 得到保存的是否添加收藏标记
	 */
	private void getSaveCollection() {
		SharedPreferences sp = getSharedPreferences("SAVECOLLECTION",
				Context.MODE_PRIVATE);
		isCollection = sp.getBoolean("isCollection", false);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.hotel_iv_back:
			finish();
			break;
		case R.id.iv_hotel_collection:
			// 收藏
			if (isCollection) {
				// 提示是否取消收藏
				cancelCollection();
			} else {
				isCollection = true;
				setSaveCollection();
				// 如果已经收藏，则显示收藏后的效果
				iv_spot_collection
						.setImageResource(R.drawable.second_2_collection);
				Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.hotel_buy_now:
			// 立即购买
			Intent intent = new Intent(Hotel_Activity.this, BuyNowHotel.class);
			startActivity(intent);
			isClickBuy = true;
			// setBackgroundBlack(all_choice_layout, 0);
			// popWindow.showAsDropDown(v);
			break;
		}
	}

	/**
	 * 保存是否添加收藏
	 */
	private void setSaveCollection() {
		SharedPreferences sp = getSharedPreferences("SAVECOLLECTION",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("isCollection", isCollection);
		editor.commit();
	}

	/**
	 * 取消收藏
	 */
	private void cancelCollection() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("是否取消收藏");
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				isCollection = false;
				// 如果取消收藏，则显示取消收藏后的效果
				iv_spot_collection.setImageResource(R.drawable.second_2);
				setSaveCollection();
			}
		});
		dialog.setNegativeButton("取消", null);
		dialog.create().show();
	}

	// 点击弹窗的确认按钮的响应
	@Override
	public void onClickOKPop() {
		setBackgroundBlack(all_choice_layout, 1);
		if (isClickBuy) {
			// 如果之前是点击的立即购买，那么就跳转到立即购物界面
			Intent intent = new Intent(Hotel_Activity.this, BuyNowHotel.class);
			startActivity(intent);
		} else {
			Toast.makeText(this, "添加到购物车成功", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 控制背景变暗0变暗1变亮
	 * 
	 * @param view
	 * @param what
	 */
	public void setBackgroundBlack(View view, int what) {
		switch (what) {
		case 0:
			view.setVisibility(View.VISIBLE);
			break;
		case 1:
			view.setVisibility(View.GONE);
			break;
		}
	}

}
