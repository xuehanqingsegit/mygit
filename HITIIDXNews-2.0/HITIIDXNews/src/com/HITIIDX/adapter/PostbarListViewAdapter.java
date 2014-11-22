package com.HITIIDX.adapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.HITIIDX.domain.Postbar;
import com.HITIIDX.netutil.GetBitmapUtil;
import com.HITIIDX.HITnewssearch.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PostbarListViewAdapter extends BaseAdapter{

	private List<Postbar> postbars;
	private Context context;
	private Map<String, SoftReference<Bitmap>> map = new HashMap<String, SoftReference<Bitmap>>();


	public PostbarListViewAdapter(Context context,List<Postbar> postbars) {
		super();
		this.postbars = postbars;
		if(postbars==null){
			this.postbars = new ArrayList<Postbar>();
		}
		this.context = context;
	}

	public void addPostbars(List<Postbar> newPostbars){
		postbars.addAll(newPostbars);
	}

	public List<Postbar> getPostbars() {
		return postbars;
	}

	@Override
	public int getCount() {
		return postbars.size();
	}

	@Override
	public Object getItem(int position) {
		return postbars.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Postbar postbar = postbars.get(position);
		if(convertView==null){
			convertView = View.inflate(context, R.layout.listview_fragment_postbar, null);
		}
		getTextViewByViewAndId(convertView,R.id.fragment_postbar_listview_title).setText(postbar.getTitle());
		getTextViewByViewAndId(convertView, R.id.fragment_postbar_listview_author).setText(postbar.getAuthor());
		getTextViewByViewAndId(convertView, R.id.fragment_postbar_listview_date).setText(postbar.getDate());
		getTextViewByViewAndId(convertView, R.id.fragment_postbar_listview_reply_Num).setText(postbar.getReplyNum());
		
		//�����첽����ͼƬ
		if(postbar.getPhotoUrls()!=null&&postbar.getPhotoUrls().size()>0){
			getTextViewByViewAndId(convertView,R.id.fragment_postbar_listview_details).setVisibility(View.GONE);
			hideImageViews(convertView, 3-postbar.getPhotoUrls().size());
			for(int i=0;i<postbar.getPhotoUrls().size()&&i<3;i++){
				String photoUrl = postbar.getPhotoUrls().get(i);
				ImageView imageView = getImageViewByTag(convertView, i);
				imageView.setImageResource(R.drawable.img_temp);
				imageView.setTag(photoUrl);
				if(map.get(photoUrl)!=null&&map.get(photoUrl).get()!=null){
					imageView.setImageBitmap(map.get(photoUrl).get());
				}else{
					MyAsyncTaskGetBitmap myAsyncTaskGetBitmap = new MyAsyncTaskGetBitmap();
					myAsyncTaskGetBitmap.imageView = imageView;
					myAsyncTaskGetBitmap.targetUrl = photoUrl;
					myAsyncTaskGetBitmap.execute("");
				}
				imageView.setVisibility(View.VISIBLE);
			}
		}else{
			getTextViewByViewAndId(convertView, R.id.fragment_postbar_listview_details).setVisibility(View.VISIBLE);
			getTextViewByViewAndId(convertView, R.id.fragment_postbar_listview_details).setText(postbar.getDetails());
			hideImageViews(convertView,3);
		}
		return convertView;
	}

	public TextView getTextViewByViewAndId(View view,int id){
		return (TextView)view.findViewById(id);
	}
	
	/**
	 * �첽��ȡ�����б�ͼƬ
	 * 
	 *
	 */
	public class MyAsyncTaskGetBitmap extends AsyncTask<String, String, Bitmap>{
		String targetUrl;
		ImageView imageView;
		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap = GetBitmapUtil.getBitmapByUrl(targetUrl);
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			map.put(targetUrl, new SoftReference<Bitmap>(bitmap));
			if(targetUrl.equals(imageView.getTag().toString())){
				imageView.setImageBitmap(bitmap);
			}
		}
	}
	
	/**
	 * ����ͼƬ����
	 * @param convertView
	 * @param i ���ؼ���ͼƬ����
	 */
	private void hideImageViews(View convertView, int i) {
		System.out.println("hide"+i);
		if(i>=1){
			getImageViewByTag(convertView, 2).setVisibility(View.GONE);
		}
		if(i>=2){
			getImageViewByTag(convertView, 1).setVisibility(View.GONE);
		}
		if(i>=3){
			getImageViewByTag(convertView, 0).setVisibility(View.GONE);
		}
	}

	/**
	 * ͨ��һ��tagֵ��ȡ��Ӧ��iamgeview��һ���������3��imageview
	 * @param view 
	 * @param tag 0 1 2�ֱ��Ӧ3��imageview
	 * @return
	 */
	public ImageView getImageViewByTag(View view , int tag){
		switch (tag) {
		case 0:
			return (ImageView) view.findViewById(R.id.fragment_postbar_listview_photo1);
		case 1:
			return (ImageView) view.findViewById(R.id.fragment_postbar_listview_photo2);
		case 2:
			return (ImageView) view.findViewById(R.id.fragment_postbar_listview_photo3);
		default:
			return (ImageView) view.findViewById(R.id.fragment_postbar_listview_photo1);
		}
	}
}
