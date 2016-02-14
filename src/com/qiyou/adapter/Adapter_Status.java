/**
 * 
 */
package com.qiyou.adapter;

import java.util.List;

import com.qiyou.R;
import com.qiyou.bean.ChildStatusEntity;
import com.qiyou.bean.GroupStatusEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * @齐游
 * @2016-2-14
 * @author 谞臣
 * 
 */
public class Adapter_Status extends BaseExpandableListAdapter {

	private LayoutInflater inflater = null;
	private List<GroupStatusEntity> groupList;

	/**
	 * 构造方法
	 * 
	 * @param context
	 * @param oneList
	 */
	public Adapter_Status(Context context, List<GroupStatusEntity> group_list) {
		this.groupList = group_list;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getGroupCount() {
		return groupList.size();
	}

	/**
	 * 返回二级Item总数
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		if (groupList.get(groupPosition).getChildList() == null) {
			return 0;
		} else {
			return groupList.get(groupPosition).getChildList().size();
		}
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return groupList.get(groupPosition).getChildList().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupViewHolder holder = new GroupViewHolder();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.time_manager_item, null);
		}
		holder.groupName = (TextView) convertView
				.findViewById(R.id.one_status_name);

		holder.groupName.setText(groupList.get(groupPosition).getGroupName());

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildViewHolder viewHolder = null;
		ChildStatusEntity entity = (ChildStatusEntity) getChild(groupPosition,
				childPosition);
		if (convertView != null) {
			viewHolder = (ChildViewHolder) convertView.getTag();
		} else {
			viewHolder = new ChildViewHolder();
			convertView = inflater.inflate(R.layout.child_status_item, null);
			viewHolder.twoStatusTime = (TextView) convertView
					.findViewById(R.id.two_complete_time);
		}
		viewHolder.twoStatusTime.setText(entity.getCompleteTime());

		convertView.setTag(viewHolder);
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	private class GroupViewHolder {
		TextView groupName;
	}

	private class ChildViewHolder {
		public TextView twoStatusTime;
	}
}
