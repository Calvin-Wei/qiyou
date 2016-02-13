/**
 * 
 */
package com.qiyou.activity;

import java.util.HashMap;

import com.qiyou.R;
import com.zdp.aseo.content.AseoZdpAseo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @齐游
 * @2016-2-6
 * @author 谞臣
 * @category 第三方分享界面
 */
public class ShareActivity extends Activity implements OnClickListener {

	private ImageView Sharekaixin, Shareqq, Shareqzone, Sharerenren,
			Sharesinaweibo, Sharetencentweibo, Sharewechat, Sharewechatmoment;
	private String sharecontent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_share);
		Sharekaixin = (ImageView) this.findViewById(R.id.share_kaixin);
		Shareqq = (ImageView) this.findViewById(R.id.share_qq);
		Shareqzone = (ImageView) this.findViewById(R.id.share_qzone);
		Sharerenren = (ImageView) this.findViewById(R.id.share_renren);
		Sharesinaweibo = (ImageView) this.findViewById(R.id.share_sina);
		Sharetencentweibo = (ImageView) this.findViewById(R.id.share_tecent);
		Sharewechat = (ImageView) this.findViewById(R.id.share_wechat);
		Sharewechatmoment = (ImageView) this
				.findViewById(R.id.share_wechatmoment);
		Sharekaixin.setOnClickListener(this);
		Shareqq.setOnClickListener(this);
		Shareqzone.setOnClickListener(this);
		Sharerenren.setOnClickListener(this);
		Sharesinaweibo.setOnClickListener(this);
		Sharetencentweibo.setOnClickListener(this);
		Sharewechat.setOnClickListener(this);
		Sharewechatmoment.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.share_tecent:
			Toast.makeText(getApplicationContext(), "分享成功", 1).show();
			break;

		case R.id.share_sina:
			Toast.makeText(getApplicationContext(), "分享成功", 1).show();
			break;
		case R.id.share_renren:
			Toast.makeText(getApplicationContext(), "分享成功", 1).show();
			break;
		case R.id.share_kaixin:
			Toast.makeText(getApplicationContext(), "分享成功", 1).show();
			break;
		case R.id.share_qq:
			Toast.makeText(getApplicationContext(), "分享成功", 1).show();
			break;
		case R.id.share_qzone:
			Toast.makeText(getApplicationContext(), "分享成功", 1).show();
			break;
		case R.id.share_wechat:
			Toast.makeText(getApplicationContext(), "分享成功", 1).show();
			break;
		case R.id.share_wechatmoment:
			Toast.makeText(getApplicationContext(), "分享成功", 1).show();
			break;
		}
	}

}
