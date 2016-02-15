/**
 * 
 */
package com.qiyou.activity;

import com.qiyou.R;
import com.qiyou.ui.view.BuynowActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @齐游
 * @2016-2-15
 * @author 谞臣
 * 
 */
public class BuyNowTrain extends Activity {
	private TextView bt_ok, bt_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy_now_train);
		initView();

	}

	private void initView() {
		bt_ok = (TextView) findViewById(R.id.train_buy_ok);
		bt_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(BuyNowTrain.this, "支付成功", Toast.LENGTH_SHORT)
						.show();
				finish();
			}
		});

	}
}
