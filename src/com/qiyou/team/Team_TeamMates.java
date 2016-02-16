/**
 * 
 */
package com.qiyou.team;

import java.util.ArrayList;
import java.util.HashMap;

import com.qiyou.R;
import com.qiyou.adapter.Adapter_TeamMates;
import com.qiyou.xListView.ListViewSwipeGesture;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @齐游
 * @2016-2-14
 * @author 谞臣
 * @category 查看队内成员
 */
public class Team_TeamMates extends Activity {
	private ListView list_myTeam;
	Adapter_TeamMates adapter;

	private ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_team_manage);
		list_myTeam = (ListView) findViewById(R.id.list_myteam);

		for (int i = 0; i < 6; i++) {
			HashMap<String, Object> itemData = new HashMap<String, Object>();
			itemData.put("mem_name", "张三" + i);
			itemData.put("mem_message", "个人简介：良好的文艺青年");
			data.add(itemData);
		}
		adapter = new Adapter_TeamMates(getApplicationContext(), data);
		list_myTeam.setAdapter(adapter);

		final ListViewSwipeGesture touchListener = new ListViewSwipeGesture(
				list_myTeam, swipeListener, this);
		touchListener.SwipeType = ListViewSwipeGesture.Double; // 设置两个选项列表项的背景
		list_myTeam.setOnTouchListener(touchListener);
	}

	ListViewSwipeGesture.TouchCallbacks swipeListener = new ListViewSwipeGesture.TouchCallbacks() {

		@Override
		public void FullSwipeListView(int position) {
			// TODO Auto-generated method stub
			Toast.makeText(Team_TeamMates.this, "Action_2", Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		public void HalfSwipeListView(int position) {
			// TODO Auto-generated method stub
			// System.out.println("<<<<<<<" + position);
			data.remove(position);
			adapter.notifyDataSetChanged();
			Toast.makeText(Team_TeamMates.this, "删除", Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		public void LoadDataForScroll(int count) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onDismiss(ListView listView, int[] reverseSortedPositions) {
			// TODO Auto-generated method stub
			// Toast.makeText(activity,"Delete", Toast.LENGTH_SHORT).show();
			// for(int i:reverseSortedPositions){
			// data.remove(i);
			// new MyAdapter().notifyDataSetChanged();
			// }
		}

		@Override
		public void OnClickListView(int position) {
			// TODO Auto-generated method stub

		}

	};
}
