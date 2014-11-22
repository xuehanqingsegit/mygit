package com.HITIIDX.netservice;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.HITIIDX.domain.Photo;

public class GetPhotoService extends NetService{

	/**
	 * ��ȡͼƬ���Ϸ�����
	 * @param photoName ͼƬ����
	 * @param page ҳ��
	 * @return ͼƬ����
	 */
	public static List<Photo> getPhotosByPage(String photoName,int page){
		List<Photo> photos = new ArrayList<Photo>();
		String url = "http://m.baidu.com/img?tn=bdjsonapp&word="+photoName+"&pn="+page*30+"&rn=30";
		JSONObject jsonObject = getJsonObjectByUrl(url);
		try{
			JSONArray jsonArray  =jsonObject.getJSONArray("data");
			for(int i=0;i<jsonArray.length();i++){
				JSONObject object = jsonArray.getJSONObject(i);
				Photo photo = new Photo();
				photo.setPhotoUrl(object.getString("thumburl2"));
				photo.setWidth(object.getInt("width"));
				photo.setHeight(object.getInt("height"));
				photos.add(photo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return photos;
	}
}
