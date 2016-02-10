/**
 * 
 */
package com.qiyou.adapter;

import java.util.ArrayList;

import com.qiyou.R;
import com.qiyou.bean.JingXuanData;
import com.qiyou.bean.UserInfo;
import com.qiyou.circleimageview.CircleImageView;
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
 * @2016-2-7
 * @author 谞臣
 * @category 景点精选适配器
 */
public class JingXuanAdapter extends BaseAdapter {
	// 上下文对象
	private Context context;
	// 景点精选数据list存放
	private ArrayList<JingXuanData> datas;
	private LruCache<String, Bitmap> lruCache;
	private final static String IMAGEHOME = "";
	private final static String AVATAR = "";

	public JingXuanAdapter(Context context) {
		this.context = context;
		lruCache = ImageCache.GetLruCache(context);
	}

	public void BindData(ArrayList<JingXuanData> datas) {
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
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
					R.layout.jingxuan_item, null);
			viewHolder.disCitys = (TextView) convertView
					.findViewById(R.id.jingxuan_txt_address);
			viewHolder.favoriteCount = (TextView) convertView
					.findViewById(R.id.jingxuan_favoriteCount);
			viewHolder.image = (ImageView) convertView
					.findViewById(R.id.jingxuan_imageView);
			viewHolder.pictureCount = (TextView) convertView
					.findViewById(R.id.jingxuan_txt_picCount);
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.jingxuan_txt_picCount);
			viewHolder.viewCount = (TextView) convertView
					.findViewById(R.id.jingxuan_txt_eye);
			viewHolder.userCircleImageView = (CircleImageView) convertView
					.findViewById(R.id.jingxuan_user_circleImageView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		JingXuanData jingXuanData = datas.get(position);
		viewHolder.favoriteCount.setText(jingXuanData.getFavoriteCount());
		viewHolder.pictureCount.setText(jingXuanData.getPictureCount());
		viewHolder.title.setText(jingXuanData.getTitle());
		viewHolder.viewCount.setText(jingXuanData.getViewCount());
		StringBuffer stringBuffer = new StringBuffer();
		if (jingXuanData.getDispCities().length > 0) {
			for (int i = 0; i < jingXuanData.getDispCities().length; i++) {
				stringBuffer.append(jingXuanData.getDispCities()[i]).append(
						"->");
				if (i == jingXuanData.getDispCities().length) {
					stringBuffer.append(jingXuanData.getDispCities()[i]);

				}

			}
			viewHolder.disCitys.setText(jingXuanData.getDispCities()[0]); // stringBuffer.toString()
		}

		viewHolder.image.setTag(IMAGEHOME + jingXuanData.getImage());
		viewHolder.image.setImageResource(R.drawable.defaultcovers);
		new ImageCache(context, lruCache, viewHolder.image, IMAGEHOME
				+ jingXuanData.getImage(), "OnTheWay", 800, 400);
		UserInfo userInfo = jingXuanData.getUserInfo();
		viewHolder.userCircleImageView.setTag(AVATAR + userInfo.getAvatar());
		new ImageCache(context, lruCache, viewHolder.userCircleImageView,
				AVATAR + userInfo.getAvatar(), "OnTheWay", 800, 400);
		return convertView;
	}

	private class ViewHolder {
		private CircleImageView userCircleImageView;
		public TextView pictureCount;
		public TextView favoriteCount;
		public TextView disCitys;
		public TextView viewCount;
		public ImageView image;
		public TextView title;
	}

}
