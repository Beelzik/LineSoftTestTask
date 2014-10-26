package com.example.linesofttesttask.net;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.example.linesofttesttask.untils.GlobalConst;

import android.util.Log;

public abstract class AbstractNetProtocol {

	private final String BASE_URL_="https://api.github.com/";
	private String abstractMethod=BASE_URL_;
	private String query=abstractMethod;

	private List<NameValuePair> paramList= new ArrayList<NameValuePair>();
	private final int TIME_OUT_CONECTION=2000;
	
	public AbstractNetProtocol(String method) {
		if(method!=null)
		query+=method;
	
	}
	
	
	public String createRequest(String queryType){
		String param=createParams();
		StringBuilder answer=new StringBuilder();
		answer.append(query);
		answer.append(queryType);
		answer.append("?");
		answer.append(param);
	
		return answer.toString();
	}
	
	public String sendRequest(String queryType){
		String response =null;
		String requestString=createRequest(queryType);
		Log.d(GlobalConst.LOG_TAG,"requestString: "+requestString);
		URL requestUrl;
		HttpsURLConnection urlConnection=null;
		try {
			requestUrl= new URL(requestString);
			urlConnection=(HttpsURLConnection) requestUrl.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setConnectTimeout(TIME_OUT_CONECTION);
			
			InputStream is=urlConnection.getInputStream();
			response=inputStreamToString(is);
			
		} catch (Exception e) {
			Log.d("LOG_TAG","sendRequest ERROR ");
			e.printStackTrace();
		}finally{
			if(urlConnection!=null){
				urlConnection.disconnect();
			}
		}
		
		
		return response;
	}
	
	private String inputStreamToString(InputStream is) throws Exception{
		StringBuilder answer=new StringBuilder();
		BufferedReader reader=new BufferedReader(new InputStreamReader(is));
		String line;
		while ((line=reader.readLine())!=null) {
			answer.append(line);
		//	Log.d("LOG_TAG","line: "+line);
		}
		
		return answer.toString();
		
	}
	
	
	
	public void addParam(String name, String value){
		paramList.add(new BasicNameValuePair(name, value));
	}
	
	
	public void setParam(String name, String value){
		for (NameValuePair param : paramList) {
			if(param.getName().equalsIgnoreCase(name)){
				paramList.remove(param);
			}
		}
		paramList.add(new BasicNameValuePair(name, value));
	}
	
	public String createParams(){
		StringBuilder answer= new StringBuilder();
		for (NameValuePair param : paramList) {
			answer.append(param.getName());
			answer.append("=");
			answer.append(param.getValue());
	//		answer.append("&");
		}
		return answer.toString();
		
	}
}
