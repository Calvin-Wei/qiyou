/**
 * 
 */
package com.qiyou.adapter;

import java.util.ArrayList;

import com.qiyou.R;
import com.qiyou.bean.ZhuanTiData;
import com.qiyou.util.ImageCache;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * @齐游
 * @2016-2-10
 * @author 谞臣
 * @category 各个景点的专题适配器
 */
public class ZhuantiAdapter extends BaseAdapter {

	private ArrayList<ZhuanTiData> data;
	private Context context;
	private LruCache<String, Bitmap> lruCache;

	public void BindData(ArrayList<ZhuanTiData> data) {
		this.data = data;
	}

	public ZhuantiAdapter(Context context) {
		this.context = context;
		lruCache = ImageCache.GetLruCache(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
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
					R.layout.zhuanti_item, null);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.zhuanti_main_image);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ZhuanTiData zhuanTiData = data.get(position);
		viewHolder.imageView.setImageResource(R.drawable.defaultcovers);
		viewHolder.imageView.setTag(zhuanTiData.getImage());
		new ImageCache(context, lruCache, viewHolder.imageView,
				zhuanTiData.getImage(), "齐游", 800, 400);
		return convertView;
	}

	private class ViewHolder {
		public ImageView imageView;
	}

}
