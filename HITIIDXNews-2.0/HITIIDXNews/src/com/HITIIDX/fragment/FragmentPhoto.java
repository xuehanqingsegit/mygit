package com.HITIIDX.fragment;

import java.util.List;

import com.HITIIDX.adapter.PhotoListViewAdapter;
import com.HITIIDX.domain.Photo;
import com.HITIIDX.netservice.GetPhotoService;
import com.HITIIDX.HITnewssearch.R;


import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentPhoto extends Fragment implements OnScrollListener{

	private PhotoListViewAdapter adapter;
	private boolean isLoading = true;
	private int page = 0;
	private Activity activity;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_photo, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		activity = getActivity();
		ListView listview = (ListView) activity.findViewById(R.id.fragment_photo_listview);
		adapter = new PhotoListViewAdapter(activity,null);
		listview.addFooterView(View.inflate(activity, R.layout.foot, null));
		listview.setAdapter(adapter);
		listview.setOnScrollListener(this);
		new MyAsyncTashGetPhoto().execute(0);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(activity, "������item", Toast.LENGTH_SHORT).show();
			}
		});
		super.onActivityCreated(savedInstanceState);
	}
	
	/**
	 * �첽��ȡͼƬ������
	 * 
	 *
	 */
	public class MyAsyncTashGetPhoto extends AsyncTask<Integer, String, List<Photo>>{
		@Override
		protected List<Photo> doInBackground(Integer... params) {
			List<Photo> photos = GetPhotoService.getPhotosByPage("��������ҵ��ѧ",page);
			return photos;
		}
		@Override
		protected void onPostExecute(List<Photo> photos) {
			isLoading = false;
			if(photos!=null && photos.size()>0){
				adapter.addPhotos(photos);
				adapter.notifyDataSetChanged();
				page ++ ;
			}
			super.onPostExecute(photos);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {	
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if(totalItemCount <= firstVisibleItem+visibleItemCount+1&&!isLoading){
			isLoading = true;
			new MyAsyncTashGetPhoto().execute(page);
		}
	}

}
