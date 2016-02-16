/**
 * 
 */
package com.qiyou.ui.view;

import com.qiyou.R;
import com.qiyou.activity.WordsActivity;
import com.qiyou.activity.ZhuanTiActivity;
import com.qiyou.adapter.Adapter_GridView;
import com.qiyou.util.AppManager;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

/**
 * @齐游
 * @2015-12-30
 * @author 谞臣 主界面下面的4个选项卡
 */
@SuppressWarnings("deprecation")
public class HomeActivity extends TabActivity {
	public static final String TAG = HomeActivity.class.getSimpleName();

	// 下面的按钮组
	private RadioGroup mTabButtonGroup;
	private TabHost mTabHost;
	/**
	 * 
	 */
	public static final String TAB_MAIN = "MAIN_ACTIVITY";
	public static final String TAB_FEEL = "Words_ACTIVITY";
	public static final String TAB_PLAN = "PLAN_ACTIVITY";
	public static final String TAB_USER = "LoginACTIVITY";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(this);
		setContentView(R.layout.activity_home);
		findViewById();
		initView();
	}

	/**
	 * 加载页面
	 */
	private void initView() {

		mTabHost = getTabHost();
		Intent i_main = new Intent(this, MainActivity.class);
		Intent i_plan = new Intent(this, ZhuanTiActivity.class);
		Intent i_found = new Intent(this, WordsActivity.class);
		Intent i_personal = new Intent(this, LoginActivity.class);
		mTabHost.addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN)
				.setContent(i_main));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_FEEL).setIndicator(TAB_FEEL)
				.setContent(i_found));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_PLAN).setIndicator(TAB_PLAN)
				.setContent(i_plan));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_USER).setIndicator(TAB_USER)
				.setContent(i_personal));
		mTabButtonGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.home_tab_main:
							mTabHost.setCurrentTabByTag(TAB_MAIN);
							break;
						case R.id.home_tab_feel:
							mTabHost.setCurrentTabByTag(TAB_FEEL);
							break;
						case R.id.home_tab_plan:
							mTabHost.setCurrentTabByTag(TAB_PLAN);
							break;
						case R.id.home_tab_personal:
							mTabHost.setCurrentTabByTag(TAB_USER);
							break;
						default:
							break;
						}
					}
				});
	}

	/**
	 * 获得RadioGroup的ID
	 */
	private void findViewById() {
		mTabButtonGroup = (RadioGroup) findViewById(R.id.home_radio_button_group);
	}
}
