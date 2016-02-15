/**
 * 
 */
package com.qiyou.launcherGuide;

import com.qiyou.lanucherGuide.utils.AnimationUtil;
import com.qiyou.R;
import com.qiyou.ui.view.HomeActivity;
import com.qiyou.ui.view.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * @齐游
 * @2015-12-29
 * @author 谞臣
 * 
 */
public class Fragment4 extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_4, container, false);
		view.findViewById(R.id.tvInNew).setOnClickListener(
				new OnClickListener() {

					public void onClick(View v) {
						startActivity(new Intent(getActivity(),
								HomeActivity.class));
						AnimationUtil.finishActivityAnimation(getActivity());
					}
				});
		return view;
	}
}
