/**
 * 
 */
package com.qiyou.fragment;

import com.qiyou.R;
import com.qiyou.adapter.Adapter_Team;
import com.qiyou.team.TeamActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @齐游
 * @2016-2-15
 * @author 谞臣
 * 
 */
public class TeamFragment extends Fragment{
	private ListView train_list;

	private Adapter_Team adapter;

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container,  Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.gridview_page_team,
				container, false);
		train_list = (ListView) rootView.findViewById(R.id.team_list);
		adapter = new Adapter_Team(getActivity());
		train_list.setAdapter(adapter);
		train_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(), TeamActivity.class);
				startActivity(intent);
			}
		});
		return rootView;
	}
}
