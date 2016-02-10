/**
 * 
 */
package com.qiyou.activity;

import java.util.ArrayList;

import com.qiyou.R;
import com.qiyou.adapter.JingXuanDetailsAdapter;
import com.qiyou.bean.JingXuanData;
import com.qiyou.bean.JingxuanDetailData;
import com.qiyou.clients.ClientApi;
import com.qiyou.util.ImageCache;
import com.qiyou.util.LoadingAnim;
import com.zdp.aseo.content.AseoZdpAseo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @齐游
 * @2016-2-6
 * @author 谞臣
 * @category 精选的详细界面
 */
public class JingXuanDetailsActivity extends Activity {

	private ImageView mainImage;
	private TextView mainText;
	private ListView listView;
	private JingXuanDetailsAdapter adapter;
	private LruCache<String, Bitmap> lruCache;
	private RelativeLayout loadRelativeLayout;
	private LinearLayout dataLinearLayout;
	private Button viewCount, commentButton, shareButton;
	private String startId;
	private TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_jingxuan_details);
		initViews();
		new DownData().execute();
	}

	/**
	 * 
	 */
	private void initViews() {
		View headerView = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.jingxuan_details_header, null);
		mainImage = (ImageView) headerView
				.findViewById(R.id.jingxuan_detail_main_image);
		mainText = (TextView) headerView
				.findViewById(R.id.jingxuan_detail_main_txt);
		listView = (ListView) findViewById(R.id.jingxuan_detail_listview);
		commentButton = (Button) findViewById(R.id.CommentCount);
		shareButton = (Button) findViewById(R.id.share);
		viewCount = (Button) findViewById(R.id.likeCount);
		adapter = new JingXuanDetailsAdapter(getApplicationContext());
		dataLinearLayout = (LinearLayout) findViewById(R.id.list);
		LoadingAnim.ininLoading(JingXuanDetailsActivity.this);
		title = (TextView) findViewById(R.id.detail_main_title);
		loadRelativeLayout = (RelativeLayout) findViewById(R.id.loadingRelativieLayout);
		Intent intent = getIntent();
		final JingXuanData jingXuanData = (JingXuanData) intent
				.getSerializableExtra("JingXuanData");
		lruCache = ImageCache.GetLruCache(getApplicationContext());
		mainImage.setTag("http://img.117go.com/timg/p308/"
				+ jingXuanData.getImage());
		new ImageCache(getApplicationContext(), lruCache, mainImage,
				"http://img.117go.com/timg/p308/" + jingXuanData.getImage(),
				"OnTheway", 800, 400);
		AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
		mainText.setText(jingXuanData.getForeword());
		listView.addHeaderView(headerView);
		startId = intent.getStringExtra("id");
		title.setText(jingXuanData.getTitle());
		viewCount.setText(jingXuanData.getViewCount());
		commentButton.setText(jingXuanData.getCmtCount());
		shareButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// 跳转到第三方分享界面
				Intent intent = new Intent(JingXuanDetailsActivity.this,
						ShareActivity.class);
				intent.putExtra("shareContent", jingXuanData.getForeword());
				startActivity(intent);
			}
		});
	}

	class DownData extends AsyncTask<Void, Void, ArrayList<JingxuanDetailData>> {

		@Override
		protected ArrayList<JingxuanDetailData> doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			return ClientApi.getJingxuanDetailDatas(startId);
		}

		@Override
		protected void onPostExecute(ArrayList<JingxuanDetailData> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				loadRelativeLayout.setVisibility(View.GONE);
				Toast.makeText(JingXuanDetailsActivity.this, "网络异常,请检查！", 1)
						.show();
			} else {
				adapter.BindData(result);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				loadRelativeLayout.setVisibility(View.GONE);
				dataLinearLayout.setVisibility(View.VISIBLE);
			}

		}

	}
}
