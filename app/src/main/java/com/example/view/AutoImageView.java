package com.example.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AutoImageView extends ImageView {

    public AutoImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public AutoImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //
        int width = getDrawable().getIntrinsicWidth();
        int height = getDrawable().getIntrinsicHeight();
        int currentWidth = getMeasuredWidth();
        int currentHeight = getMeasuredHeight();
        float xx = (float)currentWidth / (float)width;
        currentHeight = (int)(xx * height);
        setMeasuredDimension(currentWidth, currentHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 方案二
        Drawable mipmap = getDrawable();
        float scale = (float)getMeasuredWidth() / (float) mipmap.getIntrinsicWidth();
        canvas.save();
        canvas.scale(scale, scale);
        mipmap.draw(canvas);
        canvas.restore();

        // 这个方案图像会失真
        // Matrix matrix = new Matrix();
        // matrix.setScale(0.5f, 0.5f);
        // canvas.drawBitmap(drawable.getBitmap(), matrix, paint);

    }

}
