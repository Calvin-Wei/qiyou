/**
 * 
 */
package com.qiyou.fragment;

import java.util.ArrayList;

import com.qiyou.R;
import com.qiyou.activity.ZhuantiDetailImageActivity;
import com.qiyou.activity.ZhuantiDetailTourActivity;
import com.qiyou.adapter.ZhuantiAdapter;
import com.qiyou.bean.ZhuanTiData;
import com.qiyou.clients.ClientApi;
import com.qiyou.util.LoadingAnim;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @齐游
 * @2016-2-10
 * @author 谞臣
 * @category
 
 
  
 */
public class ZhuanTiFragment extends Fragment {
	private ListView image_listview;
	private ListView tour_listview;
	private TextView image_more;
	private ZhuantiAdapter tour_adapter;
	private ZhuantiAdapter iamge_adaAdapter;
	private RelativeLayout loadRelativeLayout;
	private ScrollView scrollView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.zhuanti_fragment, container,
				false);
		LoadingAnim.ininLoadingView(view);
		initViews(view);
		new Downdata().execute();
		image_more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});

		return view;
	}

	private void initViews(View view) {
		image_listview = (ListView) view
				.findViewById(R.id.image_zhuanti_listView);
		tour_listview = (ListView) view
				.findViewById(R.id.lvxing_zhuanti_listView);
		image_more = (TextView) view.findViewById(R.id.zhuanti_image_more);
		iamge_adaAdapter = new ZhuantiAdapter(getActivity());
		tour_adapter = new ZhuantiAdapter(getActivity());
		loadRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.loadingRelativieLayout);
		scrollView = (ScrollView) view.findViewById(R.id.data);

	}

	class Downdata extends
			AsyncTask<Void, Void, ArrayList<ArrayList<ZhuanTiData>>> {

		@Override
		protected ArrayList<ArrayList<ZhuanTiData>> doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			return ClientApi.getzhuantiDatas();
		}

		protected void onPostExecute(
				final ArrayList<ArrayList<ZhuanTiData>> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				iamge_adaAdapter.BindData(result.get(1));
				image_listview.setAdapter(iamge_adaAdapter);
				iamge_adaAdapter.notifyDataSetChanged();
				image_listview
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int position, long arg3) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(getActivity(),
										ZhuantiDetailImageActivity.class);
								intent.putExtra("SearchId",
										result.get(0).get(position).getId());
								intent.putExtra("name",
										result.get(0).get(position).getName());
								startActivity(intent);
							}
						});
				tour_adapter.BindData(result.get(0));
				tour_listview.setAdapter(tour_adapter);
				tour_listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						Intent intent = new Intent(getActivity(),
								ZhuantiDetailTourActivity.class);
						intent.putExtra("SearchId", result.get(0).get(position)
								.getId());
						intent.putExtra("name", result.get(0).get(position)
								.getName());
						startActivity(intent);
					}
				});
				tour_adapter.notifyDataSetChanged();
				loadRelativeLayout.setVisibility(View.GONE);
				scrollView.setVisibility(View.VISIBLE);
			} else {
				loadRelativeLayout.setVisibility(View.GONE);
				Toast.makeText(getActivity(), "网络异常", 0).show();
			}
		}

	}
}
