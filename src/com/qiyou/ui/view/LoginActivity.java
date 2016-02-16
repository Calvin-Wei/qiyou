/**
 * 
 */
package com.qiyou.ui.view;

import java.util.ArrayList;

import com.qiyou.R;
import com.qiyou.bean.User;
import com.qiyou.util.LoginUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

/**
 * @QiYou
 * @2016-2-16
 * @author 谞臣
 * 
 */
public class LoginActivity extends Activity implements OnClickListener,
		OnItemClickListener, OnDismissListener {
	protected static final String TAG = "LoginActivity";
	private Dialog mLoginingDlg; // 显示正在登录的Dialog
	private EditText mIdEditText; // 登录ID编辑框
	private EditText mPwdEditText; // 登录密码编辑框
	private Button mLoginButton; // 登录按钮
	private String mIdString;
	private String mPwdString;
	private ArrayList<User> mUsers; // 用户列表

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		initView();
		setListener();

		/* 获取已经保存好的用户密码 */
		mUsers = LoginUtils.getUserList(LoginActivity.this);

		if (mUsers.size() > 0) {
			/* 将列表中的第一个user显示在编辑框 */
			mIdEditText.setText(mUsers.get(0).getId());
			mPwdEditText.setText(mUsers.get(0).getPwd());
		}

	}

	private void setListener() {
		mIdEditText.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				mIdString = s.toString();
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		});
		mPwdEditText.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				mPwdString = s.toString();
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		});
		mLoginButton.setOnClickListener(this);
	}

	private void initView() {
		mIdEditText = (EditText) findViewById(R.id.login_edtId);
		mPwdEditText = (EditText) findViewById(R.id.login_edtPwd);
		mLoginButton = (Button) findViewById(R.id.login_btnLogin);
		initLoginingDlg();
	}

	/* 初始化正在登录对话框 */
	private void initLoginingDlg() {

		mLoginingDlg = new Dialog(this, R.style.loginingDlg);
		mLoginingDlg.setContentView(R.layout.logining_dlg);

		Window window = mLoginingDlg.getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		// 获取和mLoginingDlg关联的当前窗口的属性，从而设置它在屏幕中显示的位置

		// 获取屏幕的高宽
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int cxScreen = dm.widthPixels;
		int cyScreen = dm.heightPixels;

		int height = (int) getResources().getDimension(
				R.dimen.loginingdlg_height);// 高42dp
		int topMargin = (int) getResources().getDimension(
				R.dimen.loginingdlg_top_margin); // 上沿20dp

		params.y = (-(cyScreen - height) / 2) + topMargin; // -199
		/* 对话框默认位置在屏幕中心,所以x,y表示此控件到"屏幕中心"的偏移量 */

		params.width = cxScreen;
		params.height = height;
		// width,height表示mLoginingDlg的实际大小

		mLoginingDlg.setCanceledOnTouchOutside(true); // 设置点击Dialog外部任意区域关闭Dialog
	}

	/* 显示正在登录对话框 */
	private void showLoginingDlg() {
		if (mLoginingDlg != null)
			mLoginingDlg.show();
	}

	/* 关闭正在登录对话框 */
	private void closeLoginingDlg() {
		if (mLoginingDlg != null && mLoginingDlg.isShowing())
			mLoginingDlg.dismiss();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btnLogin:
			// 启动登录
			showLoginingDlg(); // 显示"正在登录"对话框,因为此Demo没有登录到web服务器,所以效果可能看不出.可以结合情况使用
			Log.i(TAG, mIdString + "  " + mPwdString);
			if (mIdString == null || mIdString.equals("")) { // 账号为空时
				Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT)
						.show();
			} else if (mPwdString == null || mPwdString.equals("")) {// 密码为空时
				Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT)
						.show();
			} else {// 账号和密码都不为空时
				boolean mIsSave = true;
				try {
					Log.i(TAG, "保存用户列表");
					for (User user : mUsers) { // 判断本地文档是否有此ID用户
						if (user.getId().equals(mIdString)) {
							mIsSave = false;
							break;
						}
					}
					if (mIsSave) { // 将新用户加入users
						User user = new User(mIdString, mPwdString);
						mUsers.add(user);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				closeLoginingDlg();// 关闭对话框
				Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
				finish();
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mIdEditText.setText(mUsers.get(position).getId());
		mPwdEditText.setText(mUsers.get(position).getPwd());
	}

	/* PopupWindow对象dismiss时的事件 */
	@Override
	public void onDismiss() {
		// Log.i(TAG, "切换为角向下图标");
	}

	/* 退出此Activity时保存users */
	@Override
	public void onPause() {
		super.onPause();
		try {
			LoginUtils.saveUserList(LoginActivity.this, mUsers);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
