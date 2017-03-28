package com.example.old;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.adapter.ImageBucketAdapter;
import com.example.application.OldApplication;
import com.example.entity.ImageBucket;
import com.example.entity.ImageItem;
import com.example.entity.IntentConstants;
import com.example.util.ImageFetcher;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;




/**
 * 选择相册
 * 
 */

public class ImageBucketChooseActivity extends BaseActivity {
	private ImageFetcher mHelper;
	private List<ImageBucket> mDataList = new ArrayList<ImageBucket>();
	@ViewInject(R.id.listview)
	private ListView mListView;
	private ImageBucketAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_image_bucket_choose);
		OldApplication.getInstance().addActivity(this);
		ViewUtils.inject(this);
		mHelper = ImageFetcher.getInstance(getApplicationContext());
		initData();
		initView();
	}

	private void initData() {
		mDataList = mHelper.getImagesBucketList(true);
	}

	private void initView() {
		mAdapter = new ImageBucketAdapter(this, mDataList);
		mListView.setAdapter(mAdapter);
		initBackView();
		initTitleView("相册");
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectOne(position);
				Intent intent = new Intent(context, ImageChooseActivity.class);
				List<ImageItem> subImageItemList = new ArrayList<ImageItem>();
				if ( 0 < mDataList.get(position).imageList.size() ){
					if ( 50 <= mDataList.get(position).imageList.size() ){
						subImageItemList = new ArrayList<ImageItem>( mDataList.get(position).imageList.subList( mDataList.get(position).imageList.size() - 50, mDataList.get(position).imageList.size() - 1 ) );
					}else{
						subImageItemList = new ArrayList<ImageItem>( mDataList.get(position).imageList.subList( 0, mDataList.get(position).imageList.size() - 1 ) );
					}
				}
				//反转一下
                Collections.reverse( subImageItemList );
				intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST, (Serializable) subImageItemList  );
				intent.putExtra(IntentConstants.EXTRA_BUCKET_NAME, mDataList.get(position).bucketName);
				startActivity(intent);
			}
		});
	}

	private void selectOne(int position) {
		int size = mDataList.size();
		for (int i = 0; i < size; i++) {
			if (i == position)
				mDataList.get(i).selected = true;
			else {
				mDataList.get(i).selected = false;
			}
		}
	}

}
