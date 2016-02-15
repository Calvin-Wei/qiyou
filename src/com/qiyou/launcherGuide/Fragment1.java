/**
 * 
 */
package com.qiyou.launcherGuide;

import com.qiyou.R;

import android.os.Bundle;
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
public class Fragment1 extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_1, container, false);
		return view;
	}
}
