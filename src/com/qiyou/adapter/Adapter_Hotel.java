/**
 * 
 */
package com.qiyou.adapter;

import com.qiyou.R;
import com.qiyou.activity.HotelActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @齐游
 * @2016-2-14
 * @author 谞臣
 * 
 */
public class Adapter_Hotel extends BaseAdapter {
	HoldView hold = new HoldView();
	private Context context;

	public Adapter_Hotel(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return 30;
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
					R.layout.hotel_list_item, null);
			init(convertView);
		}

		return convertView;
	}

	public void init(View convertView) {
		hold.name = (TextView) convertView.findViewById(R.id.nearby_item_name);
		hold.local = (TextView) convertView
				.findViewById(R.id.nearby_item_local);
		hold.dis1 = (TextView) convertView.findViewById(R.id.nearby_item_dis1);
		hold.dis2 = (TextView) convertView.findViewById(R.id.nearby_item_dis2);
		hold.dis3 = (TextView) convertView.findViewById(R.id.nearby_item_dis3);
	}

	class HoldView {
		TextView name, local, dis1, dis2, dis3;
	}
}
