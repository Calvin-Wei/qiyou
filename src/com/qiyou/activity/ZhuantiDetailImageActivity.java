/**
 * 
 */
package com.qiyou.activity;

import java.util.ArrayList;

import com.etsy.android.grid.StaggeredGridView;
import com.qiyou.R;
import com.qiyou.adapter.ZhuantiImageDetailsAdapter;
import com.qiyou.bean.JingXuanData;
import com.qiyou.clients.ClientApi;
import com.qiyou.util.LoadingAnim;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @齐游
 * @2016-2-4
 * @author 谞臣
 * 
 */
public class ZhuantiDetailImageActivity extends Activity {
	private StaggeredGridView gridView;
	private ZhuantiImageDetailsAdapter adapter;
	private String searchId;
	private TextView titleTextView;
	private RelativeLayout loadRelativeLayout;
	private LinearLayout dataLinearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zhaunti_detail_image);
		LoadingAnim.ininLoading(ZhuantiDetailImageActivity.this);
		gridView = (StaggeredGridView) findViewById(R.id.gridview);
		adapter = new ZhuantiImageDetailsAdapter(getApplicationContext());
		searchId = getIntent().getStringExtra("SearchId");
		String title = getIntent().getStringExtra("name");
		titleTextView = (TextView) findViewById(R.id.zhuanti_main_title);
		titleTextView.setText(title);
		loadRelativeLayout = (RelativeLayout) findViewById(R.id.loadingRelativieLayout);
		dataLinearLayout = (LinearLayout) findViewById(R.id.dataLinearLayout);
		new DownLoad().execute();
	}

	class DownLoad extends AsyncTask<Void, Void, ArrayList<JingXuanData>> {

		@Override
		protected ArrayList<JingXuanData> doInBackground(Void... params) {
			return ClientApi.getTourZhuanti(searchId);
		}

		protected void onPostExecute(final ArrayList<JingXuanData> result) {
			super.onPostExecute(result);
			if (result != null) {
				adapter.BindData(result);
				gridView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				loadRelativeLayout.setVisibility(View.GONE);
				dataLinearLayout.setVisibility(View.VISIBLE);
				gridView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						Intent intent = new Intent(
								ZhuantiDetailImageActivity.this,
								JingXuanDetailsActivity.class);
						intent.putExtra("JingXuanData", result.get(position));
						intent.putExtra("id", result.get(position).getTourId());
						startActivity(intent);
					}
				});
			} else {
				loadRelativeLayout.setVisibility(View.GONE);
				Toast.makeText(getApplicationContext(), "网络异常，请检查", 0).show();
			}
		}

	}

}
