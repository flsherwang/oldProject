package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.adapter.StoryListAdapter;
import com.example.old.R;
import com.example.util.NetWorkUtils;
import com.example.view.refresh.PullToRefreshBase;
import com.example.view.refresh.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class RankFragment1 extends BaseFragment {
	@ViewInject(R.id.pull_to_refresh_lv)
	private PullToRefreshListView pull_to_refresh_lv;
	private ListView listView;
	private StoryListAdapter storyListAdapter;
	private int i = 0;//0周榜 1月榜 2 总榜
	public static RankFragment1 newInstance(int i) {
		RankFragment1 fragment = new RankFragment1();
		fragment.i = i;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.rank_fragment1, getViewGroup(),false);
		ViewUtils.inject(this, view);
		initView();
		return view;
	}

	private void initView(){
		initPull();
//		pullRefresh();
		storyListAdapter = new StoryListAdapter(getActivity());
		listView.setAdapter(storyListAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Active act;
//				if (active.getAds() != null) {
//					act = active.getLact().get(position - 1);
//				} else {
//					act = active.getLact().get(position);
//				}
//				if (act != null) {
//					Intent intent = new Intent(getActivity(), ActDetialActivity.class);
//					intent.putExtra("uuid", act.getUuid());
//					intent.putExtra("position", position);
//					AnimationUtil.startActivity(getActivity(), intent);
//				}
			}
		});
	}
	private void initPull() {
		pull_to_refresh_lv.setPullLoadEnabled(false);
		pull_to_refresh_lv.setScrollLoadEnabled(true);
		pull_to_refresh_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				pullRefresh();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				pullMore();
			}
		});
		listView = pull_to_refresh_lv.getRefreshableView();
		listView.setVerticalScrollBarEnabled(false);
		listView.setDivider(null);
	}

	public void pullRefresh() {
		if (NetWorkUtils.isConnect(getActivity())) {
			System.gc();
//			TaskExecutor.newOrderedExecutor().put(getList(true)).put(doneRefresh()).start();
		} else {
			showNetWorkToast();
			refreshset();
		}
	}

	private void pullMore() {
		if (NetWorkUtils.isConnect(getActivity())) {
			System.gc();
//			TaskExecutor.newOrderedExecutor().put(getList(false)).put(doneMore()).start();
			refreshset();
		} else {
			showNetWorkToast();
			refreshset();
		}
	}
	private void refreshset() {
		pull_to_refresh_lv.onPullDownRefreshComplete();
//		pull_to_refresh_lv.setHasMoreData(active.isHasmore());
	}
}
