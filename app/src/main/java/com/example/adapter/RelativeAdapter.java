package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.entity.User;
import com.example.old.R;

public class RelativeAdapter extends BaseAdapter {
    private Context context;
    private User user;

    public RelativeAdapter(Context context, User user) {
        // TODO Auto-generated method stub
        this.context = context;
        this.user = user;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (user != null) {
            if (user.getPerson() != null) {
                return user.getPerson().size();
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
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.relative_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.relative = (TextView) convertView.findViewById(R.id.relative);
            holder.focus = (TextView) convertView.findViewById(R.id.focus);
            holder.state_tv = (TextView) convertView.findViewById(R.id.state_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (user != null) {
            if (user.getPerson() != null) {
                User.Person person = user.getPerson().get(position);
                String nameStr = person.getNICK_NAME();
                String friendTypeName = person.getFRIEND_TYPE_NAME();
                int focusNum = person.getFOCUSNUM();
                int status = person.getSTATUS();
                holder.name.setText(nameStr);
                holder.relative.setText(friendTypeName);
                holder.focus.setText(focusNum + "关注");
                if (status == 1) {
                    holder.state_tv.setText("待邀请");
                    holder.state_tv.setTextColor(context.getResources().getColor(R.color.white));
                    holder.state_tv.setBackgroundResource(R.drawable.border_circular_two_bg_green_btn);
                } else if (status == 2) {
                    holder.state_tv.setText("邀请中");
                    holder.state_tv.setBackgroundResource(R.drawable.border_circular_two_bg_green_btn);
                    holder.state_tv.setTextColor(context.getResources().getColor(R.color.white));
                } else if (status == 3) {
                    holder.state_tv.setText("已关注");
                    holder.state_tv.setBackgroundResource(R.drawable.border_circular_two_bg_gray_btn);
                    holder.state_tv.setTextColor(context.getResources().getColor(R.color.black_light));
                }
            }
        }
        return convertView;
    }

    class ViewHolder {
        TextView name, relative, focus,state_tv;
    }
}
