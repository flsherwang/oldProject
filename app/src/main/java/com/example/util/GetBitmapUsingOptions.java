package com.example.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * 当加载的图片内存消耗过大则通过options加载减少消耗的内存
 * 
 * @author zh
 * 
 */
public class GetBitmapUsingOptions {

	private static final float MAX_MEMORY = 0.039f;// 当图片消耗的内存大于该值，则需要缩略

	public static Bitmap execute(String path) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inSampleSize = 8;
		BitmapFactory.decodeFile(path, options);// 临时bitmap，用于获取宽度和高度
		float width = options.outWidth;
		float height = options.outHeight;
		float memory = calculateMemory(width, height);// 该图片消耗的内存
		if (memory >= MAX_MEMORY)// 超过限制内存需要缩略
		{
			for (int i = 2; i < 10; i++)// 检查每次缩略后是否低于临界值，未低于则增加缩略倍数,最大为81倍
			{
				if (memory / (i * i) <= MAX_MEMORY) {
					options.inSampleSize = i;
					break;
				}
			}
		}
		options.inJustDecodeBounds = false;
		try {
			bitmap = BitmapFactory.decodeFile(path, options);
		} catch (OutOfMemoryError err) {
		}
		return bitmap;
	}

	public static float calculateMemory(float width, float height) {
		float memory = 0;
		memory = ((width * height * 4) / 1024) / 1024;// 该图片消耗的内存
		return memory;
	}

}
