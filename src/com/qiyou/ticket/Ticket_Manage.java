/**
 * 
 */
package com.qiyou.ticket;

import com.qiyou.R;
import com.qiyou.adapter.Ticket_Adapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * @齐游
 * @2016-1-3
 * @author 谞臣
 * @category 已经购买的门票
 */
public class Ticket_Manage extends Activity {

	public Ticket_Adapter nAdapter;

	public ListView showList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_ticket);
		initResourceRefs();
		initSetting();
	}

	public void initResourceRefs() {

		showList = (ListView) findViewById(R.id.ticket_list_content);

		nAdapter = new Ticket_Adapter(getApplicationContext());

	}

	public void initSetting() {

		showList.setAdapter(nAdapter);
		showList.setDivider(null);
		showList.setDividerHeight(20);

	}
}
