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
	/** ��¼loading��ʾ�� */
	private ProgressDialog proDialog;
	/** ���ע��ʧ��,������Ը��û�ȷ�е���Ϣ��ʾ,true����������ʧ��,falseע��ʧ�� */
	private boolean isNetError;
	
	
	/** ��¼��̨֪ͨ����UI�߳�,��Ҫ���ڵ�¼ʧ��,֪ͨUI�̸߳��½��� */
	Handler RegisterHandler = new Handler() {
		public void handleMessage(Message msg) {
			isNetError = msg.getData().getBoolean("isNetError");
			if (proDialog != null) {
				proDialog.dismiss();
			}
			if (isNetError) {
				Toast.makeText(Register.this, "ע��ʧ��:\n1.��������������.\n2.����ϵ����.!",
						Toast.LENGTH_SHORT).show();
			}
			// �û������������
			else {
				Toast.makeText(Register.this, "ע��ʧ��,�û��ѱ�ע��!",
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
		// Ȼ��ִ��ע�������,����Զ�̷�����ע���û�
	}

	/** 1.��ʼ��ע��view��� */
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

	/** ����ע��ȷ����ť */
	private OnClickListener submitListener = new OnClickListener() {
		@Override
		
		public void onClick(View v) {
			userName = view_userName.getText().toString();
			password = view_password.getText().toString();
			String passwordConfirm = view_passwordConfirm.getText().toString();
			boolean vForm=validateForm(userName, password, passwordConfirm);
			if (vForm)
			{
				
			proDialog = ProgressDialog.show(Register.this, "������..",
					"������..���Ժ�....", true, true);
			// ��һ���߳̽��е�¼��֤,��Ҫ������ʧ��,�ɹ�����ֱ��ͨ��startAcitivity(Intent)ת��
			Thread RegisterThread = new Thread(new RegisterFailureHandler());
			RegisterThread.start();
			}
		}
	};

	/** ��ռ�����ť */
	private OnClickListener clearListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			clearForm();
		}
	};

	/** ���ע��� */
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
			//sub����ΪҪ��ȥ���һ�� \n���з�
			Toast.makeText(this, suggest.subSequence(0, suggest.length() - 1),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		else {
			
			return true;
		}
	}

	/** ��ձ� */
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
			//���ﻻ�������֤��ַ
			String validateURL="http://10.0.2.2/index.php/index/AndroidLogin/register?username="
				+ userName + "&password=" + password;
			boolean RegisterState = validateLocalRegister(userName, password,
					validateURL);
			Log.d(this.toString(), "validateLogin");

			// ��½�ɹ�
			if (RegisterState) {
				// ��Ҫ�������ݵ���½��Ľ���,
				Intent intent = new Intent();
				intent.setClass(Register.this, MainActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("MAP_USERNAME", userName);
				intent.putExtras(bundle);
				// ת���½���ҳ��
				startActivity(intent);
				proDialog.dismiss();
			} else {
				// ͨ������handler��֪ͨUI���̸߳���UI,
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
		// ���ڱ�ǵ�½״̬
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
			//��ȡ�������ĵ�¼״̬��			
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
		/*// ��½�ɹ�
		if (loginState) {
			if (isRememberMe()) {
				saveSharePreferences(true, true);
			} else {
				saveSharePreferences(true, false);
			}
		} else {
			// ��������������
			if (!isNetError) {
				clearSharePassword();
			}
		}
		if (!view_rememberMe.isChecked()) {
			clearSharePassword();
		}*/
		return RegisterState;
	}
	
	//����������
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
	
	/** �������ڶԻ��� */
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
