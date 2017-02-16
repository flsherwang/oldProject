package com.example.util;

import com.example.application.OldApplication;
import com.example.old.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * Custom Toast
 *
 * @author Lucky
 *
 */
public class NetWorkToast {
	public static final int LENGTH_LONG = 3500;
	public static final int LENGTH_SHORT = 2000;
	private static WindowManager mWindowManager;
	private static WindowManager.LayoutParams mWindowParams;
	private static View toastView;
	private Context mContext;
	private static Handler mHandler;
//	private String mToastContent = "";
	private int duration = 0;
	private int animStyleId = R.style.toast_anim_style;
//	private TextView tView;

	private static final Runnable timerRunnable = new Runnable() {

		@Override
		public void run() {
			removeView();
		}
	};

	private NetWorkToast(Context context) {
		Context ctx =  OldApplication.getInstance().getLast().getApplicationContext();
		if (ctx == null) {
			ctx = context;
		}
		this.mContext = ctx;
		mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		init();
	}

	private void init() {
		mWindowParams = new WindowManager.LayoutParams();
		mWindowParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		mWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
		mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mWindowParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
		mWindowParams.format = PixelFormat.TRANSLUCENT;
		mWindowParams.type = WindowManager.LayoutParams.TYPE_TOAST;
		mWindowParams.setTitle("ToastHelper");
		mWindowParams.packageName = mContext.getPackageName();
		mWindowParams.windowAnimations = animStyleId;
		mWindowParams.y = DisplayUtil.dip2px(mContext, 35);
	}
	@SuppressLint ("InflateParams")
	private View getDefaultToastView() {
		return LayoutInflater.from(mContext).inflate(R.layout.network_pop, null);
	}

	public void show() {
		removeView();
		if (toastView == null) {
			toastView = getDefaultToastView();
		}
		mWindowParams.gravity = GravityCompat.getAbsoluteGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, ViewCompat.getLayoutDirection(toastView));
		removeView();
		if(Looper.myLooper()==null){
			Looper.prepare();
		}

		mWindowManager.addView(toastView, mWindowParams);
		if (mHandler == null) {
			mHandler = new Handler();
		}
		mHandler.postDelayed(timerRunnable, duration);
	}

	public static void removeView() {
		if (toastView != null && toastView.getParent() != null) {
			mWindowManager.removeView(toastView);
			mHandler.removeCallbacks(timerRunnable);
		}
	}

	/**
	 * @param context
	 * @param content
	 * @param duration
	 * @return
	 */
	public static NetWorkToast makeText(Context context, String content, int duration) {
		NetWorkToast helper = new NetWorkToast(context);
		helper.setDuration(duration);
		helper.setContent(content);
		return helper;
	}

	/**
	 * @param context
	 * @param strId
	 * @param duration
	 * @return
	 */
	public static NetWorkToast makeText(Context context, int strId, int duration) {
		NetWorkToast helper = new NetWorkToast(context);
		helper.setDuration(duration);
		helper.setContent(context.getString(strId));
		return helper;
	}

	public NetWorkToast setContent(String content) {
//		this.mToastContent = content;
		return this;
	}

	public NetWorkToast setDuration(int duration) {
		this.duration = duration;
		return this;
	}

	public NetWorkToast setAnimation(int animStyleId) {
		this.animStyleId = animStyleId;
		mWindowParams.windowAnimations = this.animStyleId;
		return this;
	}

	/**
	 * custom view
	 *
	 * @param view
	 */
	public NetWorkToast setView(View view) {
	    toastView = view;
		return this;
	}
}
