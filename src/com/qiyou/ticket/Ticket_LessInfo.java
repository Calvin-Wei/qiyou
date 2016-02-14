/**
 * 
 */
package com.qiyou.ticket;

import com.qiyou.R;
import com.qiyou.activity.HotelInfoActivity;
import com.qiyou.activity.HotelShowActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * @齐游
 * @2016-2-14
 * @author 谞臣
 * 
 */
public class Ticket_LessInfo extends Activity implements OnTouchListener {

	public static final String TAG = "Ticket_LessInfo";
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int EACH = 4;
	boolean isShow;

	RelativeLayout hotel_jianjie;
	WindowManager windowManager;
	WindowManager.LayoutParams lp;
	ScrollView sv;
	TextView text;
	ImageButton img;
	GestureDetector mGestureDetector;
	int viewH;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ticket_info);
		initResourceRefs();

		isShow = text.getVisibility() == View.VISIBLE;

		img.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isShow) {
					text.setVisibility(View.GONE);
					img.setImageResource(R.drawable.arrow_down);
					isShow = false;
				} else {
					text.setVisibility(View.VISIBLE);
					img.setImageResource(R.drawable.arrow_up);
					isShow = true;
				}

			}
		});

	}

	private void initResourceRefs() {
		hotel_jianjie = (RelativeLayout) findViewById(R.id.hotel_jianjie);
		img = (ImageButton) findViewById(R.id.bus_info_detail_but);
		text = (TextView) findViewById(R.id.bus_info_explor);
		LayoutInflater inflater = LayoutInflater.from(this);

		lp = new WindowManager.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, // û�б߽�
				PixelFormat.TRANSLUCENT);
		lp.gravity = Gravity.BOTTOM;

		lp.windowAnimations = R.style.bus_view;
		sv = (ScrollView) findViewById(R.id.scrollView);
		sv.setOnTouchListener(this);

		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}
}
