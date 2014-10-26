package com.example.linesofttesttask.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.linesofttesttask.R;
import com.example.linesofttesttask.data.GitUser;
import com.example.linesofttesttask.net.GetUserStreamProtocol;
import com.example.linesofttesttask.ui.OnGitUserStreamClick;
import com.example.linesofttesttask.ui.activity.MainActivity;
import com.example.linesofttesttask.ui.adapter.UserStreamAdapter;
import com.example.linesofttesttask.ui.dialog.DiaogUserInfo;
import com.example.linesofttesttask.untils.GlobalConst;


public  class MainFragment extends Fragment implements OnRefreshListener, LoaderCallbacks<List<GitUser>> {

	private static final String ARG_SECTION_NUMBER = "section_number";
	
	SwipeRefreshLayout  swipeLayout;
	ListView lvUserStream;
	UserStreamAdapter adapter;
	int state=0;
	boolean isLoad=false;
	OnGitUserStreamClick gitUserStreamClick;
	
	public static MainFragment newInstance(OnGitUserStreamClick gitUserStreamClick) {
		MainFragment fragment = new MainFragment(gitUserStreamClick);
		Bundle args = new Bundle();
		
		fragment.setArguments(args);
		return fragment;
	}

	public MainFragment(OnGitUserStreamClick gitUserStreamClick) {
		this.gitUserStreamClick=gitUserStreamClick;
	}

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		
		gitUserStreamClick=((MainActivity)getActivity()).getOnGitUserStreamClick();
		swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swStreamCont);
		    swipeLayout.setOnRefreshListener(this);
		    swipeLayout.setColorScheme(android.R.color.holo_blue_bright, 
		            android.R.color.holo_green_light, 
		            android.R.color.holo_orange_light, 
		            android.R.color.holo_red_light);
		    
		 lvUserStream = (ListView) rootView.findViewById(R.id.lvUserStream);
		 lvUserStream.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				DiaogUserInfo diaogUserInfo= new DiaogUserInfo(getActivity(),
						(GitUser) adapter.getItem(position), MainFragment.this);
				diaogUserInfo.showDialog();
				if(gitUserStreamClick!=null){
					gitUserStreamClick.onGitUserStreamClick((GitUser) adapter.getItem(position));
				}
				
			}
		});
		
		return rootView;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter= new UserStreamAdapter(getActivity());
		getLoaderManager().initLoader(0, null, this);
		lvUserStream.setAdapter(adapter);
		lvUserStream.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				//Log.d(GlobalConst.LOG_TAG, "Start load: "+((firstVisibleItem+visibleItemCount+50)>totalItemCount));
				if(((firstVisibleItem+visibleItemCount+50)>totalItemCount) && (!isLoad) && state>0){
					
					//if(!getLoaderManager().getLoader(0).isStarted()){
						getLoaderManager().getLoader(0).forceLoad();
						Log.d(GlobalConst.LOG_TAG, "Start load");
				//}
						
					
					isLoad=true;
				}
				
			}
		});
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_NUMBER));
	}

	@Override
	public void onRefresh() {
		adapter.clean();
		state=0;
		getLoaderManager().destroyLoader(0);
		getLoaderManager().initLoader(0, null, this);
	}
	
	public int getState() {
		return state;
	}
	
	
	@Override
	public Loader<List<GitUser>> onCreateLoader(int id, Bundle args) {
		isLoad=true;
		
		return new  UserStreamLoader(getActivity(),swipeLayout,this);
	}
	@Override
	public void onLoadFinished(Loader<List<GitUser>> loader, List<GitUser> users) {
		if (users!=null) {
			//setListShown(true);
			//adapter.clean();
			state+=users.size();
			adapter.addAll(users);
			adapter.notifyDataSetChanged();
			swipeLayout.setRefreshing(false);
			isLoad=false;
		}
		
	}
	@Override
	public void onLoaderReset(Loader<List<GitUser>> loader) {
		adapter.clean();
		
	}
	
	private static class  UserStreamLoader extends AsyncTaskLoader<List<GitUser>>{

		List<GitUser> users;
		SwipeRefreshLayout  swipeLayout;
		MainFragment fragment;
		int state;
		
		public  UserStreamLoader(Context context, SwipeRefreshLayout  swipeLayout, MainFragment fragment) {
			super(context);
			this.swipeLayout=swipeLayout;
			this.fragment=fragment;
			
		}
		
	@Override
	protected void onForceLoad() {
		state=fragment.getState();
		super.onForceLoad();
	}

		@Override
		public List<GitUser> loadInBackground() {
			List<GitUser> answer= new ArrayList<GitUser>();
			GetUserStreamProtocol streamProtocol=new GetUserStreamProtocol();
			try {
				answer=streamProtocol.getUsers(state);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return answer;
		}
		
		
		@Override
		public void deliverResult(List<GitUser> data) {
			users=data;
			if (isStarted()) {
				super.deliverResult(data);
			}	
		}
		
		@Override
		protected void onStartLoading() {
			swipeLayout.setRefreshing(true);
			if (users != null) {
				// If we currently have a result available, deliver it
				// immediately.
				deliverResult(users);
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
			if (users!=null) {
				users=null;
			}
		}
	}
}