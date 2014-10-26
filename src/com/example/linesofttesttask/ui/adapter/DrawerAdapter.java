package com.example.linesofttesttask.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linesofttesttask.R;

import com.example.linesofttesttask.data.GitUser;
import com.example.linesofttesttask.ui.OnDrawerSearchClick;
import com.example.linesofttesttask.untils.AnimateFirstDisplayListener;
import com.example.linesofttesttask.untils.GlobalConst;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class DrawerAdapter extends BaseAdapter{

	LayoutInflater inflater;
	ArrayList<GitUser> data;
	
	protected ImageLoader imageLoader;
	Context ctx;
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener;
	

	
	boolean isSearchItemAttached=false;
	
	public DrawerAdapter(Context ctx) {
		
		data= new  ArrayList<>();
		inflater=(LayoutInflater) ctx.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		this.ctx=ctx;
		
		
		
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
	}
	
	public boolean isSearchItemAttached() {
		return isSearchItemAttached;
	}
	
	public void isSearchItemAttached(boolean isSearchItemAttached){
		this.isSearchItemAttached=isSearchItemAttached;
	}
	
	

	
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}
	
	public void addWatchedUser(GitUser item){
		if(isSearchItemAttached){
			cleanUserList();
			data.add(item);
			isSearchItemAttached=false;
		}else{
			if(!isUserAlreadyInAdapter(item)){
			if(data.size()>4){
				data.remove(0);
				data.add(item);
			}else{
				data.add(item);
			}}
		}
	}

	
	public boolean isUserAlreadyInAdapter(GitUser item){
		
		for (int i = 0; i < data.size(); i++) {
			if(data.get(i).equals(item)){
				return true;
			}
			
		}
		
		return false;
	}
	public void cleanUserList() {
		data.clear();
		
	}
	
	

	
	public void cleanUserListExceptPosition(int position) {
		GitUser gitUser=data.get(position);
		cleanUserList();
		data.add(gitUser);
		
	}
	
	public void addAll(List<GitUser> item) {
		data.addAll(item);
		
	}
	
	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup vg) {
		
		final ViewHolder holder;
		GitUser targetItem=(GitUser ) getItem(position);
		
		if(view==null){
			view=inflater.inflate(R.layout.drawer_item, null,false);
			holder= new ViewHolder();
			holder.ivUserIcon=(ImageView) view.findViewById(R.id.ivDrawItemUserIcon);
			holder.tvUserLogin=(TextView) view.findViewById(R.id.tvDrawItemUserName);
			
			
			
			
			view.setTag(holder);
		}else{
			holder=(ViewHolder) view.getTag();
		}
		
		
		
			
			if(targetItem.getUserLogin()!=null){
				holder.tvUserLogin.setText(targetItem.getUserLogin());
			}
	
			imageLoader.displayImage(targetItem.getUserAvatarUrl(),
					holder.ivUserIcon, options, animateFirstListener);
			
		
		
		
		return view;

	}
	
	
	
	private class ViewHolder{
		
		
		
		ImageView ivUserIcon;
		TextView tvUserLogin;
		
		
		
		
	}

}
