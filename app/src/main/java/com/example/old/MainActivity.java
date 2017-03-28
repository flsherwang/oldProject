package com.example.old;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.application.OldApplication;
import com.example.fragment.Fragment1;
import com.example.fragment.Fragment2;
import com.example.fragment.Fragment3;
import com.example.util.AnimationUtil;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;
    @ViewInject(R.id.radioGroup)
    private RadioGroup radioGroup;
    @ViewInject(R.id.add_iv)
    private ImageView add_iv;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OldApplication.getInstance().addActivity(this);
        ViewUtils.inject(this);
        initLayout();
        initControls();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @SuppressWarnings("deprecation")
    private void initControls() {
        radioGroup.setOnCheckedChangeListener(changeListener);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(1);
        viewPager.setOnPageChangeListener(pageChangeListener);
        UserClick click = new UserClick();
        add_iv.setOnClickListener(click);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    class UserClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add_iv:
                    AnimationUtil.startActivity(MainActivity.this, new Intent().setClass(MainActivity.this, PublicStoryActivity.class));
//                AnimationUtil.startActivity(MainActivity.this, new Intent().setClass(MainActivity.this,WebViewActivity.class).putExtra("title","智偕老知识集市").putExtra("url","https://www.yixielao.com/zsjs/index.html"));
                    break;
            }
        }
    }

    /**
     * 初始化底部菜单事件
     */
    public void initLayout() {
        Fragment1 fragment1 = Fragment1.newInstance();
        Fragment2 fragment2 = Fragment2.newInstance();
        Fragment3 fragment3 = Fragment3.newInstance();
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mFragments.add(fragment3);
    }

    RadioGroup.OnCheckedChangeListener changeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_rdio1:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.rb_rdio2:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.rb_rdio3:
                    viewPager.setCurrentItem(2);
                    break;

            }
        }
    };
    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            ((RadioButton) radioGroup.getChildAt(i)).setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }

    };

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        FragmentManager fm = null;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
