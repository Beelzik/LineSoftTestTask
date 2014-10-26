package com.example.linesofttesttask.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.example.linesofttesttask.data.GitUser;
import com.example.linesofttesttask.data.GitUserInfo;
import com.example.linesofttesttask.untils.GlobalConst;


	public class  GetUserInfoProtocol extends AbstractNetProtocol {
		
		final static String QUERY_TYPE="users/";
		
		
		static final String LOG_TAG="myLogs";
		
		
		public GetUserInfoProtocol() {
			super(null);
			
		}

		public GitUserInfo getUsersInfo(String userName) throws Exception{
			GitUserInfo userInfo=null;
			
			String queryTypePlusUserName=QUERY_TYPE+userName;
			String response=sendRequest(queryTypePlusUserName);
			
			//Log.d(GlobalConst.LOG_TAG, "info respone: "+response);
			JSONObject item=new  JSONObject(response);
			if (item!=null) {
			
					
				//	Log.d(LOG_TAG, "getAlbums item: "+item);
					userInfo= new GitUserInfo(item);
				
			}
			return userInfo;
			
		}

		


	}


