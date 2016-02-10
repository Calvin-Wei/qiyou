/**
 * 
 */
package com.qiyou.adapter;

import java.util.ArrayList;

import com.qiyou.R;
import com.qiyou.bean.JingxuanDetailData;
import com.qiyou.util.ImageCache;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @齐游
 * @2016-2-6
 * @author 谞臣
 * @category 精选内容实体的适配器
 */
public class JingXuanDetailsAdapter extends BaseAdapter {

	private Context context;
	private LruCache<String, Bitmap> lruCache;
	private ArrayList<JingxuanDetailData> list;

	public JingXuanDetailsAdapter(Context context) {
		this.context = context;
		lruCache = ImageCache.GetLruCache(context);
	}

	public void BindData(ArrayList<JingxuanDetailData> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.jingxuan_detail_item, null);
			viewHolder.image = (ImageView) convertView
					.findViewById(R.id.jingxuan_detail_list_image);
			viewHolder.text = (TextView) convertView
					.findViewById(R.id.jingxuan_detail_content);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		JingxuanDetailData jingxuanDetailData = list.get(position);

		viewHolder.text.setText(jingxuanDetailData.getText());
		viewHolder.image.setTag(jingxuanDetailData.getImage());
		viewHolder.image.setImageResource(R.drawable.defaultcovers);
		new ImageCache(context, lruCache, viewHolder.image,
				jingxuanDetailData.getImage(), "齐游", 800, 1080);

		return convertView;
	}

	private class ViewHolder {
		public ImageView image;
		public TextView text;
		public TextView poi;
	}
}
