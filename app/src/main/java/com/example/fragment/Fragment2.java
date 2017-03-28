package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.adapter.RelativeAdapter;
import com.example.adapter.StoryAdapter;
import com.example.constant.CacheSetting;
import com.example.entity.Story;
import com.example.entity.User;
import com.example.okhttp.ListUserCallback;
import com.example.old.CoverActivity;
import com.example.old.EditRelativeActivity;
import com.example.old.R;
import com.example.util.AnimationUtil;
import com.example.view.HorizontalListView;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import static com.example.util.AppEncryptUtil.encryptSecretKey;

public class Fragment2 extends BaseFragment {
    @ViewInject(R.id.horizontal_lv)
    private HorizontalListView horizontal_lv;
    @ViewInject(R.id.listView)
    private ListView listView;
    @ViewInject(R.id.edit_iv)
    private ImageView edit_iv;
    private RelativeAdapter adapter_rl;
    private StoryAdapter storyAdapter;
    private User user;
    private Story story;
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
        getUsers();
        getStory();
        horizontal_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AnimationUtil.startActivity(getActivity(), new Intent(getActivity(), CoverActivity.class));
            }
        });

        edit_iv.setOnClickListener(new EditOnClickListener());
    }

    public void getStory() {
        Map<String, String> params = new HashMap<>();
        params.put("userId", "1001");
        params.put("page", "1");
        params.put("rows", "30");
        params.put("OT", String.valueOf(System.currentTimeMillis()));
        String sk = encryptSecretKey(params);
        params.put("AT", "64feec5690f2061dfa4aa4c580bba561");
        params.put("SK", sk);
        String url = "http://www.yixielao.com:8081/yxlapp/memiorApi/v1/getFriendInfo";
        OkHttpUtils.post().url(url).params(params).build().execute(new ListUserCallback() {
            @Override
            public void onError(Call call, Exception e) {
            }

            @Override
            public void onResponse(String response) {
                Log.e("wang", "-------" + response.toString());
                Gson gson = new Gson();
                story = gson.fromJson(response, Story.class);
                storyAdapter = new StoryAdapter(getActivity(), story);
                listView.setAdapter(storyAdapter);
            }
        });
    }

    public void getUsers() {
        Map<String, String> params = new HashMap<>();
        params.put("userId", "1001");
        params.put("OT", String.valueOf(System.currentTimeMillis()));
        String sk = encryptSecretKey(params);
        params.put("AT", "64feec5690f2061dfa4aa4c580bba561");
        params.put("SK", sk);
        String url = "http://www.yixielao.com:8081/yxlapp/memiorApi/v1/getGroupList";
//        params.put("userId", "1002");
//        params.put("OT", String.valueOf(System.currentTimeMillis()));
//        String sk = encryptSecretKey(params);
//        params.put("AT", "64feec5690f2061dfa4aa4c580bba561");
//        params.put("SK", sk);
//        String url = "http://www.yixielao.com:8081/yxlapp/memiorApi/v1/getMemiorList";
        OkHttpUtils.post().url(url).params(params).build().execute(new ListUserCallback() {
            @Override
            public void onError(Call call, Exception e) {
            }

            @Override
            public void onResponse(String response) {
                CacheSetting.instance().put("AYdatas", response.toString());
                Gson gson = new Gson();
                user = gson.fromJson(response, User.class);
                adapter_rl = new RelativeAdapter(getActivity(), user);
                horizontal_lv.setAdapter(adapter_rl);
            }
        });
    }

    /**
     * 编辑亲友
     */
    class EditOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            AnimationUtil.startActivity(getActivity(), new Intent(getActivity(), EditRelativeActivity.class).putExtras(bundle));
        }
    }
}
