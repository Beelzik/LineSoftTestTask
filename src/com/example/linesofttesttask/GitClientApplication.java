package com.example.linesofttesttask;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class GitClientApplication extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		 ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
         .build();
		 ImageLoader.getInstance().init(config);
	}
}
