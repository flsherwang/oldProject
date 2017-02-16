package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.adapter.RelativeAdapter;
import com.example.adapter.StoryAdapter;
import com.example.old.CoverActivity;
import com.example.old.R;
import com.example.util.AnimationUtil;
import com.example.view.HorizontalListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class Fragment2 extends BaseFragment {
    @ViewInject(R.id.horizantal_lv)
    private HorizontalListView horizantal_lv;
    @ViewInject(R.id.listView)
    private ListView listView;
    private RelativeAdapter adapter_rl;
    private StoryAdapter storyAdapter;


    static Fragment2 fragment2;

    public static Fragment2 newInstance() {
        fragment2 = new Fragment2();
        return fragment2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment2, container, false);
        ViewUtils.inject(this, view);
        initView();
        return view;
    }

    private void initView() {
        adapter_rl = new RelativeAdapter(getActivity());
        horizantal_lv.setAdapter(adapter_rl);
        storyAdapter = new StoryAdapter(getActivity());
        listView.setAdapter(storyAdapter);
        horizantal_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AnimationUtil.startActivity(getActivity(), new Intent(getActivity(), CoverActivity.class));
            }
        });
    }
}
