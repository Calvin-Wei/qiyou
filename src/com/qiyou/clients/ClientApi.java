/**
 * 
 */
package com.qiyou.clients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qiyou.bean.HuoDongData;
import com.qiyou.bean.JingXuanData;
import com.qiyou.bean.JingxuanDetailData;
import com.qiyou.bean.UserInfo;
import com.qiyou.bean.ZhuanTiData;

/**
 * @齐游
 * @2016-2-6
 * @author 谞臣
 * @category 访问网络
 */
public class ClientApi {
	private static String startId;

	public ClientApi() {
		// TODO Auto-generated constructor stub
	}

	public static JSONObject ParseJson(final String path, final String encode) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpParams httpParams = httpClient.getParams();
		HttpPost httpPost = new HttpPost(path);
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(httpResponse.getEntity(),
						encode);
				JSONObject jsonObject = new JSONObject(result);
				return jsonObject;
			}

		} catch (UnsupportedEncodingException e) {
			return null;

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (httpClient != null)
				httpClient.getConnectionManager().shutdown();
		}
		return null;

	}

	/**
	 * @param Url
	 *            下载的Url
	 * @return
	 */
	public static ArrayList<JingXuanData> getJingXuanData(String Url) {
		ArrayList<JingXuanData> list = new ArrayList<JingXuanData>();
		JSONObject json = ParseJson(Url, "utf-8");

		if (json == null) {
			return null;
		} else {
			try {

				JSONArray Data = json.getJSONObject("obj").getJSONArray("list");

				for (int i = 0; i < Data.length(); i++) {
					System.out.println("------->" + i);
					JSONObject data = Data.getJSONObject(i);
					JingXuanData jingXuanData = new JingXuanData();
					jingXuanData.setId(data.optString("id"));
					JSONObject element = data.getJSONObject("tour");
					jingXuanData.setTitle(element.optString("title"));
					jingXuanData.setPubdate(element.optString("startdate"));
					jingXuanData.setPictureCount(element.optString("cntP"));
					jingXuanData.setImage(element.optString("coverpic"));
					jingXuanData.setViewCount(element.optString("pcolor"));
					jingXuanData.setFavoriteCount(element.getString("likeCnt"));
					jingXuanData.setViewCount(element.optString("viewCnt"));
					jingXuanData.setForeword(element.optString("foreword"));
					UserInfo userInfo = new UserInfo();
					JSONObject owner = element.optJSONObject("owner");
					userInfo.setUsername(owner.optString("username"));
					userInfo.setNickname(owner.optString("nickname"));
					userInfo.setUserId(owner.optString("userid"));
					userInfo.setAvatar(owner.getString("avatar"));
					jingXuanData.setUserInfo(userInfo);
					JSONArray dispcitys = element.getJSONArray("dispCities");
					String[] citys = new String[dispcitys.length()];
					for (int j = 0; j < dispcitys.length(); j++) {

						citys[j] = dispcitys.optString(j);
					}
					jingXuanData.setDispCities(citys);
					jingXuanData.setCmtCount(element.getString("cntcmt"));
					jingXuanData.setTourId(element.optString("id"));
					list.add(jingXuanData);
					if (i == Data.length() - 1) {
						startId = jingXuanData.getId();
					}

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			System.out.println("------->" + list.size());
			return list;

		}

	}

	public static String getStartId() {

		return startId;
	}

	/**
	 * 解析实体的Json数据
	 * 
	 * @param tourId
	 * @return
	 */
	public static ArrayList<JingxuanDetailData> getJingxuanDetailDatas(
			String tourId) {
		ArrayList<JingxuanDetailData> list = new ArrayList<JingxuanDetailData>();
		String jingXuanDetailUrl = "http://app.117go.com/demo27/php/formAction.php?submit=getATour2&tourid="
				+ tourId
				+ "&recType=1&refer=PlazaHome&ID2=1&token=3a79c4024f682aee74723a419f6605f9&v=a5.0.4&vc=anzhi&vd=f2e4ee47505f6fba";
		System.out.println("------>" + jingXuanDetailUrl);
		JSONObject json = ParseJson(jingXuanDetailUrl, "utf-8");
		if (json == null) {
			return null;
		} else {

			try {
				JSONArray Data = json.getJSONObject("obj").getJSONArray(
						"records");
				for (int i = 0; i < Data.length(); i++) {
					JSONObject data = Data.getJSONObject(i);
					JingxuanDetailData jingxuanDetailData = new JingxuanDetailData();
					jingxuanDetailData.setText(data.getString("words"));
					jingxuanDetailData
							.setImage("http://img.117go.com/timg/p308/"
									+ data.getString("picfile"));
					list.add(jingxuanDetailData);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}

	}

	/**
	 * 解析实体的Json数据
	 * 
	 * @param tourId
	 * @return
	 */
	public static ArrayList<ArrayList<ZhuanTiData>> getzhuantiDatas() {
		ArrayList<ArrayList<ZhuanTiData>> list_all = new ArrayList<ArrayList<ZhuanTiData>>();
		String jingXuanDetailUrl = "http://app.117go.com/demo27/php/gloryAction.php?submit=getGlory&vc=wandoujia&vd=80f117eb4244b778&v=a5.0.6";
		JSONObject json = ParseJson(jingXuanDetailUrl, "utf-8");
		if (json == null) {
			return null;
		} else {

			try {
				JSONArray Data = json.getJSONObject("obj").getJSONArray(
						"trip_list");

				ArrayList<ZhuanTiData> list1 = new ArrayList<ZhuanTiData>();
				System.out.println(">>>>>>>>>>" + Data);
				for (int i = 0; i < Data.length(); i++) {
					ZhuanTiData zhuanTiData = new ZhuanTiData();
					JSONObject data = Data.getJSONObject(i);
					zhuanTiData.setId(data.getString("id"));
					zhuanTiData.setName(data.getString("name"));
					zhuanTiData.setImage("http://img.117go.com/timg/p616/"
							+ data.getString("coverpic"));
					list1.add(zhuanTiData);
					System.out.println(">>>>>>>>>>" + zhuanTiData.getImage());

				}

				JSONArray Data2 = json.getJSONObject("obj").getJSONArray(
						"pic_list");
				ArrayList<ZhuanTiData> list2 = new ArrayList<ZhuanTiData>();
				for (int i = 0; i < Data2.length(); i++) {
					ZhuanTiData zhuanTiData = new ZhuanTiData();
					JSONObject data = Data2.getJSONObject(i);
					zhuanTiData.setId(data.getString("id"));
					zhuanTiData.setName(data.getString("name"));
					zhuanTiData.setImage("http://img.117go.com/timg/p616/"
							+ data.getString("coverpic"));
					list2.add(zhuanTiData);
				}

				list_all.add(list1);
				list_all.add(list2);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list_all;
		}

	}

	public static ArrayList<JingXuanData> getTourZhuanti(String searchId) {
		ArrayList<JingXuanData> list = new ArrayList<JingXuanData>();
		String Url = "http://app.117go.com/demo27/php/searchAction.php?submit=getSearchTours&searchid="
				+ searchId
				+ "&startId=0&length=20&fetchNewer=1&vc=wandoujia&vd=80f117eb4244b778&v=a5.0.6";
		JSONObject json = ParseJson(Url, "utf-8");
		if (json == null) {
			return null;
		} else {
			try {

				JSONArray Data = json.getJSONObject("obj")
						.getJSONArray("items");

				for (int i = 0; i < Data.length(); i++) {
					System.out.println("------->" + i);
					JSONObject data = Data.getJSONObject(i);
					JingXuanData jingXuanData = new JingXuanData();
					jingXuanData.setId(data.optString("id"));
					JSONObject element = data.getJSONObject("tour");
					jingXuanData.setTitle(element.optString("title"));
					jingXuanData.setPubdate(element.optString("startdate"));
					jingXuanData.setPictureCount(element.optString("cntP"));
					jingXuanData.setImage(element.optString("coverpic"));
					jingXuanData.setViewCount(element.optString("pcolor"));
					jingXuanData.setFavoriteCount(element.getString("likeCnt"));
					jingXuanData.setViewCount(element.optString("viewCnt"));
					jingXuanData.setForeword(element.optString("foreword"));
					UserInfo userInfo = new UserInfo();
					JSONObject owner = element.optJSONObject("owner");
					userInfo.setUsername(owner.optString("username"));
					userInfo.setNickname(owner.optString("nickname"));
					userInfo.setUserId(owner.optString("userid"));
					userInfo.setAvatar(owner.getString("avatar"));
					jingXuanData.setUserInfo(userInfo);
					JSONArray dispcitys = element.getJSONArray("dispCities");
					String[] citys = new String[dispcitys.length()];
					for (int j = 0; j < dispcitys.length(); j++) {

						citys[j] = dispcitys.optString(j);
					}
					jingXuanData.setDispCities(citys);
					jingXuanData.setCmtCount(element.getString("cntcmt"));
					jingXuanData.setTourId(element.optString("id"));
					list.add(jingXuanData);
					if (i == Data.length() - 1) {
						startId = jingXuanData.getId();
					}

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			System.out.println("------->" + list.size());
			return list;

		}

	}

	public static ArrayList<HuoDongData> getHuoDongList() {
		String jingXuanDetailUrl = "http://app.117go.com/demo27/php/eventAction.php?submit=getEventList&startId=0&length=20&vc=wandoujia&vd=80f117eb4244b778&v=a5.0.6";
		ArrayList<HuoDongData> list = new ArrayList<HuoDongData>();
		JSONObject json = ParseJson(jingXuanDetailUrl, "utf-8");
		if (json == null) {
			return null;
		} else {

			try {
				JSONArray Data = json.getJSONArray("obj");

				for (int i = 0; i < Data.length(); i++) {
					HuoDongData huoDongData = new HuoDongData();
					JSONObject data = Data.getJSONObject(i);
					huoDongData.setId(data.getString("id"));
					huoDongData.setName(data.getString("name"));
					huoDongData.setImage("http://img.117go.com/timg/p616/"
							+ data.getString("coverpic"));
					huoDongData.setUrls(data.getString("url"));
					list.add(huoDongData);
				}

			} catch (Exception e) {
			}
		}
		return list;
	}
}
