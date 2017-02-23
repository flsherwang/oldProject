package com.example.old;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.application.OldApplication;
import com.example.util.PreferencesUtils;
import com.example.view.CustomAlertDialog;
import com.example.view.seekbar.FontSliderBar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/2/9.
 */

public class CoverActivity extends BaseActivity {
    @ViewInject(R.id.back_iv)
    private LinearLayout back_iv;
    private int states = 0;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.cover);
        OldApplication.getInstance().addActivity(this);
        ViewUtils.inject(this);
        UserClick userClick = new UserClick();
        back_iv.setOnClickListener(userClick);
        states = PreferencesUtils.getInt(this, "theme", 0);
        FontSliderBar sliderBar = (FontSliderBar) findViewById(R.id.sliderbar);
        sliderBar.setTickCount(3).setThumbIndex(1).setTickHeight(30).setBarColor(Color.MAGENTA)
                .setTextColor(Color.CYAN).setTextPadding(20).setTextSize(20)
                .setThumbRadius(20).setThumbColorNormal(Color.CYAN).setThumbColorPressed(Color.GREEN)
                .withAnimation(false).setCurrentIndex(1).applay();
        sliderBar.setOnSliderBarChangeListener(new FontSliderBar.OnSliderBarChangeListener() {
            @Override
            public void onIndexChanged(FontSliderBar rangeBar, int index) {
                PreferencesUtils.putInt(CoverActivity.this, "theme", index);
            }
        });

    }

    private class UserClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back_iv:
//                    Log.e("wang", "states=" + states + "   " + "PreferencesUtils.getInt(CoverActivity.this,\"theme\",0)=" + PreferencesUtils.getInt(CoverActivity.this, "theme", 0));
                    if (states != PreferencesUtils.getInt(CoverActivity.this, "theme", 0)) {
                        showSelectDialog();
                    } else {
                        OldApplication.getInstance().toFinish(CoverActivity.class);
                    }
                    break;
            }
        }
    }

    private void showSelectDialog() {
        CustomAlertDialog dialog = new CustomAlertDialog(CoverActivity.this);
        dialog.show("是否保存当前设置");
        dialog.setNegative(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OldApplication.getInstance().toFinish(CoverActivity.class);
            }
        });
        dialog.setPositive(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartApplication();
            }
        });
    }

    private void restartApplication() {
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
