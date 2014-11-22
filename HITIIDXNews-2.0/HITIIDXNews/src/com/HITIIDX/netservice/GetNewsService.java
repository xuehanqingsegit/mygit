package com.HITIIDX.netservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import com.HITIIDX.domain.News;

public class GetNewsService extends NetService{

	/**
	 * ͨ��page���������б���
	 * @param newsName ������������
	 * @param page ��������ҳ��
	 * @return
	 */
	public static List<News> getNewsByPage(String newsName,int page){/*List��������κζ���*/
		
		JSONObject[] jsonObjects = getJsonObjectsByUrl("http://10.0.2.2/index.php/index/index/show?nextrow="+page);
		List<News> newss = new ArrayList<News>();
		try{
			if(jsonObjects!=null&&jsonObjects.length>0){
				for(JSONObject jsonObject : jsonObjects){
					News news = new News();
					news.setTitle(jsonObject.getString("title"));
					news.setSource(jsonObject.getString("title"));
					news.setUrl(jsonObject.getString("url"));
					news.setPhotoUrl(jsonObject.getString("title"));
					news.setDate(jsonObject.getString("title"));
				    newss.add(news);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return newss;
	}
}
