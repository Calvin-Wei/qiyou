/**
 * 
 */
package com.qiyou.ui.view;

import com.qiyou.R;
import com.qiyou.adapter.Adapter_GridView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

/**
 * @齐游
 * @2015-12-31
 * @author 谞臣 个人用户Activity
 */
public class UserActivity extends Activity implements OnClickListener {
	// private GridView my_gridView_user;
	// private Adapter_GridView adapter_GridView;

	private LinearLayout ll_user_life;
	private LinearLayout ll_user_members;
	private LinearLayout ll_user_hotel;
	private LinearLayout ll_user_opinion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal);
		initView();
	}

	/**
	 * 
	 */
	private void initView() {
		ll_user_life = (LinearLayout) findViewById(R.id.ll_user_life);
		ll_user_members = (LinearLayout) findViewById(R.id.ll_user_members);
		ll_user_hotel = (LinearLayout) findViewById(R.id.ll_user_hotel);
		ll_user_opinion = (LinearLayout) findViewById(R.id.ll_user_opinion);
		ll_user_life.setOnClickListener(this);
		ll_user_members.setOnClickListener(this);
		ll_user_hotel.setOnClickListener(this);
		ll_user_opinion.setOnClickListener(this);
		// my_gridView_user=(GridView) view.findViewById(R.id.gridView_user);
	}

	public void onClick(View arg0) {
		// switch (arg0.getId()) {
		// case R.id.ll_user_life:
		// Intent intent1=new Intent(getActivity(),User_life.class);
		// startActivity(intent1);
		// break;
		// case R.id.ll_user_members:
		// Intent intent2=new Intent(getActivity(),User_life.class);
		// startActivity(intent2);
		// break;
		// case R.id.ll_user_hotel:
		// Intent intent3=new Intent(getActivity(),User_life.class);
		// startActivity(intent3);
		// break;
		// case R.id.ll_user_opinion:
		// Intent intent4=new Intent(getActivity(),User_opinion.class);
		// startActivity(intent4);
		// break;
		//
		// default:
		// break;
		// }

	}
}
