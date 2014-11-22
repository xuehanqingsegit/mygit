package com.HITIIDX.newssearch;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.HITIIDX.fragment.FragmentJqr;
import com.HITIIDX.fragment.FragmentNews;
import com.HITIIDX.fragment.FragmentPhoto;
import com.HITIIDX.fragment.FragmentPostbar;
import com.HITIIDX.fragment.FragmentQuestion;
import com.HITIIDX.HITnewssearch.R;
import com.HITIIDX.HITnewssearch.R.id;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
 

public class MainActivity extends Activity {

	private Fragment[] fragments = new Fragment[4];
	private MenuItem User;
	private boolean ifcheck;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
 
		fragments[0] = new FragmentNews();
		fragments[1] = new FragmentPostbar();
		fragments[2] = new FragmentQuestion();
		fragments[3] = new FragmentJqr();
		getFragmentManager().beginTransaction().add(R.id.main_fragment,fragments[1])
		.add(R.id.main_fragment,fragments[2]).add(R.id.main_fragment,fragments[3]).add(R.id.main_fragment,fragments[0]).commit();
		newsClick(null);
		
		 
		
	}
	public void newsClick(View view){
		getFragmentManager().beginTransaction().hide(fragments[1]) 
		.hide(fragments[2]).hide(fragments[3]).show(fragments[0]).commit();
	
		getActionBar().setTitle("新闻");
	}
	
	public void postbarClick(View view){
		getFragmentManager().beginTransaction().hide(fragments[0])
		.hide(fragments[2]).hide(fragments[3]).show(fragments[1]).commit();
		
		getActionBar().setTitle("贴吧");
	}
	
	public void questionClick(View view){
		getFragmentManager().beginTransaction().hide(fragments[0])
		.hide(fragments[1]).hide(fragments[3]).show(fragments[2]).commit();
		getActionBar().setTitle("知道");
	}
	
	public void photoClick(View view){
		getFragmentManager().beginTransaction().hide(fragments[0])
		.hide(fragments[1]).hide(fragments[2]).show(fragments[3]).commit();
		getActionBar().setTitle("搜索");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu); 
		SharedPreferences share = getSharedPreferences("savelogin", 0);
		String userName = share.getString("username", "");
		User = menu.findItem(R.id.menu_hello);
		User.setTitle("你好，"+userName);
		return true;
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_share:
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain"); 
			intent.putExtra(Intent.EXTRA_TEXT, "这是一个可以定制关键词的新闻app,欢迎体验"); 
			startActivity(Intent.createChooser(intent, getTitle()));
			break;
		case R.id.menu_setting:
            Intent open = new Intent(this, Setting.class);  //自定义打开的界面
            open.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(open);
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {  
	        if (menu.getClass().getSimpleName().equals("MenuBuilder")) {  
	            try {  
	                Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);  
	                m.setAccessible(true);  
	                m.invoke(menu, true);  
	            } catch (Exception e) {  
	            	e.printStackTrace();
	            }  
	        }  
	    }  
		return super.onMenuOpened(featureId, menu);
	}

}