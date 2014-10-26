package com.example.linesofttesttask.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linesofttesttask.R;
import com.example.linesofttesttask.data.GitUser;
import com.example.linesofttesttask.untils.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class UserStreamAdapter extends BaseAdapter{

	ArrayList<GitUser> data;
	LayoutInflater inflater;
	
	protected ImageLoader imageLoader;
	Context ctx;
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener;;
	
	public UserStreamAdapter(Context ctx) {
		data= new ArrayList<>();
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
	@Override
	public int getCount() {
		
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return ((GitUser) data.get(position)).getUserId();
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		
		ViewHolder holder;
		GitUser targetUser=(GitUser) getItem(position);
		
		if(view==null){
			view=inflater.inflate(R.layout.user_stream_list_item, null,false);
			holder= new ViewHolder();
			holder.ivUserIcon=(ImageView) view.findViewById(R.id.ivStreamItUserIcon);
			holder.tvUserLogin=(TextView) view.findViewById(R.id.tvStreamItUserLogin);
			view.setTag(holder);
		}else{
			holder=(ViewHolder) view.getTag();
		}
		
		holder.tvUserLogin.setText(targetUser.getUserLogin());
		imageLoader.displayImage(targetUser.getUserAvatarUrl(),
				holder.ivUserIcon, options, animateFirstListener);
		return view;
	}

	public void clean() {
		data.clear();
	}

	public void addAll(List<GitUser> users) {
		data.addAll(users);
		
	}
	
	private class ViewHolder{
		
		ImageView ivUserIcon;
		TextView tvUserLogin;
	}

}
