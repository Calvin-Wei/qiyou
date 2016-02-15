/**
 * 
 */
package com.qiyou.adapter;

import java.util.List;

import com.qiyou.R;
import com.qiyou.adapter.Adapter_Words.ViewHolder;
import com.qiyou.bean.Message;
import com.qiyou.bean.TeamMates;
import com.qiyou.team.Team_TeamMates;

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
 * @2016-2-14
 * @author 谞臣
 * 
 */
public class Adapter_TeamMates extends BaseAdapter {
	private Context context;

	public Adapter_TeamMates(Context context) {
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
		Log.i("adapter", "1");
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.member_item, null);
			holder = new ViewHolder();
			holder.memMessage = (TextView) convertView
					.findViewById(R.id.mem_message);
			holder.memsex = (TextView) convertView.findViewById(R.id.mem_sex);
			holder.memLocate = (TextView) convertView
					.findViewById(R.id.mem_locate);
			holder.memName = (TextView) convertView.findViewById(R.id.mem_name);
			holder.ivAnswer = (ImageView) convertView
					.findViewById(R.id.iv_answer);
			convertView.setTag(holder);
		} else {
			Log.i("adapter", "4");
			holder = (ViewHolder) convertView.getTag();
		}
		Log.i("adapter", "5");
		return convertView;
	}

	class ViewHolder {
		TextView memLocate;
		TextView memMessage;
		TextView memsex;
		TextView memName;
		ImageView ivAnswer;
	}

}
