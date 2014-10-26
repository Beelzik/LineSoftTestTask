package com.example.linesofttesttask.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linesofttesttask.R;
import com.example.linesofttesttask.data.GitUser;
import com.example.linesofttesttask.data.UserReposit;

import com.example.linesofttesttask.untils.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class RepositsAdapter extends BaseAdapter{
	ArrayList<UserReposit> data;
	LayoutInflater inflater;
	SpannableString forkedSpan;
	SpannableString notForkedSpan;
	
	protected ImageLoader imageLoader;
	Context ctx;
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener;;
	
	public  RepositsAdapter(Context ctx) {
		data= new ArrayList<>();
		inflater=(LayoutInflater) ctx.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		this.ctx=ctx;
	
		forkedSpan=new SpannableString(ctx.getString(R.string.repo_forked));
		notForkedSpan=new SpannableString(ctx.getString(R.string.repo_not_forked));
		forkedSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 0, forkedSpan.length(),0);
		
		notForkedSpan.setSpan(new ForegroundColorSpan(Color.GREEN), 0, notForkedSpan.length(),0);
		
		
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
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		
		ViewHolder holder;
		UserReposit targetReposit=(UserReposit) getItem(position);
		
		if(view==null){
			view=inflater.inflate(R.layout.reposit_list_item, null,false);
			holder= new ViewHolder();
		
			holder.tvRepoName=(TextView) view.findViewById(R.id.tvRepositName);
			holder.tvRepoForkStatus=(TextView) view.findViewById(R.id.tvReposForkStatus);
			holder.tvRepoUrl=(TextView) view.findViewById(R.id.tvRepoUri);
			view.setTag(holder);
		}else{
			holder=(ViewHolder) view.getTag();
		}
		
		holder.tvRepoName.setText(targetReposit.getName());
		holder.tvRepoForkStatus.setText(targetReposit.isFork()==true ? forkedSpan : notForkedSpan );
		holder.tvRepoUrl.setText(targetReposit.getUrl());
	
		return view;
	}

	public void clean() {
		data.clear();
	}

	public void addAll(List<UserReposit> users) {
		data.addAll(users);
		
	}
	
	private class ViewHolder{
		
		TextView tvRepoName;
		TextView tvRepoForkStatus;
		TextView tvRepoUrl;
	}
}
