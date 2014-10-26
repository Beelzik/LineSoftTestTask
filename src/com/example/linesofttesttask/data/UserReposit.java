package com.example.linesofttesttask.data;

import org.json.JSONObject;

import com.example.linesofttesttask.untils.GlobalConst;

import android.util.Log;

public class UserReposit {

	String name;
	boolean isFork;
	String url;
	
	public  UserReposit(JSONObject jsonObject) throws Exception {
		
		name=jsonObject.getString("name");
		isFork=jsonObject.getBoolean("fork");
		url=jsonObject.getString("url");	
	
	Log.d(GlobalConst.LOG_TAG,"repo: "+name+" create");
}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFork() {
		return isFork;
	}

	public void setFork(boolean isFork) {
		this.isFork = isFork;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
