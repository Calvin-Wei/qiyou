/**
 * 
 */
package com.qiyou.team;

import com.qiyou.R;
import com.qiyou.adapter.Adapter_TeamMates;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * @齐游
 * @2016-2-14
 * @author 谞臣
 * @category 查看队内成员
 */
public class Team_TeamMates extends Activity {
	private ListView list_myTeam;
	Adapter_TeamMates adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_team_manage);
		list_myTeam = (ListView) findViewById(R.id.list_myteam);
		adapter = new Adapter_TeamMates(getApplicationContext());
		list_myTeam.setAdapter(adapter);
	}
}
