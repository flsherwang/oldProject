package com.example.old;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.constant.BusProvider;
import com.example.util.PreferencesUtils;
import com.example.util.TextScaleUtils;

import java.util.List;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

/**
 * @author ITWANG 1021754384@qq.com
 * @ClassName: BaseActivity
 * @Description: TODO(基础Activity类)
 * @date 2017年1月7日 下午18:12:52
 */
public class BaseActivity extends FragmentActivity {
    protected LinearLayout back_iv, rightIcon_ll;
    protected TextView title, rightMenu, rightMenu1, leftMenu;
    protected ImageView rightIcon, top_back;
    protected static Context context;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        context = this;
        BusProvider.getInstance().register(this);
        int theme= PreferencesUtils.getInt(this,"theme",0);
        TextScaleUtils.scaleTextSize(this,theme);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName) && appProcess.importance == IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showToast(String msg) {
        makeText(getApplicationContext(), msg, LENGTH_SHORT).show();
    }

    /**
     * 初始化返回按钮
     */
    public void initBackView() {
        back_iv = (LinearLayout) findViewById(R.id.back_iv);
        top_back = (ImageView) findViewById(R.id.top_back);
        if (back_iv != null) {
            back_iv.setVisibility(View.VISIBLE);
            back_iv.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
//					 OldApplication.getInstance().toFinish(getClass());
//					 AnimationUtil.backActivity(context);
                    finish();
                }
            });
        }
    }

    /**
     * 初始化左侧菜单
     */
    public void initLeftMenuView(String str) {
        leftMenu = (TextView) findViewById(R.id.leftMenu);
        if (leftMenu != null) {
            leftMenu.setVisibility(View.VISIBLE);
            leftMenu.setText(str);
        }
    }

    /**
     * 初始化标题
     */
    public void initTitleView(String str) {
        title = (TextView) findViewById(R.id.title);
        if (title != null) {
            title.setVisibility(View.VISIBLE);
            title.setText(str);
        }
    }

    /**
     * 初始化右侧菜单1
     */
    public void initRightMenuView(String str) {
        rightMenu = (TextView) findViewById(R.id.rightMenu);
        if (rightMenu != null) {
            rightMenu.setVisibility(View.VISIBLE);
            rightMenu.setText(str);
        }
    }

    /**
     * 初始化右侧菜单2
     */
    public void initRightMenuView1(String str) {
        rightMenu1 = (TextView) findViewById(R.id.rightMenu1);
        if (rightMenu1 != null) {
            rightMenu1.setVisibility(View.VISIBLE);
            rightMenu1.setText(str);
        }
    }

    /**
     * 初始化右侧图标菜单
     */
    public void initRightIcon(int resource) {
        rightIcon_ll = (LinearLayout) findViewById(R.id.rightIcon_ll);
        rightIcon = (ImageView) findViewById(R.id.rightIcon);
        if (rightIcon_ll != null) {
            rightIcon_ll.setVisibility(View.VISIBLE);
            rightIcon.setBackgroundResource(resource);
        }
    }
}
