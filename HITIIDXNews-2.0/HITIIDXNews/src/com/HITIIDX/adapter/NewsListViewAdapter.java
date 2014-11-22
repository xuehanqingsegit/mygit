package com.HITIIDX.adapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.HITIIDX.domain.News;
import com.HITIIDX.netutil.GetBitmapUtil;
import com.HITIIDX.HITnewssearch.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsListViewAdapter extends BaseAdapter {

	private List<News> newss;
	private Context context;
	private Map<String, SoftReference<Bitmap>> map = new HashMap<String, SoftReference<Bitmap>>();

	public NewsListViewAdapter(Context context,List<News> news) {
		super();
		this.context = context;
		if(newss==null){
			newss = new ArrayList<News>();
		}else{
			this.newss = news;
		}
	}

	public void addNews(List<News> newNewss){
		newss.addAll(newNewss);
	}

	public List<News> getNewss() {
		return newss;
	}

	@Override
	public int getCount() {
		return newss.size();
	}

	@Override
	public Object getItem(int position) {
		return newss.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		News news = newss.get(position);
		if(convertView == null){
			convertView = View.inflate(context,R.layout.listview_fragment_news, null);
		}
		((TextView)convertView.findViewById(R.id.fragment_news_listview_title)).setText(news.getTitle());
		((TextView)convertView.findViewById(R.id.fragment_news_listview_source)).setText(news.getSource());
		((TextView)convertView.findViewById(R.id.fragment_news_listview_time)).setText(news.getDate());

		//�첽����ͼƬ
		ImageView imageView = (ImageView)convertView.findViewById(R.id.fragment_news_listview_photo);
		imageView.setImageResource(R.drawable.img_temp);
		imageView.setTag(news.getPhotoUrl());
		if(TextUtils.isEmpty(news.getPhotoUrl())){
			imageView.setVisibility(View.GONE);
		}else{
			imageView.setVisibility(View.VISIBLE);
			if(map.get(news.getPhotoUrl())!=null&&map.get(news.getPhotoUrl()).get()!=null){
				imageView.setImageBitmap(map.get(news.getPhotoUrl()).get());
			}else{
				MyAsyncTaskGetBitmap myAsyncTaskGetBitmap = new MyAsyncTaskGetBitmap();
				myAsyncTaskGetBitmap.targetUrl = news.getPhotoUrl();
				myAsyncTaskGetBitmap.imageView = imageView;
				myAsyncTaskGetBitmap.execute("");
			}
		}
		return convertView;
	}

	/**
	 * �첽��ȡ�����б������ͼƬ
	 *
	 *
	 */
	public class MyAsyncTaskGetBitmap extends AsyncTask<String, String, Bitmap>{
		ImageView imageView ;
		//��Ƭ��־������imageview�����ã����ܱ�����itemռ�ã����»�ȡ��imageview��ͼƬ��item���͡�������һ����־���tag��imageView.getTag()��ͬ˵����imagevIEW��û�б�����
		String targetUrl; 
		@Override
		protected Bitmap doInBackground(String... imageViews) {
			Bitmap bitmap = GetBitmapUtil.getBitmapByUrl(targetUrl);
			return bitmap;
		}
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			map.put(targetUrl, new SoftReference<Bitmap>(bitmap));
			if(imageView.getTag().equals(targetUrl)){
				imageView.setImageBitmap(bitmap);
			}
		}

	}

}
