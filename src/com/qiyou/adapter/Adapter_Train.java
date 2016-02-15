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
import android.widget.TextView;

/**
 * @齐游
 * @2016-2-15
 * @author 谞臣
 * 
 */
public class Adapter_Train extends BaseAdapter {
	HolderView hold = new HolderView();
	private Context context;

	public Adapter_Train(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return 6;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.trans_item, null);
		}
		return convertView;
	}

	class HolderView {
		TextView tv_name, tv_message;
	}

}
