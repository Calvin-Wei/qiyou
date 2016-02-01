/**
 * 
 */
package com.qiyou.ui.view;

import com.qiyou.R;
import com.qiyou.custom.ScaleView.HackyViewPager;
import com.zdp.aseo.content.AseoZdpAseo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * @齐游
 * @2016-1-26
 * @author 谞臣 大图界面
 */
public class ShowBigPictrue extends FragmentActivity {
	private HackyViewPager viewPager;
	private int[] resId = { R.drawable.detail_show_1, R.drawable.detail_show,
			R.drawable.detail_show_2 };
	// 得到上一个界面点击图片的位置
	private int position = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_big_picture);
		Intent intent = getIntent();
		position = intent.getIntExtra("position", 0);
//		AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
	}

	private void initViewPager() {
		viewPager = (HackyViewPager) findViewById(R.id.viewPager_show_big_Pic);
		ViewPagerAdapter adapter = new ViewPagerAdapter(
				getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		// 跳转到第几个页面
		viewPager.setCurrentItem(position);
	}

	private class ViewPagerAdapter extends FragmentStatePagerAdapter {
		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			int show_resId = resId[position];
			return new PictureFragment(show_resId);
		}

		public int getCount() {
			return resId.length;
		}
	}
}
