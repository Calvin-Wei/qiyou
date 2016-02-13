/**
 * 
 */
package com.qiyou.activity;

import com.qiyou.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @齐游
 * @2016-2-13
 * @author 谞臣
 * @category 单项专题详情
 */
public class ZhuanTiItemDetailActivity extends Activity {
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhuanti_item);
		dialog = new ProgressDialog(this);
		dialog.setMessage("正在拼命加载。。。");
	}
}
