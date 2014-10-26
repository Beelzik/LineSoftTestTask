package com.example.linesofttesttask.data;

import org.json.JSONObject;

import com.example.linesofttesttask.untils.GlobalConst;

import android.util.Log;

public class GitUser {

	String userLogin;
	int userReposits;
	String userAvatarUrl;
	Long userId;
	
	
	public GitUser(JSONObject jsonObject) throws Exception {
	
			userId=jsonObject.getLong("id");
			userAvatarUrl=jsonObject.getString("avatar_url");
			
			userLogin=jsonObject.getString("login");	
			//Log.d(GlobalConst.LOG_TAG, " userLogin "+userLogin+" created");
		
	}


	public String getUserLogin() {
		return userLogin;
	}

	
	@Override
	public boolean equals(Object o) {
		GitUser toCheck=(GitUser) o;
		if((toCheck.userLogin).equals(this.getUserLogin())){
			return true;
		}
		return false;
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
