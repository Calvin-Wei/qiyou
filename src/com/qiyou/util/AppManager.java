/**
 * 
 */
package com.qiyou.util;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * @齐游
 * @2015-12-29
 * @author 谞臣
 * Activity堆栈
 */
public class AppManager {
	private static Stack<Activity> mActivityStack;
	private static AppManager mAppManager;

	private AppManager() {
	}

	/**
	 * 单一实例
	 * 
	 * @return
	 */
	public static AppManager getInstance() {
		if (mAppManager == null) {
			mAppManager = new AppManager();
		}
		return mAppManager;
	}

	/**
	 * 添加新的Activity到堆栈
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		if (mActivityStack == null) {
			mActivityStack = new Stack<Activity>();
		}
		mActivityStack.add(activity);
	}

	/**
	 * 获取堆栈中的第一个元素(栈顶)最后加入的Activity
	 * 
	 * @return
	 */
	public Activity getTopActivity() {
		Activity activity = mActivityStack.lastElement();
		return activity;
	}

	/**
	 * 结束栈顶Activity
	 */
	public void killTopActivity() {
		Activity activity = mActivityStack.lastElement();
		killActivity(activity);
	}

	/**
	 * 结束掉指定的Activity
	 * 
	 * @param activity
	 */
	public void killActivity(Activity activity) {
		if (activity != null) {
			mActivityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束掉指定的类名的Activity
	 * 
	 * @param cls
	 */
	public void killActivity(Class<?> cls) {
		for (Activity activity : mActivityStack) {
			if (activity.getClass().equals(cls)) {
				killActivity(activity);
			}
		}
	}

	/**
	 * 结束掉所有的Activity
	 */
	public void killAllActivity() {
		for (int i = 0, size = mActivityStack.size(); i < size; i++) {
			if (null != mActivityStack.get(i)) {
				mActivityStack.get(i).finish();
			}
		}
		mActivityStack.clear();
	}

	public void AppExit(Context context) {
		try {
			killAllActivity();
			ActivityManager activityManager = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityManager.killBackgroundProcesses(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
