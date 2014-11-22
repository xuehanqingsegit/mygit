package com.HITIIDX.newssearch;


import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.HITIIDX.HITnewssearch.R;
import com.HITIIDX.newssearch.Login.LoginFailureHandler;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
public class Setting extends Activity {
	private CheckBox checkBox1;
	private CheckBox checkBox2;
	private CheckBox checkBox3;
	private CheckBox checkBox4;
	private CheckBox checkBox5;
	private CheckBox checkBox6;
	private CheckBox checkBox7;
	private CheckBox checkBox8;
	private String cbtext1;
	private String cbtext2;
	private String cbtext3;
	private String cbtext4;
	private String cbtext5;
	private String cbtext6;
	private String cbtext7;
	private String cbtext8;
	private Button Submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		initView();
		setListener();
	}

	private void initView() {
		boolean ifcheck;
		checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
		ifcheck = readBox("checkboxes", "cb1");
		checkBox1.setChecked(ifcheck);
		cbtext1=(String) checkBox1.getText();
		
		checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
		ifcheck = readBox("checkboxes", "cb2");
		checkBox2.setChecked(ifcheck);
		cbtext2=(String) checkBox2.getText();
		
		checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
		ifcheck = readBox("checkboxes", "cb3");
		checkBox3.setChecked(ifcheck);
		cbtext3=(String) checkBox3.getText();
		
		checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
		ifcheck = readBox("checkboxes", "cb4");
		checkBox4.setChecked(ifcheck);
		cbtext4=(String) checkBox4.getText();
		
		checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
		ifcheck = readBox("checkboxes", "cb5");
		checkBox5.setChecked(ifcheck);
		cbtext5=(String) checkBox5.getText();
		
		checkBox6 = (CheckBox) findViewById(R.id.checkBox6);
		ifcheck = readBox("checkboxes", "cb6");
		checkBox6.setChecked(ifcheck);
		cbtext6=(String) checkBox6.getText();
		
		checkBox7 = (CheckBox) findViewById(R.id.checkBox7);
		ifcheck = readBox("checkboxes", "cb7");
		checkBox7.setChecked(ifcheck);
		cbtext7=(String) checkBox7.getText();
		
		checkBox8 = (CheckBox) findViewById(R.id.checkBox8);
		ifcheck = readBox("checkboxes", "cb8");
		checkBox8.setChecked(ifcheck);
		cbtext8=(String) checkBox8.getText();
		
		Submit = (Button) findViewById(R.id.button1);
	}
	/** 设置监听器 */
	private void setListener() {
		checkBox1.setOnCheckedChangeListener(checkbox1Listener);
		checkBox2.setOnCheckedChangeListener(checkbox2Listener);
		checkBox3.setOnCheckedChangeListener(checkbox3Listener);
		checkBox4.setOnCheckedChangeListener(checkbox4Listener);
		checkBox5.setOnCheckedChangeListener(checkbox5Listener);
		checkBox6.setOnCheckedChangeListener(checkbox6Listener);
		checkBox7.setOnCheckedChangeListener(checkbox7Listener);
		checkBox8.setOnCheckedChangeListener(checkbox8Listener);
		Submit.setOnClickListener(Listener);
	}
	/** 记住我checkBoxListener */
	private OnCheckedChangeListener checkbox1Listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (checkBox1.isChecked()) {
				Toast.makeText(Setting.this, "您已勾选"+cbtext1,
						Toast.LENGTH_SHORT).show();
				writeBox("checkboxes", "cb1",cbtext1,true);
			}
			else {
				Toast.makeText(Setting.this, "您已取消勾选"+cbtext1,
						Toast.LENGTH_SHORT).show();
				writeBox("checkboxes", "cb1",cbtext1,false);
			}
			
		}
	};
	private OnCheckedChangeListener checkbox2Listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (checkBox2.isChecked()) {
				Toast.makeText(Setting.this, "您已勾选"+cbtext2,
						Toast.LENGTH_SHORT).show();
				writeBox("checkboxes", "cb2",cbtext2,true);
			}
			else {
				Toast.makeText(Setting.this, "您已取消勾选"+cbtext2,
						Toast.LENGTH_SHORT).show();
				writeBox("checkboxes", "cb2",cbtext2,false);
			}
			
		}
	};
	private OnCheckedChangeListener checkbox3Listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (checkBox3.isChecked()) {
				Toast.makeText(Setting.this, "您已勾选"+cbtext3,
						Toast.LENGTH_SHORT).show();
				writeBox("checkboxes", "cb3",cbtext3,true);
			}
			else {
				Toast.makeText(Setting.this, "您已取消勾选"+cbtext3,
						Toast.LENGTH_SHORT).show();
				writeBox("checkboxes", "cb3",cbtext3,false);
			}
			
		}
	};
	private OnCheckedChangeListener checkbox4Listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (checkBox1.isChecked()) {
				Toast.makeText(Setting.this, "您已勾选"+cbtext4,
						Toast.LENGTH_SHORT).show();
				writeBox("checkboxes", "cb4",cbtext4,true);
			}
			else {
				Toast.makeText(Setting.this, "您已取消勾选"+cbtext4,
						Toast.LENGTH_SHORT).show();
				writeBox("checkboxes", "cb4",cbtext4,false);
			}
			
		}
	};
	private OnCheckedChangeListener checkbox5Listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (checkBox1.isChecked()) {
				Toast.makeText(Setting.this, "您已勾选"+cbtext5,
						Toast.LENGTH_SHORT).show();
				writeBox("checkboxes", "cb5",cbtext5,true);
			}
			else {
				Toast.makeText(Setting.this, "您已取消勾选"+cbtext5,
						Toast.LENGTH_SHORT).show();
				writeBox("checkboxes", "cb5",cbtext5,false);
			}
			
		}
	};
	private OnCheckedChangeListener checkbox6Listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (checkBox6.isChecked()) {
				Toast.makeText(Setting.this, "您已勾选"+cbtext6,
						Toast.LENGTH_SHORT).show();
				writeBox("checkboxes", "cb6",cbtext6,true);
			}
			else {
				Toast.makeText(Setting.this, "您已取消勾选"+cbtext6,
						Toast.LENGTH_SHORT).show();
				writeBox("checkboxes", "cb6",cbtext6,false);
			}
			
		}
	};
	private OnCheckedChangeListener checkbox7Listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (checkBox7.isChecked()) {
				Toast.makeText(Setting.this, "您已勾选"+cbtext7,
						Toast.LENGTH_SHORT).show();
				writeBox("checkboxes", "cb7",cbtext7,true);
			}
			else {
				Toast.makeText(Setting.this, "您已取消勾选"+cbtext7,
						Toast.LENGTH_SHORT).show();
				writeBox("checkboxes", "cb7",cbtext7,false);
			}
			
		}
	};
	private OnCheckedChangeListener checkbox8Listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (checkBox8.isChecked()) {
				Toast.makeText(Setting.this, "您已勾选"+cbtext8,
						Toast.LENGTH_SHORT).show();
				writeBox("checkboxes", "cb8",cbtext8,true);
			}
			else {
				Toast.makeText(Setting.this, "您已取消勾选"+cbtext8,
						Toast.LENGTH_SHORT).show();
				writeBox("checkboxes", "cb8",cbtext8,false);
			}
			
		}
	};

	
	
	private OnClickListener Listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Set<String> sets= new HashSet<String>();
			int i;
			for(i=1;i<9;i++)
			{
			if(readBox("checkboxes", "cb"+i))
					 sets.add(readBoxtext("checkboxes","cb"+i+"text"));
			}
			JPushInterface.setTags(Setting.this, sets,new TagAliasCallback() {
				
				@Override
				public void gotResult(int arg0, String arg1, Set<String> arg2) {
					
					Log.d("TAG","tag is ok");
					
				}
			});
			Intent intent = new Intent();
			intent.setClass(Setting.this, MainActivity.class);
			// 转向页面
			startActivity(intent);
          
		}
	};
	public void writeBox(String scheduleID, String opStep,String text,boolean checked) {
		SharedPreferences sp = getSharedPreferences(scheduleID, 0);
		Editor edt = sp.edit();
		edt.putBoolean(opStep, checked);
		edt.putString(opStep+"text", text);
		edt.commit();
	}

	/**
	 * 功能：操作之前调用
	 */
	public boolean readBox(String scheduleID, String opStep) {
		SharedPreferences sp = getSharedPreferences(scheduleID, 0);
		return sp.getBoolean(opStep, false);
	}
	public String readBoxtext(String scheduleID, String opStep) {
		SharedPreferences sp = getSharedPreferences(scheduleID, 0);
		return sp.getString(opStep,"");
	}

}
