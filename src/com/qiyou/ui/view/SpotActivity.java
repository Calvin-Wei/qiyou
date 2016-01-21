/**
 * 
 */
package com.qiyou.ui.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @齐游
 * @2016-1-20
 * @author 谞臣
 * @category 景点详情页
 */
public class SpotActivity extends Activity implements OnItemClickListener,
		OnClickListener {

	private TextView pop_choice_16g,pop_choice_32g,pop_choice_16m,pop_choice_32m,pop_choice_black,pop_choice_white,pop_add,pop_reduce,pop_num,pop_ok;
	private ImageView pop_del;
	
	private PopupWindow popupWindow;
	//监听器
	private OnItemClickListener listener;
	private final int ADDORREDUCE=1;
	private Context context;
	//保存选择的颜色的数据
	private String str_color="";
	//保存选择的类型的数据
	private String str_type="";
	
	@Override
	public void onClick(View v) {
		
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}

}
