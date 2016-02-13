/**
 * 
 */
package com.qiyou.adapter;

import java.util.ArrayList;
import java.util.List;

import com.qiyou.R;
import com.qiyou.bean.WuLiuData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @齐游
 * @2016-2-14
 * @author 谞臣
 * 
 */
public class Adapter_WuLiu extends BaseAdapter {

	private Context mcontext;
	private List<WuLiuData> mList = new ArrayList<WuLiuData>();
	private LayoutInflater minflater;

	public Adapter_WuLiu(Context context, List<WuLiuData> List) {
		this.mcontext = context;
		minflater = LayoutInflater.from(context);
		this.mList = List;
	}

	@Override
	public int getCount() {
		if (null != mList) {
			return mList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (null != mList) {
			return mList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHold viewHold;
		if (convertView == null) {
			viewHold = new ViewHold();
			convertView = minflater.inflate(R.layout.wuliu_item, null);
			viewHold.imageView = (ImageView) convertView
					.findViewById(R.id.circle);
			viewHold.tvAdress = (TextView) convertView
					.findViewById(R.id.tvAdress);
			viewHold.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			viewHold.line = (View) convertView.findViewById(R.id.line);
			convertView.setTag(viewHold);
		} else {
			viewHold = (ViewHold) convertView.getTag();
		}
		if (position == 0) {
			viewHold.line.setVisibility(View.VISIBLE);
			viewHold.imageView.setImageResource(R.drawable.item_show);
		}
		viewHold.tvAdress.setText(mList.get(position).getMaddress());
		viewHold.tvTime.setText(mList.get(position).getMtime());
		return convertView;
	}

	private final static class ViewHold {
		ImageView imageView;
		View line;
		TextView tvAdress;
		TextView tvTime;
	}
}
