/**
 * 
 */
package com.qiyou.activity;

import java.util.ArrayList;
import java.util.List;

import com.qiyou.R;
import com.qiyou.adapter.Adapter_WuLiu;
import com.qiyou.bean.WuLiuData;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * @齐游
 * @2016-2-14
 * @author 谞臣
 * 
 */
public class WuliuActivity extends Activity {
	private ListView listView;
	private List<WuLiuData> list;
	private Adapter_WuLiu adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wuliu);
		addData();
		listView = (ListView) findViewById(R.id.list_wuliu);
		adapter = new Adapter_WuLiu(getApplicationContext(), list);
		listView.setAdapter(adapter);
	}

	private void addData() {
		list = new ArrayList<WuLiuData>();
		WuLiuData one = new WuLiuData("郑州市高新区区 已收件", "2016.2.02  10:46");
		list.add(one);
		WuLiuData two = new WuLiuData("快递已到达郑州物流中心", "2016.2.01  11:19");
		list.add(two);
		WuLiuData three = new WuLiuData("快递由北京物流中心发往郑州物流中心", "2016.1.30  05:19");
		list.add(three);
		WuLiuData four = new WuLiuData("快递正发往北京物流中心", "2016.1.29  12:14");
		list.add(four);
		WuLiuData five = new WuLiuData("快递员已揽件", "2016.1.27  1:19");
		list.add(five);
	}
}
