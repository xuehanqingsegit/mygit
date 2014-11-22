package com.HITIIDX.netservice;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.HITIIDX.domain.Postbar;
import com.HITIIDX.util.FileUtil;
import com.HITIIDX.util.StringUtil;

public class GetPostbarService extends NetService{

	private final static String postbarUrl = "http://tieba.baidu.com/f?ie=utf-8&mo_device=1";

	/**
	 * ��ȡ�����б���(�Ӱٶ����ɻ�ȡ����)
	 * ע������δ�ҵ�json���ݽӿڣ��˴�ֱ�ӽ���html����
	 * @param postBarName ��������
	 * @param page ҳ��
	 * @return ���ɼ���
	 */
	public static List<Postbar> getPostbarsByPage(String postBarName,int page){
		List<Postbar> postbars = new ArrayList<Postbar>();
		try{
			String kw = StringUtil.changeEncoding(postBarName, "ISO-8859-1");
			Document document = getDocumentByUrl(postbarUrl+"&kw="+kw+"&pn="+page*50);
			for(Element element:document.select(".tl_shadow").select(".j_common")){
				Postbar postbar = new Postbar();
				postbar.setTitle(element.select(".ti_title").get(0).text());
				if(element.select(".ti_abs").size()>0){
				    postbar.setDetails(element.select(".ti_abs").get(0).text());
				}
				postbar.setAuthor(element.select(".ti_author").get(0).text());
				postbar.setDate(element.select(".ti_time").get(0).text());
				postbar.setReplyNum(element.select(".btn_icon").get(0).text());
				postbar.setUrl(element.absUrl("href"));
				List<String> photoUrls = new ArrayList<String>();
				for(Element photoEle : element.select(".medias_item")){
					photoUrls.add(photoEle.child(0).absUrl("data-url").replace("amp;", ""));
				}
				postbar.setPhotoUrls(photoUrls);
				postbars.add(postbar);
			}
		}catch(Exception e){
			FileUtil.addFile(e.getStackTrace().toString());
			e.printStackTrace();
		}
		return postbars;
	}

}
