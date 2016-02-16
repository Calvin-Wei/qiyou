/**
 * 
 */
package com.qiyou.team;

import java.util.ArrayList;

import com.qiyou.R;
import com.qiyou.adapter.Adapter_ListView_Team;
import com.qiyou.adapter.Adapter_ListView_detail;
import com.qiyou.custom.ScaleView.HackyViewPager;
import com.qiyou.stepview.Team_Step_Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @齐游
 * @2016-2-15
 * @author 谞臣
 * 
 */
public class TeamActivity extends Activity implements OnClickListener {

	// nfc适配器
	NfcAdapter nfcAdapter;

	private HackyViewPager viewPager;
	private ArrayList<View> allListView;
	private int[] resId = { R.drawable.ic_photo };
	private ImageView iv_spot_collection;
	private LinearLayout all_choice_layout = null;
	boolean isClickBuy = false;
	private static boolean isCollection = false;
	private int position = 0;

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team);
		// 得到保存的收藏信息
		getSaveCollection();
		initView();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		// 获取默认的NFC控制器
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (nfcAdapter == null) {
			Toast.makeText(this, "您的手机不支持NFC", Toast.LENGTH_SHORT).show();
		}

		listView = (ListView) findViewById(R.id.team_listView_Detail);
		listView.setFocusable(false);
		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		listView.setAdapter(new Adapter_ListView_Team(this));

		((ImageView) findViewById(R.id.team_iv_back)).setOnClickListener(this);
		iv_spot_collection = (ImageView) findViewById(R.id.team_collection);
		iv_spot_collection.setOnClickListener(this);

		Button join = (Button) findViewById(R.id.join_team);
		join.setOnClickListener(this);

		Button step = (Button) findViewById(R.id.team_step);
		step.setOnClickListener(this);

		all_choice_layout = (LinearLayout) findViewById(R.id.team_all_choice_layout);
		initViewPager();
		// 如果当前景点已经收藏，显示收藏后的效果
		if (isCollection) {
			iv_spot_collection.setImageResource(R.drawable.second_2_collection);
		}
	}

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
		viewPager = (HackyViewPager) findViewById(R.id.iv_team);
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
		case R.id.team_iv_back:
			finish();
			break;
		case R.id.team_collection:
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
		case R.id.join_team:
			Toast.makeText(getApplicationContext(), "参加团队成功", 1000).show();
			break;
		case R.id.team_step:
			Intent intent = new Intent(getApplicationContext(),
					Team_Step_Activity.class);
			startActivity(intent);
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
