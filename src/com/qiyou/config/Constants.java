/**
 * 
 */
package com.qiyou.config;

import android.os.Environment;

/**
 * @齐游
 * @2015-12-29
 * @author 谞臣 参数信息设置
 */
public class Constants {
	/**
	 * 应用名称
	 */
	public static String APP_NAME = "";
	/**
	 * 保存参数文件夹名称
	 */
	public static final String SHARED_PREFERENCE_NAME = "qy_qiyou_prefs";
	/**
	 * SD卡路径
	 */
	public static final String SD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();
	/**
	 * 图片存储路径
	 */
	public static final String BASE_PATH = SD_PATH + "/image/qiyou/";
	/**
	 * 缓存图片路径
	 */
	public static final String BASE_IMAGE_CACHE = BASE_PATH + "cache/images/";
	/**
	 * 手机IMEI号码
	 */
	public static String IMEI = "";
	/**
	 * 手机号码
	 */
	public static String TEL = "";
	/**
	 * 分享成功
	 */
	public static final int SHARE_SUCCESS = 0X1000;
	/**
	 * 分享取消
	 */
	public static final int SHARE_CANCEL = 0X2000;
	/**
	 * 分享失败
	 */
	public static final int SHARE_ERROR = 0X3000;
	/**
	 * 开始执行
	 */
	public static final int EXECUTE_LOADING = 0X4000;
	/**
	 * 正在执行
	 */
	public static final int EXECUTE_SUCCESS = 0X5000;
	/**
	 * 执行完成
	 */
	public static final int EXECUTE_FAILED = 0X6000;
	/**
	 * 加载数据成功
	 */
	public static final int LOAD_DATA_SUCCESS = 0X7000;
	/**
	 * 加载数据失败
	 */
	public static final int LOAD_DATA_ERROR = 0X8000;
	/**
	 * 动态加载数据
	 */
	public static final int SET_DATA = 0X9000;
	/**
	 * 未登录
	 */
	public static final int NONE_LOGIN = 0x10000;
}
