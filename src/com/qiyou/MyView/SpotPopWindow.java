/**
 * 
 */
package com.qiyou.MyView;

import java.util.HashMap;

import com.qiyou.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

/**
 * 尚未完成保存数据(当前只有界面)
 * 
 * @齐游
 * @2016-1-22
 * @author 谞臣
 * @category 单个景点详情界面的弹窗
 */
public class SpotPopWindow implements OnDismissListener, OnClickListener {

	private TextView spot_name, pop_MeiLanFang_park, spot_time,
			spot_trans_info, pop_reduce, pop_num, pop_add, pop_ok;
	private ImageView pop_del;

	private PopupWindow popupWindow;
	private OnItemClickListener listener;
	private final int ADDORREDUCE = 1;
	private Context context;

	public SpotPopWindow(Context context) {
		this.context = context;
		View view = LayoutInflater.from(context).inflate(
				R.layout.adapter_popwindow, null);
		spot_name = (TextView) view.findViewById(R.id.spot_name);
		pop_MeiLanFang_park = (TextView) view
				.findViewById(R.id.pop_MeiLanFang_park);
		spot_time = (TextView) view.findViewById(R.id.spot_time);
		spot_trans_info = (TextView) view.findViewById(R.id.spot_trans_info);
		pop_add = (TextView) view.findViewById(R.id.pop_add);
		pop_reduce = (TextView) view.findViewById(R.id.pop_reduce);
		pop_num = (TextView) view.findViewById(R.id.pop_num);
		pop_ok = (TextView) view.findViewById(R.id.pop_ok);
		pop_del = (ImageView) view.findViewById(R.id.pop_del);

		spot_name.setOnClickListener(this);
		pop_MeiLanFang_park.setOnClickListener(this);
		spot_time.setOnClickListener(this);
		spot_time.setOnClickListener(this);
		spot_trans_info.setOnClickListener(this);
		pop_add.setOnClickListener(this);
		pop_reduce.setOnClickListener(this);
		pop_num.setOnClickListener(this);
		pop_ok.setOnClickListener(this);
		pop_del.setOnClickListener(this);

		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		// 设置popwindow的动画效果
		popupWindow.setAnimationStyle(R.style.popWindow_anim_style);
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		popupWindow.setOnDismissListener(this);// 当popWindow消失时的监听器
	}

	public interface OnItemClickListener {
		// 设置点击确认按钮是的监听接口
		public void onClickOKPop();
	}

	/**
	 * 设置监听器
	 * 
	 * @param listener
	 */
	public void setOnItemClickListener(OnItemClickListener listener) {
		this.listener = listener;
	}

	/**
	 * 点击组件的事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pop_add:// 购买最大数量可以为750
			if (!pop_num.getText().toString().equals("750")) {
				String num_add = Integer.valueOf(pop_num.getText().toString())
						+ ADDORREDUCE + "";
				pop_num.setText(num_add);
			} else {
				Toast.makeText(context, "不能超过最大产品数量", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.pop_reduce:
			if (!pop_num.getText().toString().equals("1")) {
				String num_reduce = Integer.valueOf(pop_num.getText()
						.toString()) - ADDORREDUCE + "";
				pop_num.setText(num_reduce);
			} else {
				Toast.makeText(context, "购买数量不能低于1件", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.pop_del:
			listener.onClickOKPop();
			dissmiss();
			break;
		case R.id.pop_ok:
			listener.onClickOKPop();
			HashMap<String, Object> allHashMap = new HashMap<String, Object>();
			allHashMap.put("num", pop_num.getText().toString());
			// 保存数据之后再写
			break;
		default:
			break;
		}
	}

	/**
	 * 当popWindow消失时响应
	 */
	@Override
	public void onDismiss() {

	}

	public void showAsDropDown(View parent) {
		popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
	}

	/**
	 * 消除弹窗
	 */
	public void dissmiss() {
		popupWindow.dismiss();
	}

}
