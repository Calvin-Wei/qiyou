/**
 * 
 */
package com.qiyou.ui.view;

import com.qiyou.R;
import com.zdp.aseo.content.AseoZdpAseo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @齐游
 * @2016-1-26
 * @author 谞臣
 * @category 确认订单界面
 */
public class BuynowActivity extends Activity {
	private TextView bt_ok,bt_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy_now_ticket);
		initView();
		
	}
	
	private void initView(){
		bt_back=(TextView) findViewById(R.id.bt_buy_back);
		bt_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
		bt_ok=(TextView) findViewById(R.id.tv_buy_ok);
		bt_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(BuynowActivity.this, "暂无法支付", Toast.LENGTH_SHORT).show();
			}
		});
		
		
	}
}
