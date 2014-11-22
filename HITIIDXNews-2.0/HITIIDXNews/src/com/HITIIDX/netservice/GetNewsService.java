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
	 * 通过page返回新闻列表集合
	 * @param newsName 搜索新闻名称
	 * @param page 搜索新闻页码
	 * @return
	 */
	public static List<News> getNewsByPage(String newsName,int page){/*List可以添加任何对象*/
		
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
