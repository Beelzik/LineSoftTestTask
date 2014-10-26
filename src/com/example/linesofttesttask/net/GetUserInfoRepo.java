package com.example.linesofttesttask.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.example.linesofttesttask.data.GitUserInfo;
import com.example.linesofttesttask.data.UserReposit;
import com.example.linesofttesttask.untils.GlobalConst;

public class GetUserInfoRepo  extends AbstractNetProtocol {
		

		

		
		final static String QUERY_TYPE="users/";
		final static String QUERY_PARAM="/repos";
		
		
		static final String LOG_TAG="myLogs";
		
		
		public GetUserInfoRepo() {
			super(null);
			
		}

		public ArrayList<UserReposit> getUsersRepo(String userName) throws Exception{
			ArrayList<UserReposit> answer= new ArrayList<>();
			UserReposit reposit;
		
			String queryTypePlusUserName=QUERY_TYPE+userName+QUERY_PARAM;
			String response=sendRequest(queryTypePlusUserName);
			
			Log.d(GlobalConst.LOG_TAG,"queryTypePlusUserName ");
			JSONArray jsonArray=new JSONArray(response);
			//Log.d(LOG_TAG, "getAlbums jsonArray: "+jsonArray);
			if (jsonArray!=null) {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item=jsonArray.getJSONObject(i);
				//	Log.d(LOG_TAG, "getAlbums item: "+item);
				reposit= new UserReposit(item);
				answer.add(reposit);
			}
					
				
			}
			return answer;
			
		}

		


	}


	
