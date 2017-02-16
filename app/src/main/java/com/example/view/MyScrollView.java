package com.example.view;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Scroller;

public class MyScrollView extends ScrollView {
	private GestureDetectorCompat mGestureDetector;
	OnTouchListener mGestureListener;
//	private int lastXIntercept = 0, lastYIntercept = 0;
	protected Scroller mScroller;
	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
//		mScroller = new Scroller(getContext());
		mGestureDetector = new GestureDetectorCompat(context, new YScrollDetector());
		setFadingEdgeLength(0);
	}

	 @Override
	 public boolean onInterceptTouchEvent(MotionEvent ev) {
	 return super.onInterceptTouchEvent(ev) &&
	 mGestureDetector.onTouchEvent(ev);
	 }

	class YScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			if (Math.abs(distanceY) > Math.abs(distanceX)) {
				return true;
			}
			return false;
		}
	}
/**
 * 解决横竖冲突
 */
//	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		boolean intercepted = false;
//		int x = (int) ev.getX();
//		int y = (int) ev.getY();
//		switch (ev.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			lastXIntercept = x;
//			lastYIntercept = y;
//			intercepted = false;
//			if (!mScroller.isFinished()) {
//				mScroller.abortAnimation();
//				intercepted = true;
//			}
//			break;
//		case MotionEvent.ACTION_MOVE:
//			final int deltaX = x - lastXIntercept;
//			final int deltaY = y - lastYIntercept;
//			/* 根据条件判断是否拦截该事件 */
//			if (Math.abs(deltaX) > Math.abs(deltaY)) {
//				intercepted = false;
//			} else {
//				intercepted = true;
//			}
//			break;
//		case MotionEvent.ACTION_UP:
//			intercepted = false;
//			break;
//		}
//		lastXIntercept = x;
//		lastYIntercept = y;
//		return intercepted;
//	};
}
