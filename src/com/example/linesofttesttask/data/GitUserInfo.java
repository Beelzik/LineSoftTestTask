package com.example.linesofttesttask.data;

import java.util.ArrayList;

import org.json.JSONObject;

import android.util.Log;

import com.example.linesofttesttask.net.GetUserInfoRepo;
import com.example.linesofttesttask.untils.GlobalConst;

public class GitUserInfo {


	String userLogin;
	int userReposits;
	String userAvatarUrl;
	Long userId;
	ArrayList<UserReposit> reposits;
	
	
	public GitUserInfo(JSONObject jsonObject) throws Exception {
	
			userId=jsonObject.getLong("id");
			userAvatarUrl=jsonObject.getString("avatar_url");
			
			userLogin=jsonObject.getString("login");	
			reposits=new GetUserInfoRepo().getUsersRepo(userLogin);
			
			Log.d(GlobalConst.LOG_TAG,"GitUserInfo: "+userLogin);
		
	}
	
	

	public ArrayList<UserReposit> getReposits() {
		return reposits;
	}
	
	public void setReposits(ArrayList<UserReposit> reposits) {
		this.reposits = reposits;
	}

	public String getUserLogin() {
		return userLogin;
	}


	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}


	public int getUserReposits() {
		return userReposits;
	}


	public void setUserReposits(int userReposits) {
		this.userReposits = userReposits;
	}


	public String getUserAvatarUrl() {
		return userAvatarUrl;
	}


	public void setUserAvatarUrl(String userAvatarUrl) {
		this.userAvatarUrl = userAvatarUrl;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
