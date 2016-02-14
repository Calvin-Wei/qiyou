/**
 * 
 */
package com.qiyou.activity;

import com.qiyou.R;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @齐游
 * @2016-2-14
 * @author 谞臣
 * 
 */

public class HotelShowActivity extends Activity {

	boolean imageMeasured = false;
	TextView tv_right;
	TextView tv_bottom;

	static final String text = "速8酒店属于经济型酒店品牌。" + "英文名为 Super 8 。"
			+ " 速8国际是全球最大的经济型酒店连锁，从1974年发展到现在，全球共有2100多家速8酒店。"
			+ " 中国地区有680多家开业或即将开业的速8酒店。"
			+ " 经济型酒店有着巨大的市场潜力，具有低投入、高回报、周期短等突出的优点，其扩张速度惊人。"
			+ "同时，全球经济型酒店头把交椅的美国的“速8”进入国内。" + "从沿海到内地，市场份额逐渐扩大。";
	// 屏幕的高度
	int screenWidth = 0;
	// 总共可以放多少个字
	int count = 0;
	// textView全部字符的宽度
	float textTotalWidth = 0.0f;
	// textView一个字的宽度
	float textWidth = 0.0f;
	Paint paint = new Paint();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_info);

		tv_right = (TextView) findViewById(R.id.test_tv_right);
		tv_bottom = (TextView) findViewById(R.id.test_tv_bottom);
		final ImageView imageView = (ImageView) findViewById(R.id.test_image);

		screenWidth = getWindowManager().getDefaultDisplay().getWidth();

		/**
		 * 获取一个字的宽度
		 */
		textWidth = tv_right.getTextSize();
		paint.setTextSize(textWidth);

		/**
		 * 因为图片一开始的时候，高度是测量不出来的，通过增加一个监听器，即可获取其图片的高度和长度
		 */
		ViewTreeObserver vto = imageView.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {
				if (!imageMeasured) {
					imageMeasured = true;
					int height = imageView.getMeasuredHeight();
					int width = imageView.getMeasuredWidth();
					drawImageViewDone(width, height);
				}
				return imageMeasured;
			}
		});
	}

	private void drawImageViewDone(int width, int height) {
		// 一行字体的高度
		int lineHeight = tv_right.getLineHeight();
		// 可以放多少行
		int lineCount = (int) Math.ceil((double) height / (double) lineHeight);
		// 一行的宽度
		float rowWidth = screenWidth - width - tv_right.getPaddingLeft()
				- tv_right.getPaddingRight();
		// 一行可以放多少个字
		int columnCount = (int) (rowWidth / textWidth);

		// 总共字体数等于 行数*每行个数
		count = lineCount * columnCount;
		// 一个TextView中所有字符串的宽度和（字体数*每个字的宽度）
		textTotalWidth = (float) ((float) count * textWidth);

		measureText();
		tv_right.setText(text.substring(0, count));

		// 检查行数是否大于设定的行数，如果大于的话，就每次减少一个字符，重新计算行数与设定的一致
		while (tv_right.getLineCount() > lineCount) {
			count -= 1;
			tv_right.setText(text.substring(0, count));
		}
		tv_bottom.setPadding(0, lineCount * lineHeight - height, 0, 0);
		tv_bottom.setText(text.substring(count));
	}

	/**
	 * 测量已经填充的长度，计算其剩下的长度
	 */
	private void measureText() {
		String string = text.substring(0, count);
		float size = paint.measureText(string);
		int remainCount = (int) ((textTotalWidth - size) / textWidth);
		if (remainCount > 0) {
			count += remainCount;
			measureText();
		}
	}

}
