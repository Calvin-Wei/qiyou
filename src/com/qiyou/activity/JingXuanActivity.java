/**
 * 
 */
package com.qiyou.activity;

import java.util.ArrayList;
import java.util.List;

import com.qiyou.R;
import com.qiyou.MyView.MyGridView;
import com.qiyou.adapter.ImagePollAdapter;
import com.qiyou.bean.Poll;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @齐游
 * @2016-2-15
 * @author 谞臣
 * 
 */
public class JingXuanActivity extends Activity {
	private Button pollButton;
	private MyGridView mGridView = null;
	private ImagePollAdapter adapter;
	private List<Poll> listItems;
	// "图八", "图九" };
	private String[] optionName = { "图一", "图二", "图三", "图四", "图五", "图六", "图七",
			"图八", "图九", "图十", "图十一", "图十二", "图十三", "图十四", "图十五", "图十六" };
	private String[] currentNUm = { "333", "21", "90", "45", "67", "200", "10",
			"56", "45", "51", "23", "21", "12", "8", "9","10" };
	private int[] image = { R.drawable.icon_travel, R.drawable.icon_travel_1,
			R.drawable.icon_travel_2, R.drawable.icon_travel_3,
			R.drawable.icon_travel_4, R.drawable.icon_travel_5,
			R.drawable.icon_travel_6, R.drawable.icon_travel_7,
			R.drawable.icon_travel_8, R.drawable.icon_travel_9,
			R.drawable.icon_travel_10, R.drawable.icon_travel_11,
			R.drawable.icon_travel_12, R.drawable.icon_travel_13,
			R.drawable.icon_travel_14, R.drawable.icon_travel_15 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_image_poll);

		if (ImagePollAdapter.back == true) {
			Poll.ischecked = false;
		}

		initview();
		mGridView = (MyGridView) findViewById(R.id.my_gridView);
		listItems = getData();
		adapter = new ImagePollAdapter(this, listItems);
		mGridView.setAdapter(adapter);
	}

	private void initview() {
		// TODO Auto-generated method stub
		pollButton = (Button) findViewById(R.id.poll_btn);
		pollButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Poll.ischecked = true;
				pollButton
						.setBackgroundResource(R.drawable.button_style_checked);
				pollButton.setText("已投票");
				adapter.notifyDataSetChanged();
				pollButton.setFocusable(false);
			}
		});

		if (Poll.ischecked == true) {
			pollButton.setBackgroundResource(R.drawable.button_style_checked);
			pollButton.setText("已投票");
			adapter.notifyDataSetChanged();
			pollButton.setFocusable(false);
		}
	}

	private List<Poll> getData() {
		List<Poll> list = new ArrayList<Poll>();
		for (int i = 0; i < optionName.length; i++) {
			Poll poll = new Poll();
			poll.optionName = optionName[i];
			poll.image = image[i];
			poll.currentNUm = Integer.parseInt(currentNUm[i]);
			list.add(poll);
		}
		return list;

	}
}
