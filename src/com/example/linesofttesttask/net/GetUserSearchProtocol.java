package com.example.linesofttesttask.net;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.linesofttesttask.data.GitUser;

import android.util.Log;



public class GetUserSearchProtocol extends AbstractNetProtocol {

	

	final static String QUERY_TYPE_SEARCH_USER="search/users";
	final static String PARAM_USER_QUERY="q";
	
	
	
	public GetUserSearchProtocol() {
		super(null);
	}
	
	public List<GitUser> getUser( String login) throws Exception{
		List<GitUser> answerUsers= new ArrayList<GitUser>();
	//	Log.d(LOG_TAG, "getPhotos albumId: "+albumId);
		addParam(PARAM_USER_QUERY,login);
		
		String response=sendRequest(QUERY_TYPE_SEARCH_USER);
		//Log.d(LOG_TAG, "getPhotos response: "+response);
		JSONObject jObject=new JSONObject(response);
	
		 JSONArray jsonArray=jObject.optJSONArray("items");
		// Log.d(LOG_TAG, "getPhotos responseArray: "+ jsonArray);
			if (jsonArray!=null) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject item=jsonArray.getJSONObject(i);
					// Log.d(LOG_TAG, "getPhotos item: "+ item);
					GitUser gitInfo= new GitUser(item);
					answerUsers.add(gitInfo);
				}
			}
			
		return answerUsers;
		
	}

	



}
