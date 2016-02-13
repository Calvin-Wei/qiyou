/**
 * 
 */
package com.qiyou.activity;

import java.util.ArrayList;
import java.util.List;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;

import com.qiyou.R;
import com.qiyou.adapter.Adapter_ZhuanTi;
import com.qiyou.bean.ZhuanTiData;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * @齐游
 * @2016-2-12
 * @author 谞臣
 * 
 */
public class ZhuanTiActivity extends Activity {

	private ListView list_zhuanti;
	private String time = "12:00";
	private int[] list_pic = new int[] { R.drawable.pic1, R.drawable.pic2,
			R.drawable.pic3, R.drawable.pic4, R.drawable.pic5, R.drawable.pic6,
			R.drawable.pic7, R.drawable.pic8 };
	private String[] newsTitle = new String[] { "印度Spiti Valley，神之领土",
			"北国之冬，Tomamu之雪", "加勒，损毁之城", "年少有你，青春有我 -- 清迈拜县六天四晚疯游",
			"来自月亮的星星，离开地球表面的奇幻", "一个北方人眼中的深圳，南方生活随记", "南游记：北回归线转个“湾”",
			"大理，这里日光倾城" };
	private List<ZhuanTiData> list = new ArrayList<ZhuanTiData>();
	private Adapter_ZhuanTi adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhuanti);
		list_zhuanti = (ListView) findViewById(R.id.zhuanti_list);
		ZhuanTiData data[] = new ZhuanTiData[8];
		for (int i = 0; i < list_pic.length; i++) {
			ZhuanTiData zhuanTiData = new ZhuanTiData();
			zhuanTiData.setId(list_pic[i]);
			zhuanTiData.setMessage(newsTitle[i]);
			zhuanTiData.setTime(time);
			data[i] = zhuanTiData;

		}
		for (int i = 0; i < data.length; i++) {
			list.add(data[i]);
		}
		adapter = new Adapter_ZhuanTi(getApplicationContext(), list);
		list_zhuanti.setAdapter(adapter);
		list_zhuanti.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(ZhuanTiActivity.this,
						ZhuanTiItemDetailActivity.class);
				startActivity(intent);
			}
		});
	}
}
