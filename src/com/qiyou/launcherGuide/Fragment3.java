/**
 * 
 */
package com.qiyou.launcherGuide;

import com.qiyou.main.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @齐游
 * @2015-12-29
 * @author 谞臣
 * 
 */
public class Fragment3 extends Fragment {
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view=inflater.inflate(R.layout.fragment_3, container, false);
		return view;
	}
}
