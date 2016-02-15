/**
 * 
 */
package com.qiyou.adapter;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

import com.qiyou.R;
import com.qiyou.bean.Poll;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @齐游
 * @2016-2-15
 * @author 谞臣
 * 
 */
public class ImagePollAdapter extends BaseAdapter {
	public static boolean back = false;
	private int lastPosition = -1;
	private List<Poll> listItems;
	Context context;
	private LayoutInflater inflater = null;
	HashMap<String, Boolean> states = new HashMap<String, Boolean>(); // 用于记录每个RadioButton的状态，并保证只可选一个

	public ImagePollAdapter(Context context, List<Poll> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint({ "InflateParams", "NewApi" })
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.image_poll_item, null);
			viewHolder.image = (ImageView) convertView
					.findViewById(R.id.poll_gridview);
			viewHolder.rl_progress = (RelativeLayout) convertView
					.findViewById(R.id.poll_progress);
			viewHolder.currentNum = (TextView) convertView
					.findViewById(R.id.currentNum);
			viewHolder.currentPrecent = (TextView) convertView
					.findViewById(R.id.currentPercent);
			viewHolder.progressBar = (ProgressBar) convertView
					.findViewById(R.id.progressBar1);
			viewHolder.tv_optionName = (TextView) convertView
					.findViewById(R.id.poll_optionName);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final ImageView imagePoll = (ImageView) convertView
				.findViewById(R.id.polled);
		viewHolder.imagePoll = imagePoll;

		final RadioButton radio = (RadioButton) convertView
				.findViewById(R.id.poll_gridview_item);
		viewHolder.optionName = radio;

		// 设置内容
		viewHolder.optionName.setText(listItems.get(position).optionName);
		viewHolder.image.setBackgroundResource(listItems.get(position).image);

		viewHolder.optionName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for (String key : states.keySet()) {
					states.put(key, false);
				}
				if (lastPosition == -1) { // 判断是否第一次进入
					listItems.get(position).currentNUm++;
					lastPosition = position;
				} else {
					if (lastPosition != position) {
						listItems.get(position).currentNUm++;
						listItems.get(lastPosition).currentNUm--;
						lastPosition = position;
					}
				}

				states.put(String.valueOf(position), radio.isChecked());
				ImagePollAdapter.this.notifyDataSetChanged();
			}
		});
		boolean res = false;
		if (states.get(String.valueOf(position)) == null
				|| states.get(String.valueOf(position)) == false) {
			res = false;
			states.put(String.valueOf(position), false);
		} else {
			res = true;
		}
		viewHolder.optionName.setChecked(res);

		if (Poll.ischecked == true) {
			back = true;
			viewHolder.rl_progress.setVisibility(convertView.VISIBLE);
			viewHolder.rl_progress.getBackground().setAlpha(200);
			// //单选按钮是否选中
			if (radio.isChecked()) {
				// 心形图片
				viewHolder.imagePoll.setBackground(convertView.getResources()
						.getDrawable(R.drawable.yueyuebbs_tp_gooded));
			}

			radio.setVisibility(convertView.GONE);
			viewHolder.tv_optionName.setVisibility(convertView.VISIBLE);
			viewHolder.tv_optionName.setText(listItems.get(position).optionName
					+ "");

			viewHolder.currentNum.setText((listItems.get(position).currentNUm)
					+ "");
			int sum = 0;
			int a = listItems.get(position).currentNUm;
			for (int i = 0; i < listItems.size(); i++) {
				sum = listItems.get(i).currentNUm + sum;
			}

			double precent = (double) ((a + 0.0) / sum);
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(2);

			viewHolder.progressBar.setMax(sum); // 设置最大进度值

			viewHolder.progressBar.setProgress(a);
			viewHolder.currentPrecent.setText("(" + nt.format(precent) + ")");// 百分比

			// Poll.ischecked = false;

		}

		return convertView;
	}

	private class ViewHolder {
		RadioButton optionName;
		ImageView image;
		TextView tv_optionName; // 点击投票后显示的选项名字
		TextView currentNum, currentPrecent; // 当前投票数和百分比
		ImageView imagePoll; // 投票后显示的"心形"logo
		RelativeLayout rl_progress; // 点击投票后显示的layout
		ProgressBar progressBar; // 点击投票后显示的进度条
	}
}
