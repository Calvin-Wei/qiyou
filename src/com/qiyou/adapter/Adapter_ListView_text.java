/**
 * 
 */
package com.qiyou.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.qiyou.R;
import com.qiyou.http.CU_VolleyTool;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @齐游
 * @2016-1-20
 * @author 谞臣
 * 
 */
public class Adapter_ListView_text extends BaseAdapter {

	private Context context;
	private int[] data;
	private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();

	public Adapter_ListView_text(Context context,
			ArrayList<HashMap<String, Object>> arrayList) {

		this.context = context;
		this.arrayList = arrayList;
	}

	public Adapter_ListView_text(Context context) {
		this.context = context;

	}

	@Override
	public int getCount() {
		return (arrayList != null && arrayList.size() == 0) ? 30 : arrayList
				.size();
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
	public View getView(int position, View currentView, ViewGroup parent) {
		HolderView holderView = null;
		if (currentView == null) {
			holderView = new HolderView();
			currentView = LayoutInflater.from(context).inflate(
					R.layout.adapter_listview_text, null);
			holderView.iv_pic = (ImageView) currentView
					.findViewById(R.id.iv_adapter_list_pic);
			holderView.tv_name = (TextView) currentView.findViewById(R.id.name);
			holderView.tv_price = (TextView) currentView
					.findViewById(R.id.price);
			holderView.tv_sale_num = (TextView) currentView
					.findViewById(R.id.sale_num);
			currentView.setTag(holderView);
		} else {
			holderView = (HolderView) currentView.getTag();
		}
		if (arrayList.size() != 0) {

			ImageListener listener = ImageLoader.getImageListener(
					holderView.iv_pic, R.drawable.ic_launcher,
					R.drawable.ic_launcher);
			CU_VolleyTool
					.getInstance(context)
					.getmImageLoader()
					.get("http://192.168.0.111:3000/taoBao/img/"
							+ arrayList.get(position).get("pic"), listener);

			holderView.tv_name.setText(arrayList.get(position).get("name")
					.toString());
			holderView.tv_price.setText("￥"
					+ arrayList.get(position).get("price").toString() + "元");
			holderView.tv_sale_num.setText("月销量:"
					+ arrayList.get(position).get("sale_num").toString()
					+ "件     "
					+ arrayList.get(position).get("address").toString());
		}
		return currentView;
	}

	public class HolderView {

		private ImageView iv_pic;
		private TextView tv_sale_num;
		private TextView tv_name;
		private TextView tv_price;

	}

}
