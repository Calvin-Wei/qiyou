/**
 * 
 */
package com.qiyou.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	ArrayList<HashMap<String, Object>> data;

	public Adapter_TeamMates(Context context) {
		this.context = context;
	}

	/**
	 * @param applicationContext
	 * @param data
	 */
	public Adapter_TeamMates(Context applicationContext,
			ArrayList<HashMap<String, Object>> data) {
		this.context = applicationContext;
		this.data = data;
	}

	@Override
	public int getCount() {

		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
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
					R.layout.manager_member_item_parent, null);
			holder = new ViewHolder();

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Map<String, Object> itemData = (Map<String, Object>) getItem(position);

		TextView ManName = (TextView) convertView.findViewById(R.id.mem_name);
		TextView LastTime = (TextView) convertView
				.findViewById(R.id.mem_message);
		ManName.setText(itemData.get("mem_name").toString());
		LastTime.setText(itemData.get("mem_message").toString());

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
