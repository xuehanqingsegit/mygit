package com.HITIIDX.netutil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.security.interfaces.DSAKey;
import java.util.Date;



import com.HITIIDX.bean.ChatMessage;
import com.HITIIDX.bean.ChatMessage.Type;
import com.HITIIDX.bean.Result;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class Httputils {
	private static final String URL = "http://10.0.2.2/index.php/index/index/answer";
	public static ChatMessage sendMessage(String msg) {
		ChatMessage chatMessage=new ChatMessage();
		String jsonRes=doGet(msg);
		Gson gson =new Gson();
		Result result=null;
		try {
			result =gson.fromJson(jsonRes,Result.class);
			chatMessage.setMsg(result.getText());
		} catch (Exception e) {
			chatMessage.setMsg("服务器繁忙，请稍后再试");
		}
		chatMessage.setDate(new Date());
		chatMessage.setType(Type.INCOMING);
		return chatMessage;
	}

	public static String doGet(String msg) {
		String result = "";
		String url = setParams(msg);
		InputStream is = null;
		ByteArrayOutputStream baos = null;
			
			try {
				java.net.URL urlNet = new java.net.URL(url);
				HttpURLConnection conn = (HttpURLConnection) urlNet
						.openConnection();
				conn.setReadTimeout(5 * 1000);// 5miao
				conn.setConnectTimeout(5 * 1000);
				conn.setRequestMethod("GET");
				is = conn.getInputStream();
				int len = -1;
				byte[] buf = new byte[128];
				baos = new ByteArrayOutputStream();
				while ((len = is.read(buf)) != -1) {//内容读入缓冲区
					baos.write(buf, 0, len);
				}
				baos.flush();//清空缓冲区
				result = new String(baos.toByteArray());
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally {
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	private static String setParams(String msg) {
		String url="";
		try {
			url = URL +"?info=" +URLEncoder.encode(msg,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
}
