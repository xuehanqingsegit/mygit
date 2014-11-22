package com.HITIIDX.newssearch;



import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.HITIIDX.HITnewssearch.R;

public class NewsDetailsActivity extends Activity{

	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_details);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		String url = getIntent().getStringExtra("url");
		webView = (WebView)findViewById(R.id.news_details_webview);
		webView.loadUrl(url);
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {  
				view.loadUrl(url);  
				return true;  
			}  
		});
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==android.R.id.home){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * ¼àÌý·µ»Ø°´Å¥
	 */
	public void back(View view){
		finish();
	}
	

}
