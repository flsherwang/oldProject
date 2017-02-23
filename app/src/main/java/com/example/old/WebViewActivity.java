package com.example.old;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;

import com.example.application.OldApplication;
import com.example.view.FrameWebView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by JiangYongQiang.
 *
 * @date 2015/9/16
 * @Description: TODO(网页内容浏览)
 */

public class WebViewActivity extends BaseActivity {

    @ViewInject(R.id.web_view)
    private FrameWebView web_view;

    private String url;
    private String title;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_webview);
        OldApplication.getInstance().addActivity(this);
        ViewUtils.inject(this);
        initView();
    }

    private void initView() {
        initBackView();
//        initTitleView(getResources().getString(R.string.myjoin_title));
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        initTitleView(title);
        url = intent.getStringExtra("url");
        setWebView(url);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView(String url) {
        WebSettings settings = web_view.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不使用缓存
        settings.setDomStorageEnabled(true);//储存功能
        settings.setJavaScriptEnabled(true); // 支持JavaScript
        web_view.setHorizontalScrollBarEnabled(false); // 横向滚动条
        settings.setSupportZoom(false); // 支持缩放
        settings.setBuiltInZoomControls(false);// 显示放大缩小
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        web_view.loadUrl(url);
    }
}
