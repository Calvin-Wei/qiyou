/**
 * 
 */
package com.qiyou.adapter;

import com.qiyou.R;
import com.qiyou.adapter.Adapter_Ticket.HoldView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @QiYou
 * @2016-2-16
 * @author 谞臣
 * 
 */
public class Ticket_Adapter extends BaseAdapter {
	private Context context;

	public Ticket_Adapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return 10;
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
					R.layout.ticket_item, null);
		}

		return convertView;
	}

}
