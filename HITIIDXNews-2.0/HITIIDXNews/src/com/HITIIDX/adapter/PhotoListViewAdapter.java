package com.HITIIDX.adapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.HITIIDX.domain.Photo;
import com.HITIIDX.netutil.GetBitmapUtil;
import com.HITIIDX.HITnewssearch.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

/**
 * ͼƬ�б�������
 * ע���˴���һ�Ż�����ͼƬ��Ϊһ�飨����ȴ���1.6��Ϊһ��һ�飩
 * �˴���ͼƬ��ȡ���첽��
 * map �������Ի�ȡͼƬ
 *
 */
public class PhotoListViewAdapter extends BaseAdapter{

	private Context context;
	private List<Photo[]> photos;
	private Map<String, SoftReference<Bitmap>> map = new HashMap<String, SoftReference<Bitmap>>();

	public PhotoListViewAdapter(Context context , List<Photo> photos) {
		super();
		if(photos==null){
			photos = new ArrayList<Photo>();
		}
		this.photos = sortPhotos(photos);
		this.context = context;
	}

	/**
	 * ͼƬ���飬�����������1.6�ĵ���һ����ʾ
	 * @param sortPhotos
	 * @return
	 */
	private List<Photo[]> sortPhotos(List<Photo> sortPhotos) {
		List<Photo[]> photos = new ArrayList<Photo[]>();
		Photo[] tempPhotoArray = new Photo[2];
		int tempIndex = 0;
		for(Photo photo:sortPhotos){
			if(photo.getWidth()/photo.getHeight()>1.6){
				photos.add(new Photo[]{photo});
			}else{
				tempPhotoArray[tempIndex] = photo;
				if(tempIndex==1){
					photos.add(tempPhotoArray);
					tempIndex = 0;
					tempPhotoArray = new Photo[2];
				}else{
					tempIndex++;
				}
			}
		}
		//�����������һ���Ѿ�����
		if(tempIndex!=0){
			photos.add(new Photo[]{tempPhotoArray[0]});
		}
		return photos;
	}

	
	public void addPhotos(List<Photo> newPhotos) {
		this.photos.addAll(sortPhotos(newPhotos));
	}

	public List<Photo[]> getPhotos() {
		return photos;
	}

	@Override
	public int getCount() {
		Log.i("sa", photos.size()+"");
		return photos.size();
	}
	@Override
	public Object getItem(int position) {
		return photos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = View.inflate(context, R.layout.listview_fragment_photo, null);
		} 
		Photo[] photoArray = photos.get(position);
		if(photoArray.length == 1){
			getImageViewByTag(convertView, 1).setVisibility(View.GONE);
		}

		for(int i = 0;i<photoArray.length;i++){
			ImageView imageView = getImageViewByTag(convertView, i);
			Photo photo = photoArray[i];
			imageView.setTag(photo.getPhotoUrl());
			imageView.setImageResource(R.drawable.img_temp);
			imageView.setVisibility(View.VISIBLE);

			if(map.get(photo.getPhotoUrl())!=null&&map.get(photo.getPhotoUrl()).get()!=null){
				imageView.setImageBitmap(map.get(photo.getPhotoUrl()).get());
			}else{
				MyAsyncTaskGetBitmap myAsyncTaskGetBitmap = new MyAsyncTaskGetBitmap();
				myAsyncTaskGetBitmap.imageView = imageView;
				myAsyncTaskGetBitmap.targetUrl = photo.getPhotoUrl();
				myAsyncTaskGetBitmap.execute("");
			}
		}
		//������������������ǵ�weight����
		if(photoArray.length==2){
			((LayoutParams)getImageViewByTag(convertView, 1).getLayoutParams()).weight = getWeight(photoArray);
		}
		return convertView;
	}

	/**
	 * �Ե�һ��ͼƬΪ��׼��ȡ�ڶ���ͼƬ����ռ����
	 * @param photo
	 * @return
	 */
	public float getWeight(Photo[] photo){
		Photo photo1 = photo[0];
		Photo photo2 = photo[1];
		//���ݸ��еĿ�߱���������վ�Ŀ�ȱ�
		if(photo1.getWidth()!=0&&photo1.getHeight()!=0&&photo2.getWidth()!=0&&photo2.getHeight()!=0){
			float weight = (photo2.getWidth()/photo2.getHeight())/(photo1.getWidth()/photo1.getHeight());
			return weight;
		}
		return 1;
	}


	/**
	 * ͨ��һ��tagֵ��ȡ��Ӧ��iamgeview��һ��ͼƬ���2��imageview
	 * @param view 
	 * @param tag 0 1�ֱ��Ӧ2��imageview
	 * @return
	 */
	public ImageView getImageViewByTag(View view , int tag){
		switch (tag) {
		case 0:
			return (ImageView) view.findViewById(R.id.fragment_photo_listview_image1);
		case 1:
			return (ImageView) view.findViewById(R.id.fragment_photo_listview_image2);
		default:
			return (ImageView) view.findViewById(R.id.fragment_photo_listview_image1);
		}
	}

	/**
	 * �첽��ȡͼƬ����ʾ
	 * 
	 *
	 */
	public class MyAsyncTaskGetBitmap extends AsyncTask<String, String, Bitmap>{
		String targetUrl;
		ImageView imageView ; 
		@Override
		protected Bitmap doInBackground(String... params) {
			if(targetUrl==imageView.getTag()){
				Bitmap bitmap = GetBitmapUtil.getBitmapByUrl(targetUrl);
				return bitmap;
			}
			return null;
		}
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if(bitmap!=null){
				map.put(targetUrl, new SoftReference<Bitmap>(bitmap));
				if(targetUrl.equals(imageView.getTag())){
					imageView.setImageBitmap(bitmap);
				}
			}
			super.onPostExecute(bitmap);
		}
	}
}
