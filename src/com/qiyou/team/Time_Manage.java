/**
 * 
 */
package com.qiyou.team;

import java.util.ArrayList;
import java.util.List;

import com.qiyou.R;
import com.qiyou.adapter.Adapter_Status;
import com.qiyou.bean.ChildStatusEntity;
import com.qiyou.bean.GroupStatusEntity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

/**
 * @齐游
 * @2016-1-3
 * @author 谞臣
 * 
 */
public class Time_Manage extends Activity {
	private ExpandableListView expandlistView;
	private Adapter_Status statusAdapter;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time);
		context = this;
		expandlistView = (ExpandableListView) findViewById(R.id.expandlist);
		initExpandListView();
	}

	/**
	 * 初始化可拓展列表
	 */
	private void initExpandListView() {
		statusAdapter = new Adapter_Status(context, getListData());
		expandlistView.setAdapter(statusAdapter);
		expandlistView.setGroupIndicator(null); // 去掉默认带的箭头
		expandlistView.setSelection(0);// 设置默认选中项

		// 遍历所有group,将所有项设置成默认展开
		int groupCount = expandlistView.getCount();
		for (int i = 0; i < groupCount; i++) {
			expandlistView.expandGroup(i);
		}

		expandlistView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return true;
			}
		});
	}

	private List<GroupStatusEntity> getListData() {
		List<GroupStatusEntity> groupList;
		String[] strArray = new String[] { "2月1日", "2月2日", "2月3日", "2月4日",
				"2月5日", "2月6日", "2月7日" };
		String[][] childTimeArray = new String[][] {
				{ "出发第一天", "先去火车票", "到达目的地", "入住预约的酒店", "中午吃过饭，休息过后开始第一天的旅游",
						"旅游正式开始" },
				{ "第二天早上精神饱满", "去世纪欢乐园游乐" },
				{ "第三天早上早起,驾车到景区", "到了大理吃过早饭就直接（古城包车更多更方便）包车开始直奔洱海而去",
						"开始环洱海之行，晚上住在双廊，伴着洱海入眠，是一件非常惬意的事情" },
				{ "第四天早上早起,驾车到景区", "去迪士尼乐园游玩" },
				{ "第五天早上早起,驾车到购物街", "购物开始" },
				{ "第六天早上早起,驾车到景区", "时间应该是比较充裕了，主要就是去昆明最经典滇池和民族村",
						"另外，从海埂公园内部可以到达西山脚下，可以爬山", "西山上有个景区是：龙门" },
				{ "第七天早上早起,准备回家", "旅游结束" } };
		groupList = new ArrayList<GroupStatusEntity>();
		for (int i = 0; i < strArray.length; i++) {
			GroupStatusEntity groupStatusEntity = new GroupStatusEntity();
			groupStatusEntity.setGroupName(strArray[i]);

			List<ChildStatusEntity> childList = new ArrayList<ChildStatusEntity>();

			for (int j = 0; j < childTimeArray[i].length; j++) {
				ChildStatusEntity childStatusEntity = new ChildStatusEntity();
				childStatusEntity.setCompleteTime(childTimeArray[i][j]);
				childStatusEntity.setIsfinished(true);
				childList.add(childStatusEntity);
			}

			groupStatusEntity.setChildList(childList);
			groupList.add(groupStatusEntity);
		}
		return groupList;
	}
}
