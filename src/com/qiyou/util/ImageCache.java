/**
 * 
 */
package com.qiyou.util;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

/**
 * @齐游
 * @2016-2-4
 * @author 谞臣
 * @category 图片加载工具类
 */
public class ImageCache {
	// 上下文对象
	private static Context context;
	// 需要加载的图片
	private ImageView imageView;
	// 图片的URL
	private String path;
	// 内存缓存技术,字符串是图片的url,bitmap是图片的位图
	private static LruCache<String, Bitmap> lruCache;
	// 图片压缩的目标宽度
	private int width;
	// 图片压缩的目标高度
	private int height;
	// 使用硬盘进行缓存
	private DiskLruCache mDiskLruCache;
	private final Object mDiskCacheLock = new Object();
	private boolean mDiskCacheStarting = true;
	// 硬盘缓存10MB
	private static final int DISK_CACHE_SIZE = 1024 * 10224 * 10;
	// 希望存储的目标文件夹
	private static String FolderName;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (imageView.getTag() != null && imageView.getTag().equals(path)) {
				imageView.setImageBitmap((Bitmap) msg.obj);
			}
		};
	};

	public ImageCache(Context context, LruCache<String, Bitmap> lruCache,
			ImageView imageView, String path, String FolderName, int width,
			int height) {
		this.context = context;
		this.imageView = imageView;
		this.path = path;
		this.FolderName = FolderName;
		this.width = width;
		this.height = height;
		this.lruCache = lruCache;

		File cacheFile = getDiskCacheDir(context, ImageCache.FolderName);
		new InitDiskCacheTask().execute(cacheFile);
		loadBitmap(path, imageView);// 加载图片数据
	}

	/**
	 * MD5算法 信息摘要算法
	 * 
	 * @param url
	 * @return
	 */
	public String GetMD5(String url) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] data = digest.digest(url.getBytes());
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < data.length; i++) {
				int iv = (int) (data[i] & 0x0ff);
				if (iv <= 0x0f) {
					stringBuilder.append('0');
				}
				stringBuilder.append(Integer.toHexString(iv));
			}
			String fileName = stringBuilder.toString();
			return fileName;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @齐游
	 * @2016-2-4
	 * @author 谞臣
	 * @category 内部类(初始化硬盘异步缓存任务)
	 */
	class InitDiskCacheTask extends AsyncTask<File, Void, Void> {

		@Override
		protected Void doInBackground(File... params) {
			synchronized (mDiskCacheLock) {
				File cacheDir = params[0];
				mDiskLruCache = DiskLruCache.openCache(context, cacheDir,
						DISK_CACHE_SIZE);
				mDiskCacheStarting = false;
				// 激活所有的对象
				mDiskCacheLock.notifyAll();
			}
			return null;
		}

	}

	/**
	 * 获得磁盘缓存的目录
	 * 
	 * @param context
	 * @param uniqueName
	 * @return
	 */
	public static File getDiskCacheDir(Context context, String uniqueName) {
		final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState()) || !isExternalStorageRemovable() ? getExternalCacheDir(
				context).getPath()
				: context.getCacheDir().getPath();
		return new File(cachePath + File.separator + uniqueName);
	}

	public static File getExternalCacheDir(Context context) {
		return context.getExternalCacheDir();
	}

	/**
	 * 
	 * @return
	 */
	public static boolean isExternalStorageRemovable() {
		return Environment.MEDIA_REMOVED.equals(Environment
				.getExternalStorageState());
	}

	/**
	 * 缓存图片
	 * 
	 * @param key
	 *            图片的url
	 * @param imageView
	 *            指定图片
	 */
	public void loadBitmap(String key, ImageView imageView) {
		Bitmap bitmap = null;// 先从缓存中获取图片
		bitmap = getBitmapFromMemCache(key);
		if (bitmap == null) {
			// 从SD卡文件中获取文件
			bitmap = getBitmapFromDiskCache(key);
			if (bitmap == null) {
				// 启动网络下载资源
				downLoadImage(key);
			} else {
				imageView.setImageBitmap(bitmap);
			}
		} else {
			imageView.setImageBitmap(bitmap);
		}
	}

	/**
	 * 往缓存中存放图片
	 * 
	 * @param key
	 * @param bitmap
	 */
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			lruCache.put(key, bitmap);
		}
	}

	/**
	 * 图片存入缓存
	 * 
	 * @param key
	 * @param bitmap
	 */
	public void addBitmapToCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			Log.i("", "-----------------存入缓存----------------");
			lruCache.put(key, bitmap);
		}
		synchronized (mDiskCacheLock) {
			if (mDiskLruCache != null && mDiskLruCache.get(key) == null) {
				mDiskLruCache.put(key, bitmap);
				Log.i("", "---------------------存入SD卡----------------");
			}
		}
	}

	/**
	 * 从缓存当中提取图片
	 * 
	 * @param key
	 * @return
	 */
	public Bitmap getBitmapFromMemCache(String key) {
		// 先判断从缓存中获取图片内容,如果找不到,从SD卡中获取信息
		// 先建立一张图片的维护表,url 保存图片的路径
		return lruCache.get(key);
	}

	/**
	 * 从后台线程等待启动磁盘缓存
	 * 
	 * @param key
	 * @return
	 */
	public Bitmap getBitmapFromDiskCache(String key) {
		synchronized (mDiskCacheLock) {
			while (mDiskCacheStarting) {
				try {
					mDiskCacheLock.wait();
				} catch (InterruptedException e) {
				}
			}
			if (mDiskLruCache != null) {

				return mDiskLruCache.get(key);
			} else {

				Log.i("", "-------------------访问失效--------------->");
			}
		}
		return null;
	}

	/**
	 * 启动异步任务下载图片
	 * 
	 * @param context
	 * @return
	 */
	public static LruCache<String, Bitmap> GetLruCache(Context context) {
		final int max_size = ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		final int cacheSize = 1024 * 1024 * max_size / 8;
		LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
				cacheSize) {

			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			}
		};
		return lruCache;
	}

	public void downLoadImage(final String path) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpClient httpClient = new DefaultHttpClient();
				HttpParams httpParams = httpClient.getParams();
				HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
				HttpConnectionParams.setSoTimeout(httpParams, 3000);
				HttpGet httpget = new HttpGet(path);
				HttpResponse response = null;
				try {
					response = httpClient.execute(httpget);
					if (response.getStatusLine().getStatusCode() == 200) {
						byte[] data = EntityUtils.toByteArray(response
								.getEntity());
						Bitmap bitmap = BitMapUtils.decodeBitmap(data, width,
								height);
						// 如果下载成功 先放到缓存中
						if (bitmap != null) {
							addBitmapToCache(path, bitmap);
							Message msgMessage = Message.obtain();
							msgMessage.obj = bitmap;
							handler.sendMessage(msgMessage);
						}

					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {

					if (httpClient != null)
						httpClient.getConnectionManager().shutdown();
				}

			}
		}).start();
	}

}
