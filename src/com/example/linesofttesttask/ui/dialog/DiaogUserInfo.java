package com.example.linesofttesttask.ui.dialog;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.linesofttesttask.R;
import com.example.linesofttesttask.data.GitUser;
import com.example.linesofttesttask.data.GitUserInfo;
import com.example.linesofttesttask.net.GetUserInfoProtocol;
import com.example.linesofttesttask.ui.adapter.RepositsAdapter;
import com.example.linesofttesttask.untils.AnimateFirstDisplayListener;
import com.example.linesofttesttask.untils.GlobalConst;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class DiaogUserInfo implements LoaderCallbacks<GitUserInfo>{
	
	LayoutInflater inflater;
	ListView lvRepos;
	TextView tvUserName;
	ImageView ivUserAvatar;
	AlertDialog dialog;
	
	protected ImageLoader imageLoader;
	Context ctx;
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener;
	GitUser gitUser;
	
	RepositsAdapter adapter;
	Fragment frag;

	public DiaogUserInfo(Context ctx, GitUser user, final Fragment frag) {
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(20))
		.build();
		imageLoader = ImageLoader.getInstance();
		
		animateFirstListener = new AnimateFirstDisplayListener();
		

		gitUser=user;
		
		this.frag=frag;
		this.ctx=ctx;
		inflater=(LayoutInflater) ctx.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		adapter= new RepositsAdapter(ctx);
		
		AlertDialog.Builder adb= new AlertDialog.Builder(ctx);
		adb.setTitle(ctx.getString(R.string.user_info_dialog));
		adb.setMessage(user.getUserLogin());
	
	
		
		View view = inflater.inflate(R.layout.user_info_dialog, null, false);
		
		
		lvRepos=(ListView) view.findViewById(R.id.lvDialUInfoReposits);
		tvUserName=(TextView) view.findViewById(R.id.tvDialUInfoName);
		ivUserAvatar=(ImageView) view.findViewById(R.id.ivDialUInfoIcon);
		
		adb.setView(view);
		lvRepos.setAdapter(adapter);
		dialog=adb.create();
		dialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				frag.getLoaderManager().destroyLoader(100);
			}
		});
		
		
	}
	
	public void showDialog(){
		dialog.show();
		imageLoader.displayImage(gitUser.getUserAvatarUrl(),
				ivUserAvatar, options, animateFirstListener);
		tvUserName.setText(gitUser.getUserLogin());
		frag.getLoaderManager().destroyLoader(100);
		frag.getLoaderManager().initLoader(100, null, this);
	}
	
	@Override
	public Loader<GitUserInfo> onCreateLoader(int id, Bundle args) {

		return new  UserInfoLoader(ctx,gitUser.getUserLogin());
	}
	@Override
	public void onLoadFinished(Loader<GitUserInfo> loader, GitUserInfo info) {
		if (info!=null) {
			//setListShown(true);
			//adapter.clean();
			adapter.addAll(info.getReposits());
			adapter.notifyDataSetChanged();
			
		}
		
	}
	@Override
	public void onLoaderReset(Loader<GitUserInfo> loader) {
		adapter.clean();
		loader.stopLoading();
		
	}
	
	public static class  UserInfoLoader extends AsyncTaskLoader<GitUserInfo>{

		GitUserInfo userInfo;
	
		String userLogin;
		
		public  UserInfoLoader(Context context, String userLogin) {
			super(context);
			this.userLogin=userLogin;
			Log.d(GlobalConst.LOG_TAG,"UserInfoLoader");
			
		}
		
	

		@Override
		public GitUserInfo loadInBackground() {
			GitUserInfo answer=null;
			GetUserInfoProtocol infoProtocol= new GetUserInfoProtocol();
			try {
				answer=infoProtocol.getUsersInfo(userLogin);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return answer;
		}
		
		
		@Override
		public void deliverResult(GitUserInfo data) {
			userInfo=data;
			if (isStarted()) {
				super.deliverResult(data);
			}	
		}
		
		@Override
		protected void onStartLoading() {
		
			if (userInfo != null) {
				// If we currently have a result available, deliver it
				// immediately.
				deliverResult(userInfo);
			} else {
				forceLoad();
			}
		}
		
		@Override
		protected void onStopLoading() {
			// Attempt to cancel the current load task if possible.
            cancelLoad();
		}
		
		@Override
		protected void onReset() {
			stopLoading();
			if (userInfo!=null) {
				userInfo=null;
			}
		}
	}
}
