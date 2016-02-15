/**
 * 
 */
package com.qiyou.fragment;

import com.qiyou.R;
import com.qiyou.activity.Hotel_Activity;
import com.qiyou.adapter.Adapter_H;
import com.qiyou.adapter.Adapter_Spot;
import com.qiyou.ui.view.SpotActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @齐游
 * @2016-2-15
 * @author 谞臣
 * 
 */
public class HotelFragment extends Fragment {
	private ListView hotel_list;
	private Adapter_H adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gridview_page_hotel, container, false);
		hotel_list=(ListView) view.findViewById(R.id.hotel_list);
		adapter=new Adapter_H(getActivity());
		hotel_list.setAdapter(adapter);
		hotel_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent(getActivity(),Hotel_Activity.class);
				startActivity(intent);
			}
		});
		return view;

	}
}
