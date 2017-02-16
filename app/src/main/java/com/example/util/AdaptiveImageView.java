package com.example.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.lidroid.xutils.util.LogUtils;

/**
 * 按比例缩放
 *
 * @author yjm
 *         <p/>
 *         <p/>
 *         <p/>
 *         2014年10月20日
 */


public class AdaptiveImageView extends ImageView {

    // 控件默认长、宽
    private int defaultWidth = 0;
    private int defaultHeight = 0;
    // 比例
    private float scale = 0;

    public AdaptiveImageView(Context context) {
        super(context);
    }

    public AdaptiveImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdaptiveImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        Drawable drawable = getDrawable();
//        if (drawable == null) {
//            return;
//        }
//        if (getWidth() == 0 || getHeight() == 0) {
//            return;
//        }
//        this.measure(0, 0);
//        if (drawable.getClass() == NinePatchDrawable.class)
//            return;
//
//        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
//        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
//        if (bitmap.getWidth() == 0 || bitmap.getHeight() == 0) {
//            return;
//        }
//        if (defaultWidth == 0) {
//            defaultWidth = getWidth();
//        }
//        if (defaultHeight == 0) {
//            defaultHeight = getHeight();
//        }
//        scale = (float) defaultWidth / (float) bitmap.getWidth();
//        defaultHeight = Math.round(bitmap.getHeight() * scale);
//        LayoutParams params = this.getLayoutParams();
//        params.width = defaultWidth;
//        params.height = defaultHeight;
//        this.setLayoutParams(params);
//        super.onDraw(canvas);
////        LogUtils.e( "\npic: bitmapwidth:" + bitmap.getWidth() + "  bitmapHeight:" + bitmap.getHeight() );
////        LogUtils.e( "\npic: defaultWidth:" + defaultWidth + "  defaultHeight:" + defaultHeight );
////        LogUtils.e( "\npic: scale:" + scale  );
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getDrawable() == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        // 计算出ImageView的宽度
        int viewWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        // 根据图片长宽比例算出ImageView的高度
        int widthTmp = getDrawable().getIntrinsicWidth();
        int viewHeight = 0;
        if ( 0 == widthTmp ){
            viewHeight = viewWidth / 2;
        } else {
            viewHeight = viewWidth * getDrawable().getIntrinsicHeight() / widthTmp;
        }

        // 将计算出的宽度和高度设置为图片显示的大小
        setMeasuredDimension(viewWidth, viewHeight);
    }
}



//
//public class AdaptiveImageView extends ImageView {
//
//    // 控件默认长、宽
//    private int defaultWidth = 0;
//    private int defaultHeight = 0;
//    // 比例
//    private float scale = 0;
//
//    public AdaptiveImageView(Context context) {
//        super(context);
//    }
//
//    public AdaptiveImageView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public AdaptiveImageView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        Drawable drawable = getDrawable();
//        if (drawable == null) {
//            return;
//        }
//        if (getWidth() == 0 || getHeight() == 0) {
//            return;
//        }
//        this.measure(0, 0);
//        if (drawable.getClass() == NinePatchDrawable.class)
//            return;
//
//        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
//        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
//        if (bitmap.getWidth() == 0 || bitmap.getHeight() == 0) {
//            return;
//        }
//        if (defaultWidth == 0) {
//            defaultWidth = getWidth();
//        }
//        if (defaultHeight == 0) {
//            defaultHeight = getHeight();
//        }
//        scale = (float) defaultWidth / (float) bitmap.getWidth();
//        defaultHeight = Math.round(bitmap.getHeight() * scale);
//        LayoutParams params = this.getLayoutParams();
//        params.width = defaultWidth;
//        params.height = defaultHeight;
//        this.setLayoutParams(params);
//        super.onDraw(canvas);
//    }
//}