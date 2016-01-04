/**
 * 
 */
package com.qiyou.adapter;

import com.qiyou.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * @齐游
 * @2016-1-2
 * @author 谞臣 GridView的适配器
 */
public class Adapter_GridView extends BaseAdapter {

	private Context context;
	private int[] data;

	public Adapter_GridView(Context context, int[] data) {
		this.context = context;
		this.data = data;
	}

	public int getCount() {
		return data.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View currentView, ViewGroup parent) {
		HolderView holderView = null;
		if (currentView == null) {
			holderView = new HolderView();
			currentView = LayoutInflater.from(context).inflate(
					R.layout.adapter_gridview_home, null);
			holderView.iv_pic = (ImageView) currentView
					.findViewById(R.id.iv_adapter_grid_pic);
			currentView.setTag(holderView);
		} else {
			holderView = (HolderView) currentView.getTag();
		}
		holderView.iv_pic.setImageResource(data[position]);
		return currentView;
	}

	public class HolderView {
		private ImageView iv_pic;
	}

}
