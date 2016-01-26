/**
 * 
 */
package com.qiyou.ui.view;

import com.qiyou.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @齐游
 * @2016-1-26
 * @author 谞臣
 * @category 显示大图的实现,并且可以放大缩小
 */
public class PictureFragment extends Fragment {
	private int resId;

	public PictureFragment(int resId) {
		this.resId = resId;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.scale_pic_item, null);
		initView(view);
		return view;
	}

	/**
	 * @param view
	 */
	private void initView(View view) {
		ImageView imageView = (ImageView) view
				.findViewById(R.id.scale_pic_item);
		imageView.setImageResource(resId);
	}
}
