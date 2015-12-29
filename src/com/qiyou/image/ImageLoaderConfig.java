/**
 * 
 */
package com.qiyou.image;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.qiyou.main.R;

/**
 * @齐游
 * @2015-12-29
 * @author 谞臣 加载图片配置
 */
public class ImageLoaderConfig {
	/**
	 * 返回默认的参数配置
	 * 
	 * @param isShowDefault
	 *            true:显示的默认加载图片 false：不显示默认的加载图片
	 * @return
	 */
	public static DisplayImageOptions initDisplayOptions(boolean isShowDefault) {
		DisplayImageOptions.Builder displayImageOptionsBuilder = new DisplayImageOptions.Builder();
		// 设置图片缩放格式
		// Exactly:图像将完全按比例缩小的目标大小
		// Exactly_stretched:图片会缩放到目标大小
		// IN_SAMPLE_INT:图像将被二次采样的整数倍
		// IN_SAMPLE_POWER_OF_2:图片将降低2倍,直到下一减少步骤，使图像更小的目标大小
		// NONE:图片不进行调整
		displayImageOptionsBuilder.imageScaleType(ImageScaleType.EXACTLY);
		if (isShowDefault) {
			// 默认显示的图片
			displayImageOptionsBuilder.showStubImage(R.drawable.no_image);
			// 地址为空的默认显示图片
			displayImageOptionsBuilder
					.showImageForEmptyUri(R.drawable.no_image);
			// 加载失败是显示的图片
			displayImageOptionsBuilder.showImageOnFail(R.drawable.no_image);
		}
		// 开启内存缓存
		displayImageOptionsBuilder.cacheInMemory(true);
		// 开启SD卡进行缓存
		displayImageOptionsBuilder.cacheOnDisc(true);
		// 设置图片的格式为RGB_565(速度快)
		displayImageOptionsBuilder.bitmapConfig(Bitmap.Config.RGB_565);
		return displayImageOptionsBuilder.build();
	}

	public static DisplayImageOptions initDisplayOptions(int targetWidth,
			boolean isShowDefault) {
		DisplayImageOptions.Builder displayImageOptionsBuilder = new DisplayImageOptions.Builder();
		// 设置图片缩放格式(同上)
		displayImageOptionsBuilder.imageScaleType(ImageScaleType.EXACTLY);
		if (isShowDefault) {// 道理同上
			displayImageOptionsBuilder.showStubImage(R.drawable.no_image);
			displayImageOptionsBuilder
					.showImageForEmptyUri(R.drawable.no_image);
			displayImageOptionsBuilder.showImageOnFail(R.drawable.no_image);
		}
		displayImageOptionsBuilder.cacheInMemory(true);
		displayImageOptionsBuilder.cacheOnDisc(true);
		displayImageOptionsBuilder.bitmapConfig(Bitmap.Config.RGB_565);
		displayImageOptionsBuilder.displayer(new SimpleImageDisplayer(
				targetWidth));

		return displayImageOptionsBuilder.build();
	}

	/**
	 * 异步图片加载ImageLoader的初始化操作,在Application中调用此方法
	 * 
	 * @param context
	 *            上下文对象
	 * @param cacheDisc
	 *            图片缓存到SD卡的目录，需要传入SD卡根目录下的子目录,默认建立在SD卡的根目录下
	 */
	public static void initImageLoader(Context context, String cacheDisc) {
		// 配置ImageLoader
		// 获取本地缓存的目录，该目录在SD卡的根目录下
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, cacheDisc);
		// 实例化Builder
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				context);
		// 设置线程数量
		builder.threadPoolSize(3);
		// 设置线程的等级比普通低
		builder.threadPriority(Thread.NORM_PRIORITY);
		// 设定内存缓存为弱缓存
		builder.memoryCache(new WeakMemoryCache());
		// 设定内存图片缓存大小限制,不设置默认为屏幕的宽高
		builder.memoryCacheExtraOptions(480, 800);
		// 设定只保存同一尺寸的图片在内存
		builder.denyCacheImageMultipleSizesInMemory();
		// 设置缓存的SD卡目录,UnlimitDiscCache速度最快
		builder.discCache(new UnlimitedDiscCache(cacheDir));
		// 设置缓存到SD卡目录的文件命名
		builder.discCacheFileNameGenerator(new HashCodeFileNameGenerator());
		// 设置网络连接超市timeout:10s 读取网络连接超时read timeout:60s
		builder.imageDownloader(new BaseImageDownloader(context, 10000, 60000));
		// 设置ImageLoader的配置参数
		builder.defaultDisplayImageOptions(initDisplayOptions(true));

		// 初始化ImageLoader
		ImageLoader.getInstance().init(builder.build());
	}

}
