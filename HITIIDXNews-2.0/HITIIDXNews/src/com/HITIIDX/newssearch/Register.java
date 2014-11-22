package com.HITIIDX.newssearch;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;





import com.HITIIDX.HITnewssearch.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {
	private String userName;
	private String password;
	private EditText view_userName;
	private EditText view_password;
	private EditText view_passwordConfirm;
	private Button view_submit;
	private Button view_clearAll;
	private static final int MENU_EXIT = Menu.FIRST - 1;
	private static final int MENU_ABOUT = Menu.FIRST;
	/** 登录loading提示框 */
	private ProgressDialog proDialog;
	/** 如果注册失败,这个可以给用户确切的消息显示,true是网络连接失败,false注册失败 */
	private boolean isNetError;
	
	
	/** 登录后台通知更新UI线程,主要用于登录失败,通知UI线程更新界面 */
	Handler RegisterHandler = new Handler() {
		public void handleMessage(Message msg) {
			isNetError = msg.getData().getBoolean("isNetError");
			if (proDialog != null) {
				proDialog.dismiss();
			}
			if (isNetError) {
				Toast.makeText(Register.this, "注册失败:\n1.请检查您网络连接.\n2.请联系我们.!",
						Toast.LENGTH_SHORT).show();
			}
			// 用户名和密码错误
			else {
				Toast.makeText(Register.this, "注册失败,用户已被注册!",
						Toast.LENGTH_SHORT).show();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		findViews();
		setListener();
		// 然后执行注册的事情,访问远程服务器注册用户
	}

	/** 1.初始化注册view组件 */
	private void findViews() {
		view_userName = (EditText) findViewById(R.id.registerUserName);
		view_password = (EditText) findViewById(R.id.registerPassword);
		view_passwordConfirm = (EditText) findViewById(R.id.registerPasswordConfirm);
		view_submit = (Button) findViewById(R.id.registerSubmit);
		view_clearAll = (Button) findViewById(R.id.registerClear);
	}

	private void setListener() {
		view_submit.setOnClickListener(submitListener);
		view_clearAll.setOnClickListener(clearListener);
	}

	/** 监听注册确定按钮 */
	private OnClickListener submitListener = new OnClickListener() {
		@Override
		
		public void onClick(View v) {
			userName = view_userName.getText().toString();
			password = view_password.getText().toString();
			String passwordConfirm = view_passwordConfirm.getText().toString();
			boolean vForm=validateForm(userName, password, passwordConfirm);
			if (vForm)
			{
				
			proDialog = ProgressDialog.show(Register.this, "连接中..",
					"连接中..请稍后....", true, true);
			// 开一个线程进行登录验证,主要是用于失败,成功可以直接通过startAcitivity(Intent)转向
			Thread RegisterThread = new Thread(new RegisterFailureHandler());
			RegisterThread.start();
			}
		}
	};

	/** 清空监听按钮 */
	private OnClickListener clearListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			clearForm();
		}
	};

	/** 检查注册表单 */
	private boolean validateForm(String userName, String password, String password2) {
		StringBuilder suggest = new StringBuilder();
		Log.d(this.toString(), "validate");
		if (userName.length() < 1) {
			suggest.append(getText(R.string.suggust_userName) + "\n");
		}
		if (password.length() < 1 || password2.length() < 1) {
			suggest.append(getText(R.string.suggust_passwordNotEmpty) + "\n");
		}
		if (!password.equals(password2)) {
			suggest.append(getText(R.string.suggest_passwordNotSame));
		}
		if (suggest.length() > 0) {
			//sub是因为要出去最后一个 \n换行符
			Toast.makeText(this, suggest.subSequence(0, suggest.length() - 1),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		else {
			
			return true;
		}
	}

	/** 清空表单 */
	private void clearForm() {
		view_userName.setText("");
		view_password.setText("");
		view_passwordConfirm.setText("");

		view_userName.requestFocus();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_EXIT, 0, R.string.MENU_EXIT);
		menu.add(0, MENU_ABOUT, 0, R.string.MENU_ABOUT);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		switch (item.getItemId()) {
		case MENU_EXIT:
			finish();
			break;
		case MENU_ABOUT:
			alertAbout();
			break;
		}
		;
		return true;
	}

	
	
	class RegisterFailureHandler implements Runnable {
		@Override
		public void run() {
			userName = view_userName.getText().toString();
			password = view_password.getText().toString();
			//这里换成你的验证地址
			String validateURL="http://10.0.2.2/index.php/index/AndroidLogin/register?username="
				+ userName + "&password=" + password;
			boolean RegisterState = validateLocalRegister(userName, password,
					validateURL);
			Log.d(this.toString(), "validateLogin");

			// 登陆成功
			if (RegisterState) {
				// 需要传输数据到登陆后的界面,
				Intent intent = new Intent();
				intent.setClass(Register.this, MainActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("MAP_USERNAME", userName);
				intent.putExtras(bundle);
				// 转向登陆后的页面
				startActivity(intent);
				proDialog.dismiss();
			} else {
				// 通过调用handler来通知UI主线程更新UI,
				Message message = new Message();
				Bundle bundle = new Bundle();
				bundle.putBoolean("isNetError", isNetError);
				message.setData(bundle);
				RegisterHandler.sendMessage(message);
			}
		}

	}
	private boolean validateLocalRegister(String userName, String password,
			String validateUrl) {
		// 用于标记登陆状态
		boolean RegisterState = false;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(validateUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() != 200) {
				isNetError = true;
				return false;
			}
			int RegisterStateInt =0; 
			InputStream inStream = conn.getInputStream();
			byte[] data = readStream(inStream); 
			String json = new String(data);
			JSONObject rs = new JSONObject(json);
			RegisterStateInt=  rs.getInt("state");
			String username=  rs.getString("username");
			//读取服务器的登录状态码			
			if (RegisterStateInt > 0) {
				RegisterState = true;
				SharedPreferences savelogin =getSharedPreferences("savelogin", 0);
				Editor editor=savelogin.edit();
				editor.putString("username",username);
				editor.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			isNetError = true;
			Log.d(this.toString(), e.getMessage() + "  127 line");
		}
		/*// 登陆成功
		if (loginState) {
			if (isRememberMe()) {
				saveSharePreferences(true, true);
			} else {
				saveSharePreferences(true, false);
			}
		} else {
			// 如果不是网络错误
			if (!isNetError) {
				clearSharePassword();
			}
		}
		if (!view_rememberMe.isChecked()) {
			clearSharePassword();
		}*/
		return RegisterState;
	}
	
	//读入数据流
    public static byte[] readStream(InputStream inputStream) throws Exception {   
        ByteArrayOutputStream bout = new ByteArrayOutputStream();   
        byte[] buffer = new byte[1024];   
        int len = 0;   
        while ((len = inputStream.read(buffer)) != -1) {   
            bout.write(buffer, 0, len);   
        }   
        bout.close();   
        inputStream.close();   
  
        return bout.toByteArray();   
    } 
	
	/** 弹出关于对话框 */
	private void alertAbout() {
		new AlertDialog.Builder(Register.this).setTitle(R.string.MENU_ABOUT)
				.setMessage(R.string.aboutInfo).setPositiveButton(
						R.string.ok_label,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
							}
						}).show();
	}
}
