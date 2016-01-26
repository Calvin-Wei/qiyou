/**
 * 
 */
package com.qiyou.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.qiyou.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @齐游
 * @2016-1-26
 * @author 谞臣
 * 
 */
public class Adapter_ListView_detail extends BaseAdapter {

	private Context context;
	private ArrayList<HashMap<String, Object>> arrayList;

	@SuppressWarnings("unchecked")
	public Adapter_ListView_detail(Context context,
			HashMap<String, Object> hashmap) {
		this.context = context;
		this.arrayList = (ArrayList<HashMap<String, Object>>) hashmap
				.get("data");
	}

	public Adapter_ListView_detail(Context context) {
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
		HolderView holderView=null;
		if(convertView==null){
			holderView=new HolderView();
			convertView=LayoutInflater.from(context).inflate(R.layout.adapter_listview_detail, null);
			convertView.setTag(holderView);
		}
		else{
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
