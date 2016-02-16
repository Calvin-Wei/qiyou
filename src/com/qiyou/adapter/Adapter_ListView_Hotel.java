/**
 * 
 */
package com.qiyou.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.qiyou.R;
import com.qiyou.adapter.Adapter_ListView_Team.HolderView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @QiYou
 * @2016-2-16
 * @author 谞臣
 * 
 */
public class Adapter_ListView_Hotel extends BaseAdapter {
	private Context context;
	private ArrayList<HashMap<String, Object>> arrayList;

	@SuppressWarnings("unchecked")
	public Adapter_ListView_Hotel(Context context,
			HashMap<String, Object> hashmap) {
		this.context = context;
		this.arrayList = (ArrayList<HashMap<String, Object>>) hashmap
				.get("data");
	}

	public Adapter_ListView_Hotel(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HolderView holderView = null;
		if (convertView == null) {
			holderView = new HolderView();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.hotel_listview_detail, null);
			convertView.setTag(holderView);
		} else {
			holderView = (HolderView) convertView.getTag();
		}
		return convertView;
	}

	public class HolderView {
		private ImageView iv_pic;
		private TextView tv_sale_num;
		private TextView tv_name;
		private TextView tv_price;
	}
}
