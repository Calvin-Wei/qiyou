/**
 * 
 */
package com.qiyou.adapter;

import java.util.List;

import com.qiyou.R;
import com.qiyou.bean.Message;

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
 * @2016-2-12
 * @author 谞臣
 * @category 旅游心得适配器
 */
public class Adapter_Words extends BaseAdapter {
	private List<Message> list;
	private Context context;

	public Adapter_Words(Context context) {
		Log.i("123123123", "进入Adapter");
		this.context = context;
	}

	public void BindData(List<Message> list) {
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
		Log.i("adapter", "1");
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_adapter, null);
			holder = new ViewHolder();
			holder.tvMessage = (TextView) convertView
					.findViewById(R.id.message);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			holder.ivAnswer = (ImageView) convertView
					.findViewById(R.id.iv_answer);
			Log.i("adapter", "2");
			holder.ivAnswer.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
				}
			});
			Log.i("adapter", "3");
			convertView.setTag(holder);
		} else {
			Log.i("adapter", "4");
			holder = (ViewHolder) convertView.getTag();
		}
		Log.i("adapter", "5");
		holder.tvMessage.setText(list.get(position).getMessage());
		holder.tvTime.setText(list.get(position).getTime());
		return convertView;
	}

	class ViewHolder {
		TextView tvMessage;
		TextView tvTime;
		ImageView ivAnswer;
	}
}
