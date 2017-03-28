package com.example.old;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.adapter.ImageGridAdapter;
import com.example.application.OldApplication;
import com.example.entity.CustomConstants;
import com.example.entity.ImageItem;
import com.example.entity.IntentConstants;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 图片选择
 * 
 */
public class ImageChooseActivity extends BaseActivity {
	@ViewInject(R.id.gridview)
	private GridView mGridView;
	@ViewInject(R.id.finish_btn)
	private Button mFinishBtn;
	private List<ImageItem> mDataList = new ArrayList<>();
	private String mBucketName;
	private int availableSize;
	private ImageGridAdapter mAdapter;
	private HashMap<String, ImageItem> selectedImgs = new HashMap<String, ImageItem>();
	public static final String ACTION = "ImageChooseActivity";

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_image_choose);
		OldApplication.getInstance().addActivity(this);
		ViewUtils.inject(this);
		mDataList = (List<ImageItem>) getIntent().getSerializableExtra(IntentConstants.EXTRA_IMAGE_LIST);
		if (mDataList == null)
			mDataList = new ArrayList<>();
		mBucketName = getIntent().getStringExtra(IntentConstants.EXTRA_BUCKET_NAME);

		if (TextUtils.isEmpty(mBucketName)) {
			mBucketName = "请选择";
		}
		availableSize = CustomConstants.MAX_IMAGE_SIZE;
		initView();
	}

	private void initView() {
		initBackView();
		initTitleView(mBucketName);
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mAdapter = new ImageGridAdapter(context, mDataList);
		mGridView.setAdapter(mAdapter);
		mFinishBtn.setText("完成" + "(" + selectedImgs.size() + "/" + availableSize + ")");
		mAdapter.notifyDataSetChanged();
		mFinishBtn.setOnClickListener(new FinishOnClickListener());
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ImageItem item = mDataList.get(position);
				if (item.isSelected) {
					item.isSelected = false;
					selectedImgs.remove(item.imageId);
				} else {
					if (selectedImgs.size() >= availableSize) {
						Toast.makeText(context, "最多选择" + availableSize + "张图片", Toast.LENGTH_SHORT).show();
						return;
					}
					item.isSelected = true;
					selectedImgs.put(item.imageId, item);
				}
				mFinishBtn.setText("完成" + "(" + selectedImgs.size() + "/" + availableSize + ")");
				mAdapter.notifyDataSetChanged();
			}

		});
	}

	/**
	 * 选择完成
	 */
	public class FinishOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(ACTION);
			intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST, (Serializable) new ArrayList<ImageItem>(selectedImgs.values()));
			sendBroadcast(intent);
			OldApplication.getInstance().toFinish(ImageBucketChooseActivity.class);
			OldApplication.getInstance().toFinish(ImageChooseActivity.class);
		}
	}
}