package com.qiyou.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.qiyou.R;
import com.qiyou.adapter.Adapter_Words;
import com.qiyou.bean.Message;
import com.qiyou.util.DpUtils;
import com.zdp.aseo.content.AseoZdpAseo;

public class WordsActivity extends Activity implements OnClickListener {
	private ListView listMessage;
	private ImageView ivSend;
	private List<Message> list = new ArrayList<Message>();;
	private Dialog dialog;
	private EditText et_comment;
	private Adapter_Words adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_words);
		Message m = new Message();
		m.setMessage("郑州空气质量有点差");
		m.setTime("21:00");
		list.add(m);
		ivSend = (ImageView) findViewById(R.id.ivAdd);
		listMessage = (ListView) findViewById(R.id.listMessage);
		adapter = new Adapter_Words(getApplicationContext());
		adapter.BindData(list);
		Log.i("123123123", "准备进入Adapter");
		listMessage.setAdapter(adapter);
		ivSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showCommentDialog();
			}
		});
	}

	private void showCommentDialog() {
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View v = View.inflate(this, R.layout.dialog_coment, null);
		dialog.setContentView(v);
		ImageView comment_cancel = (ImageView) v
				.findViewById(R.id.comment_cancel);
		ImageView comment_fb = (ImageView) v.findViewById(R.id.comment_fb);
		et_comment = (EditText) v.findViewById(R.id.et_comment);
		comment_cancel.setOnClickListener(this);
		comment_fb.setOnClickListener(this);
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.TOP);
		WindowManager.LayoutParams p = dialogWindow.getAttributes();
		p.y = DpUtils.dip2px(this, 70);
		dialogWindow.setAttributes(p);
		dialog.show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.comment_cancel:
			dialog.cancel();
			break;
		case R.id.comment_fb:
			String Msg = et_comment.getText().toString();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy--MM--dd  HH:mm:ss ");
			Date curDate = new Date(System.currentTimeMillis());
			String time = formatter.format(curDate);
			Message message = new Message();
			message.setMessage(Msg);
			message.setTime(time);
			list.add(0, message);
			adapter.notifyDataSetChanged();
			dialog.cancel();
			break;
		}
	}
}
