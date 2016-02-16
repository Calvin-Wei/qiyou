/**
 * 
 */
package com.qiyou.stepview;

import com.qiyou.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * @QiYou
 * @2016-2-16
 * @author 谞臣
 * @category 团队旅游进度表
 */
public class Team_Step_Activity extends Activity {

	private final String[] steps = { "故宫", "圆明园", "颐和园", "长城" };

	private final String[] steps2 = { "哈尔滨", "沈阳", "天津", "北京", "郑州" };

	private StepsView mStepsView;

	private ImageView back;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stepview);

		back = (ImageView) findViewById(R.id.team_iv_return);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mStepsView = (StepsView) findViewById(R.id.stepsView1);
		mStepsView.setLabels(steps)
				.setColorIndicator(getResources().getColor(R.color.blue))
				.setBarColor(Color.DKGRAY).setLabelColor(Color.GRAY)
				.setCompletedPosition(1);

		mStepsView = (StepsView) findViewById(R.id.stepsView3);
		mStepsView.setLabels(steps2)
				.setColorIndicator(getResources().getColor(R.color.orange))
				.setLabelColor(getResources().getColor(R.color.orange))
				.setCompletedPosition(3);

	}
}
