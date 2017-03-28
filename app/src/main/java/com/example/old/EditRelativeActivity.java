package com.example.old;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.adapter.EditAdapter;
import com.example.application.OldApplication;
import com.example.entity.User;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/2/28.
 */

public class EditRelativeActivity extends BaseActivity {
    @ViewInject(R.id.listView)
    private ListView listView;
    private EditAdapter editAdapter;
    private User user;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.edit_relative);
        OldApplication.getInstance().addActivity(this);
        ViewUtils.inject(this);
        user = (User) getIntent().getSerializableExtra("user");
        initView();
    }

    private void initView() {
        initBackView();
        initTitleView("编辑亲友");
        initRightMenuView("提交");
        editAdapter = new EditAdapter(user, EditRelativeActivity.this);
        listView.setAdapter(editAdapter);
        View view = LayoutInflater.from(EditRelativeActivity.this).inflate(R.layout.footer, null);
        LinearLayout add_ll = (LinearLayout) view.findViewById(R.id.add_ll);
        listView.addFooterView(view);
        rightMenu.setOnClickListener(new RightMenuOnClickListener());
        add_ll.setOnClickListener(new AddOnClickListener());
    }

    class RightMenuOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    class AddOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

}
