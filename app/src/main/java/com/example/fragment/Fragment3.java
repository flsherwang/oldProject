package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.old.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;

public class Fragment3 extends BaseFragment {
    @ViewInject(R.id.mPaper)
    private ViewPager mPaper;
    @ViewInject(R.id.radioGroup)
    private RadioGroup radioGroup;
    private FragmentPagerAdapter mAdapter;
    Fragment fragment;
    HashMap<Integer, Fragment> mFragments = new HashMap<>();


    static Fragment3 fragment3;

    public static Fragment3 newInstance() {
        fragment3 = new Fragment3();
        return fragment3;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment3, getViewGroup(), false);
        ViewUtils.inject(this, view);
        radioGroup.setOnCheckedChangeListener(changeListener);
        mAdapter = new ViewPagerAdapter(getChildFragmentManager());

        mPaper.setAdapter(mAdapter);
        mPaper.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        return view;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {

    }

    RadioGroup.OnCheckedChangeListener changeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_rdio1:
                    mPaper.setCurrentItem(0);
                    break;
                case R.id.rb_rdio2:
                    mPaper.setCurrentItem(1);
                    break;
                case R.id.rb_rdio3:
                    mPaper.setCurrentItem(2);
                    break;

            }
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
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            if (mFragments.containsKey(position)) {
                fragment = mFragments.get(position);
            } else {
                fragment = RankFragment1.newInstance(position);
                mFragments.put(position, fragment);
            }
            return fragment;
        }

        @Override
        public int getItemPosition(Object object) {
            return mAdapter.POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            RankFragment1 f = (RankFragment1) super.instantiateItem(container, position);
            return f;
        }
    }
}
