package com.example.util;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * Created by wyj on 2017/2/22.
 * 全部修改字体大小
 */

public class TextScaleUtils {
    public static void scaleTextSize(Activity activity, int isScale) {
        float size;
        Configuration configuration = activity.getResources().getConfiguration();
        if (0==isScale) {
            size = 0.85f;
        } else if(1==isScale){
            size = 1f;
        }else{
            size = 1.15f;
        }
        configuration.fontScale = size; //0.85 small size, 1 normal size, 1.15 big etc
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        activity.getBaseContext().getResources().updateConfiguration(configuration, metrics);//更新全局的配置
    }
}