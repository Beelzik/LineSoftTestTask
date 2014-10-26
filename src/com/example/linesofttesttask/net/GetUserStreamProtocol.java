package com.example.linesofttesttask.net;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.linesofttesttask.data.GitUser;





public class GetUserStreamProtocol extends AbstractNetProtocol {
	

	

	final static String PARAM_USER_SINCE="since";
	final static String QUERY_TYPE="users";
	
	
	
	
	
	public GetUserStreamProtocol() {
		super(null);
		
	}

	public List<GitUser> getUsers(int since) throws Exception{
		List<GitUser> answerUsers=new ArrayList<GitUser>();
	
		addParam(PARAM_USER_SINCE,Integer.toString(since));
		String response=sendRequest(QUERY_TYPE);
		
		
		JSONArray jsonArray=new JSONArray(response);
		//Log.d(LOG_TAG, "getAlbums jsonArray: "+jsonArray);
		if (jsonArray!=null) {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item=jsonArray.getJSONObject(i);
			//	Log.d(LOG_TAG, "getAlbums item: "+item);
				GitUser gitUser= new GitUser(item);
				answerUsers.add(gitUser);
			}
		}
		return answerUsers;
		
	}

	


}

