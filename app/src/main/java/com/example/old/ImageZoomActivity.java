package com.example.old;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.entity.ImageItem;
import com.example.entity.IntentConstants;
import com.example.util.GetBitmapUsingOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 滑动查看图片zoom
 * 
 * @author xyy
 * 
 */
public class ImageZoomActivity extends BaseActivity {

	private ViewPager pager;
	private MyPageAdapter adapter;
	private int currentPosition;
	private List<ImageItem> mDataList = new ArrayList<ImageItem>();
	private RelativeLayout photo_relativeLayout;
	private Long logId;// 记录id
	private int hasPicCount;// 已有的图片数量
	private int oldDeletePosition = -1;// 记录上次删除的位置
	private int deleteCount = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_zoom);
		initView();
	}

	@SuppressWarnings("unchecked")
	private void initView() {
		Intent intent = getIntent();
		currentPosition = intent.getIntExtra(IntentConstants.EXTRA_CURRENT_IMG_POSITION, 0);
		mDataList = (List<ImageItem>) intent.getSerializableExtra(IntentConstants.EXTRA_IMAGE_LIST);
		logId = intent.getLongExtra("logId", -1);
		hasPicCount = intent.getIntExtra("hasPicCount", -1);
		photo_relativeLayout = (RelativeLayout) findViewById(R.id.photo_relativeLayout);
		photo_relativeLayout.setBackgroundColor(0x70000000);
		Button photo_bt_exit = (Button) findViewById(R.id.photo_bt_exit);
		photo_bt_exit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK, new Intent().putExtra(IntentConstants.EXTRA_IMAGE_LIST, (Serializable) mDataList));
				finish();
			}
		});
		Button photo_bt_del = (Button) findViewById(R.id.photo_bt_del);
		photo_bt_del.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mDataList.remove(currentPosition);
				adapter.removeView(currentPosition);
				adapter.notifyDataSetChanged();
				if (mDataList.size() == 0) {
					setResult(RESULT_OK);
					finish();
				}
			}
		});
		pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setOnPageChangeListener(pageChangeListener);
		adapter = new MyPageAdapter(mDataList);
		pager.setAdapter(adapter);
		pager.setCurrentItem(currentPosition);
	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int arg0) {
			currentPosition = arg0;
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageScrollStateChanged(int arg0) {

		}
	};

	class MyPageAdapter extends PagerAdapter {
		private List<ImageItem> dataList = new ArrayList<ImageItem>();
		private ArrayList<ImageView> mViews = new ArrayList<ImageView>();

		public MyPageAdapter(List<ImageItem> dataList) {

			this.dataList = dataList;
			int len = dataList.size();
			for (int i = 0; i < len; i++) {
				ImageView iv = new ImageView(context);
				iv.setImageBitmap(GetBitmapUsingOptions.execute(dataList.get(i).sourcePath));
				iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				mViews.add(iv);
			}
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public Object instantiateItem(View arg0, int arg1) {
			ImageView iv = mViews.get(arg1);
			((ViewPager) arg0).addView(iv);
			return iv;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			if (mViews.size() >= arg1 + 1) {
				((ViewPager) arg0).removeView(mViews.get(arg1));
			}
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			return dataList.size();
		}

		public void removeView(int position) {
			// 当删除的是已有的图片时(删除网络图片)
			if (position < (hasPicCount - deleteCount)) {
				oldDeletePosition = position;
				mViews.remove(position);
				if (oldDeletePosition != -1) {
					if (position > oldDeletePosition) {
//						deleteCaseLogPic(logId, position + 1);
					}
					if (position < oldDeletePosition) {

//						deleteCaseLogPic(logId, position);
					}
					if (position == oldDeletePosition) {

//						deleteCaseLogPic(logId, position + 1);
					}
				} else {
//					deleteCaseLogPic(logId, position);
					dataList.remove(position);
				}
			} else {
				mViews.remove(position);
			}
		}
	}

	/**
	 * 
	 * 删除图片
	 * 
	 * @param logId
	 * @param postion
	 */
//	public void deleteCaseLogPic(Long logId, int position) {
//
//		MyAsyncHttpClient client = new MyAsyncHttpClient();
//		deleteCount++;
//		try {
//
//			client.json.put("logId", logId);
//			if (position == 0) {
//				client.json.put("postion", position);
//			} else {
//				client.json.put("postion", position - 1);
//			}
//
//			client.post(context, "deleteCaseLogPic", new MyJsonHttpResponseHandler(context, CustomProgressDialog.show(context)) {
//				@Override
//				public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
//					super.onSuccess(statusCode, headers, data);
//					alert("删除成功");
//					Intent intent = new Intent();
//					intent.setAction("action.deletePic");
//					sendBroadcast(intent);
//				}
//
//				@Override
//				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
//					super.onFailure(statusCode, headers, throwable, response);
//					if (response != null) {
//						alert("删除失败");
//					}
//				}
//			});
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		} finally {
//			client = null;
//
//		}
//	}
}