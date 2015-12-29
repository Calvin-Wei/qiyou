/**
 * 
 */
package com.qiyou.launcherGuide;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @齐游
 * @2015-12-29
 * @author 谞臣
 * 引导页的适配器
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragmentList = new ArrayList<Fragment>();

	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public ViewPagerAdapter(FragmentManager fragmentManager,
			List<Fragment> arrayList) {
		super(fragmentManager);
		this.fragmentList = arrayList;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		return fragmentList.size();
	}

}
