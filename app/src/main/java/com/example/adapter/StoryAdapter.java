package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.old.R;
import com.example.old.WebViewActivity;
import com.example.util.AnimationUtil;

public class StoryAdapter extends BaseAdapter {
    private Context context;

    public StoryAdapter(Context context) {
        // TODO Auto-generated method stub
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 6;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.story_item, null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.startActivity(context, new Intent().setClass(context,WebViewActivity.class).putExtra("title","游爷爷的回忆").putExtra("url","http://wt.360youtu.com/stage/161214/172263.html?22571636"));
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView title;
    }
}
