/**
 * 
 */
package com.qiyou.http;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * @齐游
 * @2016-1-20
 * @author 谞臣
 * @category 通用JSON解析类
 * @category 限制最深解析层数3层
 * @category 谨慎传入解析参数
 */
public class CU_JSONResolve {
	/**
	 * 提取JSONObject的key的String类型的value
	 * 
	 * @param jO
	 * @param key
	 * @return String_value
	 */
	private static String getSTR(JSONObject jO, String key) {

		String value = "";

		if (key.equals(""))
			return value;

		try {
			if (jO.has(key))
				value = jO.getString(key);
		} catch (JSONException e) {
			Log.v("gyygyygyy------>" + key,
					"gyygyygyy------>CU_JSONResolveException" + e.toString());
		}

		return value.equals("null") ? "" : value;
	}

	/**
	 * 内部解析1层JSON对象
	 * 
	 * @param jO
	 * @param STR_field
	 * @return hashMap
	 */
	private static HashMap<String, Object> getHashMap1(JSONObject jO,
			String STR_field[]) {

		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		if (STR_field == null)
			return hashMap;

		if (STR_field != null && STR_field.length != 0)
			for (int i = 0; i < STR_field.length; i++)
				hashMap.put(STR_field[i], getSTR(jO, STR_field[i]));

		return hashMap;
	}

	/**
	 * 内部解析2层JSON对象
	 * 
	 * @param jO
	 * @param STR1_field
	 * @param LIST1_field
	 * @param aL_STR2_field
	 * @return
	 */
	private static HashMap<String, Object> getHashMap2(JSONObject jO,
			String STR1_field[], String LIST1_field[],
			ArrayList<String[]> aL_STR2_field) {

		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		if (STR1_field == null && LIST1_field == null)
			return hashMap;

		if (STR1_field != null && STR1_field.length != 0)
			for (int i = 0; i < STR1_field.length; i++)
				hashMap.put(STR1_field[i], getSTR(jO, STR1_field[i]));

		if (LIST1_field != null && LIST1_field.length != 0)
			if (aL_STR2_field != null
					&& aL_STR2_field.size() == LIST1_field.length)
				for (int i = 0; i < LIST1_field.length; i++)
					if (aL_STR2_field.get(i) != null
							&& aL_STR2_field.get(i).length != 0)
						hashMap.put(
								LIST1_field[i],
								getLIST1(jO, LIST1_field[i],
										aL_STR2_field.get(i)));
		return hashMap;
	}

	/**
	 * 提取JSONObject的key的1层LIST类型的value
	 * 
	 * @param jO
	 * @param key
	 * @param STR_field
	 * @return List_value
	 */
	private static ArrayList<HashMap<String, Object>> getLIST1(JSONObject jO,
			String key, String STR_field[]) {

		ArrayList<HashMap<String, Object>> value = new ArrayList<HashMap<String, Object>>();

		if (key.equals(""))
			return value;

		try {
			if (jO.has(key)) {
				JSONArray jA = jO.getJSONArray(key);
				if (jA != null && jA.length() != 0)
					for (int i = 0; i < jA.length(); i++) {
						JSONObject jO_item = jA.getJSONObject(i);
						value.add(getHashMap1(jO_item, STR_field));
					}
			}
		} catch (JSONException e) {
			Log.v("gyygyygyy------>" + key,
					"gyygyygyy------>CU_JSONResolveException" + e.toString());
			try {
				JSONObject jsonobject = jO.getJSONObject(key);
				value.add(getHashMap1(jsonobject, STR_field));
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return value;
	}

	/**
	 * 提取JSONObject的key的2层LIST类型的value
	 * 
	 * @param jO
	 * @param key
	 * @param STR1_field
	 * @param aL_LIST1_field
	 * @param aLL_STR2_field
	 * @return List_value
	 */
	private static ArrayList<HashMap<String, Object>> getLIST2(JSONObject jO,
			String key, String STR1_field[],
			ArrayList<String[]> aL_LIST1_field,
			ArrayList<ArrayList<String[]>> aLL_STR2_field) {

		ArrayList<HashMap<String, Object>> value = new ArrayList<HashMap<String, Object>>();

		if (key.equals(""))
			return value;

		try {
			if (jO.has(key)) {
				JSONArray jA = jO.getJSONArray(key);
				if (jA != null && jA.length() != 0)
					for (int i = 0; i < jA.length(); i++) {
						JSONObject jO_item = jA.getJSONObject(i);
						value.add(getHashMap2(jO_item, STR1_field,
								aL_LIST1_field.get(0), aLL_STR2_field.get(0)));
					}
			}
		} catch (JSONException e) {
			Log.v("gyygyygyy------>" + key,
					"gyygyygyy------>CU_JSONResolveException" + e.toString());
		}

		return value;
	}

	/**
	 * 解析完整的1层JSON对象
	 * 
	 * @param jsonStr
	 *            未解析的JSON字符串
	 * @param STR1_field
	 *            第一层String类型的key数组
	 * @return HashMap
	 * 
	 * @param → String STR1_field[] =
	 *        {"field0","field1","field2","field3","field4"};
	 *
	 */

	public static HashMap<String, Object> parseHashMap1(String jsonStr,
			String STR1_field[]) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		try {
			if (jsonStr == null)
				return hashMap;

			if (STR1_field == null)
				return hashMap;

			JSONObject jO0 = new JSONObject(jsonStr);
			if (jO0.has("success"))
				if (!jO0.getBoolean("success"))
					return hashMap;

			if (STR1_field != null && STR1_field.length != 0)
				for (int i = 0; i < STR1_field.length; i++)
					hashMap.put(STR1_field[i], getSTR(jO0, STR1_field[i]));

		} catch (JSONException e) {
			Log.v("gyygyygyygyy------>",
					"gyygyygyygyy3------>CU_JSONResolveException"
							+ e.toString());
		}
		return hashMap;
	}

	/**
	 * 解析完整的2层JSON对象：LIST1_field的length必须和aL_STR2_field的size相等
	 * 
	 * @param jsonStr
	 *            未解析的JSON字符串
	 * @param STR1_field
	 *            第一层String类型的key数组
	 * @param LIST1_field
	 *            第一层List类型的key数组
	 * @param aL_STR2_field
	 *            第二层String类型的key列表(列表项为String[]:一层LIST1_field对应的String类型的key数组)
	 * @param //一层5个String2个list,2层分别为3个String和4个String(传参务必相互对应)
	 * @return HashMap
	 */
	public static HashMap<String, Object> parseHashMap2(String jsonStr,
			String STR1_field[], String LIST1_field[],
			ArrayList<String[]> aL_STR2_field) {

		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		try {
			if (jsonStr == null)
				return hashMap;

			if (STR1_field == null && LIST1_field == null)
				return hashMap;

			JSONObject jO0 = new JSONObject(jsonStr);

			if (STR1_field != null && STR1_field.length != 0)
				for (int i = 0; i < STR1_field.length; i++)
					hashMap.put(STR1_field[i], getSTR(jO0, STR1_field[i]));

			if (LIST1_field != null && LIST1_field.length != 0)
				if (aL_STR2_field != null
						&& aL_STR2_field.size() == LIST1_field.length)
					for (int i = 0; i < LIST1_field.length; i++)
						if (aL_STR2_field.get(i) != null
								&& aL_STR2_field.get(i).length != 0)
							hashMap.put(
									LIST1_field[i],
									getLIST1(jO0, LIST1_field[i],
											aL_STR2_field.get(i)));

		} catch (JSONException e) {
			Log.v("gyygyygyygyy------>",
					"gyygyygyygyy4------>CU_JSONResolveException"
							+ e.toString());
		}
		return hashMap;
	}

	/**
	 * 解析完整的3层JSON对象：谨慎使用
	 * 
	 * @param jsonStr
	 *            未解析的JSON字符串
	 * @param STR1_field
	 *            第一层String类型的key数组
	 * @param LIST1_field
	 *            第一层List类型的key数组
	 * @param aL_STR2_field
	 *            第二层String类型的key列表(列表项为String[]:一层LIST1_field对应的String类型的key数组)
	 * @param aL_LIST2_field
	 *            第二层List类型key列表,同aL_STR2_field
	 * @param aL_STR3_field
	 *            第三层String类型的key列表套列表(ArrayList$ArrayList$String[]%%)//$和%换尖扩号
	 * @return HashMap
	 */
	public static HashMap<String, Object> parseHashMap3(String jsonStr,
			String STR1_field[], String LIST1_field[],
			ArrayList<String[]> aL_STR2_field,
			ArrayList<String[]> aL_LIST2_field,
			ArrayList<ArrayList<String[]>> aLL_STR3_field) {

		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		try {
			if (jsonStr == null)
				return hashMap;

			if (STR1_field == null && LIST1_field == null)
				return hashMap;

			JSONObject jO0 = new JSONObject(jsonStr);
			if (jO0.has("success"))
				if (!jO0.getBoolean("success"))
					return hashMap;

			if (STR1_field != null && STR1_field.length != 0)
				for (int i = 0; i < STR1_field.length; i++)
					hashMap.put(STR1_field[i], getSTR(jO0, STR1_field[i]));

			if (LIST1_field != null && LIST1_field.length != 0)
				if (aL_STR2_field != null
						&& aL_STR2_field.size() == LIST1_field.length)
					for (int i = 0; i < LIST1_field.length; i++)
						if (aL_STR2_field.get(i) != null
								&& aL_STR2_field.get(i).length != 0)
							hashMap.put(
									LIST1_field[i],
									getLIST2(jO0, LIST1_field[i],
											aL_STR2_field.get(i),
											aL_LIST2_field, aLL_STR3_field));

		} catch (JSONException e) {
			Log.v("gyygyygyygyy------>",
					"gyygyygyygyy5------>CU_JSONResolveException"
							+ e.toString());
		}
		return hashMap;
	}
}
