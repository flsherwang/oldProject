package com.example.util;

import java.util.ArrayList;
import java.util.List;

import com.example.application.OldApplication;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
/**
 * Created by Jookie  on 2015/11/24.  http://www.coding84.com
 *   向上滑动时隐藏标题栏动画
 *   //初始化动画使用方法
     ViewAnimation viewAnimation = new ViewAnimation(getActivity(),lview);
     viewAnimation.registerAnimation(ViewAnimation.DIRECTION_UP,mall_head_wrapper);
 */
public class ViewAnimation {
    private View target = null;//滑动的view
    public static final int DIRECTION_UP = 1;//向上隐藏
    public static final int DIRECTION_DOWN = 2;//向下隐藏
    private Context context;
    private float downY = 0;//按下时Y坐标
    private float moveY = 0;//移动时Y坐标
    private float upY = 0;//放开时的Y坐标
    private long DURATION = 500;//动画效果时间
    private int DISTANCE = 0;
    private List<OtherAnimation> animationList = new ArrayList<OtherAnimation>();
    /**
     * 构造函数
     * @param context context
     * @param target 被滑动的view
     */
    public ViewAnimation(Context context, View target) {
        this.context = context;
        this.target = target;
        DISTANCE = DisplayUtil.dip2px(context, 2);//滑动Y轴敏感距离
        target.setOnTouchListener(touch);
    }
    /**
     * 注册动画效果
     *
     * @param direction    方向 1 //向上隐藏 2 //向下隐藏
     * @param needHideView 需要隐藏的view
     */
    public void registerAnimation(int direction, View needHideView) {
        OtherAnimation e = new OtherAnimation(direction, needHideView);
        e.setAnimationListener(listener);
        animationList.add(e);
    }
    private View.OnTouchListener touch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN://按下
                    /**
                     *  event.getRowX（）：触摸点相对于屏幕原点的x坐标
                        event.getX（）：   触摸点相对于其所在组件原点的x坐标
                        !!!!!!!!!!!!!!!!注意，如果target是个listview  则不能在adapter 中的convertView 设置Onitemclick 或者OnClick 否则事件会冲突 获取不到ACTION_DOWN!!!!!!!!!!!!!!!!!!
                     */
                    downY = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE://移动
                    moveY = event.getRawY();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_OUTSIDE:
                    upY = event.getRawY();

                    float moveDistance = downY - upY;
                    //如果滑动Y距离大于等于mall_head_wrapper的高度则隐藏mall_head_wrapper
                    if (moveDistance >= DISTANCE && target.getTag() == null) {//向下滑超过mall_head_wrapper的高度
                        target.setTag("hidden");//tag用于标记是否隐藏了
                        //隐藏view
                        for (OtherAnimation a : animationList) {
                            a.label = -1;
                            a.setDuration(DURATION);
                            a.view.startAnimation(a);
                        }
                        //如果向下滑动则显示view;
                    } else if (moveDistance <= -DISTANCE && target.getTag() != null) {//显示
                        target.setTag(null);
                        //显示view
                        showAll();
                    }
                    break;
            }
            return false;
        }
    };

    private void showAll() {
        for (OtherAnimation a : animationList) {
            a.label = 1;
            a.setDuration(DURATION);
            a.view.startAnimation(a);
        }
    }

    public class OtherAnimation extends Animation {
        public int label = -1;//-1隐藏 1显示
        public int direction = 1;
        public View view;

        public OtherAnimation(int direction, View view) {
            this.direction = direction;
            this.view = view;
        }
        @Override
        protected void applyTransformation(float interpolatdTime, Transformation t) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            int margin;
            if (label == -1) {
                margin = -(int) (view.getMeasuredHeight() * interpolatdTime);
            } else {
                margin = -(int) (view.getMeasuredHeight() - view.getMeasuredHeight() * interpolatdTime);
            }
            if (direction == DIRECTION_UP) {//向上隐藏
                layoutParams.topMargin = margin;
                view.setLayoutParams(layoutParams);
            } else {//向下隐藏
                layoutParams.bottomMargin = margin;
                view.setLayoutParams(layoutParams);
            }
        }

    }
    Animation.AnimationListener listener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            OldApplication.indicatorClickable = false;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
        	OldApplication.indicatorClickable = true;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
}

