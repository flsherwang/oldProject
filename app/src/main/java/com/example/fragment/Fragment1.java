package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.adapter.NeighbourAdapter;
import com.example.adapter.StoryAdapter;
import com.example.old.R;
import com.example.view.HorizontalListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class Fragment1 extends BaseFragment {
    @ViewInject(R.id.horizantal_lv)
    private HorizontalListView horizantal_lv;
    @ViewInject(R.id.listView)
    private ListView listView;
    private NeighbourAdapter adapter_rl;
    private StoryAdapter storyAdapter;


    static Fragment1 fragment1;

    public static Fragment1 newInstance() {
        fragment1 = new Fragment1();
        return fragment1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment1, container, false);
        ViewUtils.inject(this, view);
        adapter_rl = new NeighbourAdapter(getActivity());
        horizantal_lv.setAdapter(adapter_rl);
//        storyAdapter = new StoryAdapter(getActivity());
//        listView.setAdapter(storyAdapter);
        return view;
    }
}
