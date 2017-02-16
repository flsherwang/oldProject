package com.example.constant;

import com.lidroid.xutils.HttpUtils;

/**
 * 不要在这里写toast,因为在非ui线程中不允许使用ui线程的东西
 * 2016-07-26 by lwj
 */

import android.content.Context;

public class HttpSetting {
	public static HttpSetting set = null;
	public static HttpUtils http = null;

	public static HttpSetting instance(Context context) {
		if (set == null||http == null) {
			set = new HttpSetting();
			http = new HttpUtils();
			http.configTimeout(30000);
			http.configDefaultHttpCacheExpiry(1000 * 10);
			http.configHttpCacheSize(0);
			http.configRequestThreadPoolSize(5);
		}
//		if (!NetWorkUtils.isConnect(context)) {
//			NetWorkToast.makeText(context, "", 3500).show();
//		}
		return set;
	}

	public static void setNull() {
		set = null;
	}
}
