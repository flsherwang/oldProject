package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.entity.Story;
import com.example.old.R;
import com.example.old.WebViewActivity;
import com.example.util.AnimationUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import static com.example.util.DateUtil.getTimeGap;

public class StoryAdapter extends BaseAdapter {
    private Context context;
    private Story story;

    public StoryAdapter(Context context, Story story) {
        // TODO Auto-generated method stub
        this.context = context;
        this.story = story;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (story != null) {
            if (story.getData() != null) {
                return story.getData().size();
            }
        }
        return 0;
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
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.update_time = (TextView) convertView.findViewById(R.id.update_time);
            holder.pic = (ImageView) convertView.findViewById(R.id.pic);
            holder.tag = (TextView) convertView.findViewById(R.id.tag_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.startActivity(context, new Intent().setClass(context, WebViewActivity.class).putExtra("title", "游爷爷的回忆").putExtra("url", "http://wt.360youtu.com/stage/161214/172263.html?22571636"));
            }
        });
        if (story != null) {
            Story.Record record = story.getData().get(position);
            holder.name.setText(record.getNICK_NAME());
            holder.title.setText(record.getMEMIOR_NAME());
            holder.content.setText(record.getMEMIOR_REMARK());
            holder.update_time.setText(getTimeGap(record.getLAST_UPDATE_TIME()));
            ImageLoader.getInstance().displayImage(record.getMEMIOR_FACE(), holder.pic);
            String tag = record.getCHANNEL_TAG();
            if (tag.equals("1")) {
                holder.tag.setText("原来的我");
            } else if (tag.equals("2")) {
                holder.tag.setText("缤纷生活");
            } else {
                holder.tag.setText("亲子成长");
            }
        }
        return convertView;
    }

    class ViewHolder {
        TextView name, update_time, title, content, tag;
        ImageView pic;
    }

}
