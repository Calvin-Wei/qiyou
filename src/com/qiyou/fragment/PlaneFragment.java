/**
 * 
 */
package com.qiyou.fragment;


import com.qiyou.R;
import com.qiyou.activity.PlaneActivity;
import com.qiyou.adapter.Adapter_Plane;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @齐游
 * @2016-2-15
 * @author 谞臣
 * 
 */
public class PlaneFragment extends Fragment {

	private ListView plane_list;
	private Adapter_Plane adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gridview_page_plane, container, false);
		plane_list=(ListView) view.findViewById(R.id.plane_list);
		adapter=new Adapter_Plane(getActivity());
		plane_list.setAdapter(adapter);
		plane_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent(getActivity(),PlaneActivity.class);
				startActivity(intent);
			}
		});
		return view;

	}
}
