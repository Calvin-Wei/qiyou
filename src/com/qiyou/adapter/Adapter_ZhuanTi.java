/**
 * 
 */
package com.qiyou.adapter;

import java.util.List;
import java.util.Random;

import com.qiyou.R;
import com.qiyou.adapter.Adapter_Words.ViewHolder;
import com.qiyou.bean.ZhuanTiData;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @齐游
 * @2016-2-13
 * @author 谞臣
 * 
 */
public class Adapter_ZhuanTi extends BaseAdapter {

	private List<ZhuanTiData> list;
	private Context context;

	public Adapter_ZhuanTi(Context context, List<ZhuanTiData> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.zhuanti_list_item, null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.iv_news_pic);
			Random random = new Random();
			holder.image.setBackgroundResource(list.get(random.nextInt(8))
					.getId());
			holder.tvMessage = (TextView) convertView
					.findViewById(R.id.tv_news_title);
			holder.tvMessage.setText(list.get(random.nextInt(8)).getMessage());
			holder.tvTime = (TextView) convertView
					.findViewById(R.id.tv_news_time);
			Log.i("adapter", "3");
			convertView.setTag(holder);
		} else {
			Log.i("adapter", "4");
			holder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}

	class ViewHolder {
		ImageView image;
		TextView tvTime;
		TextView tvMessage;
	}

}
