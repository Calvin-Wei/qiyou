/**
 * 
 */
package com.qiyou.activity;

import com.qiyou.R;
import com.qiyou.adapter.Adapter_Hotel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @齐游
 * @2016-2-14
 * @author 谞臣
 * @category 酒店
 */
public class HotelActivity extends Activity {

	public Adapter_Hotel nAdapter;

	public Button searchBut, mapBut;

	public ListView showList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotel);
		initResourceRefs();
		initSetting();
	}

	public void initResourceRefs() {

		showList = (ListView) findViewById(R.id.nearby_list_content);

		searchBut = (Button) findViewById(R.id.nearby_serach_but);
		mapBut = (Button) findViewById(R.id.nearby_map_but);

		nAdapter = new Adapter_Hotel(getApplicationContext());

	}

	public void initSetting() {

		showList.setAdapter(nAdapter);
		showList.setDivider(null);
		showList.setDividerHeight(20);

		showList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent picIntent = new Intent(HotelActivity.this,
						HotelInfoActivity.class);
				HotelActivity.this.startActivity(picIntent);
			}
		});

	}
}
