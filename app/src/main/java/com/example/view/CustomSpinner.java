package com.example.view;

import java.util.List;

import com.example.old.R;
import com.lidroid.xutils.util.LogUtils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 自定义Spinner
 * @author Jookie  http://www.coding84.com
 * 2015年12月11日 13:52:12
 */
public class CustomSpinner extends RelativeLayout {
    private TextView label;
    private Context context;
    private List<String> items;
    private  ArrayAdapter<String> adapter;
    private PopupWindow popupWindow;//弹出框
    private OnSpinnerSelect listener;//监听器

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(context).inflate(R.layout.view_custom_spinner, this);
        label = (TextView) rootView.findViewById(R.id.name);
        label.setText("全部");
        label.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.d("popupWindow:" + popupWindow);
                if (popupWindow == null) {//如果为空就新创建一个
                    View popView = LayoutInflater.from(context).inflate(R.layout.view_custom_spinner_pop, null);
                    adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, items);
                    ListView listView = (ListView) popView.findViewById(R.id.listview);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (popupWindow != null && popupWindow.isShowing()) {
                                popupWindow.dismiss();
                                changeLabelStatus();
                            }
                            LogUtils.d("setOnItemClickListener:"+position+",listener:"+listener);
                            if (listener != null) {
                                listener.onSelect(position);
                            }
                        }
                    });
                    //搞不明白这里为什么会有获取listView为空的状况,需要搞清楚是从哪里进来的时候产生的
                    listView.setAdapter(adapter);
                    popupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            changeLabelStatus();
                        }
                    });
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());//必须设置，不然点击空白处不会关闭popup
                    popupWindow.getBackground().setAlpha(100);//透明度
                    popupWindow.setAnimationStyle(android.R.anim.fade_in);//设置动画效果，当然也可以自定义
                    popupWindow.setFocusable(true);
                    popupWindow.showAsDropDown(CustomSpinner.this, 0, 0);
                } else {
                    popupWindow.showAsDropDown(CustomSpinner.this, 0, 0);
                }
                changeLabelStatus();

            }
        });
    }
    /**
     *  设置标签
     *  t 文字
     */
    public void setText(String t){
        label.setText(t);
    }

    /**
     * 设置数据
     * @param items 数据
     */
    public void setItems(List<String> items) {
        this.items = items;
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }

    }

    /**
     * 设置监听器
     * @param listener
     */
    public void setOnSpinnerSelect(OnSpinnerSelect listener){
        this.listener = listener;
    }
    /**
     * 改变标签的状态
     */
    private void changeLabelStatus(){
        if(popupWindow!=null&&popupWindow.isShowing()){//展开状态
            label.setTextColor(context.getResources().getColor(R.color.font_orange));
            Drawable mipmap = getResources().getDrawable(R.mipmap.arrow_select_on);
            if(mipmap != null){
                mipmap.setBounds(0, 0, mipmap.getMinimumWidth(), mipmap.getMinimumHeight());
            }

            label.setCompoundDrawables(null, null, mipmap, null);
        } else{
            label.setTextColor(context.getResources().getColor(R.color.black));
            Drawable mipmap = getResources().getDrawable(R.mipmap.arrow_select_down);
            if(mipmap != null){
                mipmap.setBounds(0, 0, mipmap.getMinimumWidth(), mipmap.getMinimumHeight());
            }
            label.setCompoundDrawables(null, null, mipmap, null);
        }
    }

    /**
     * 监听器
     */
    public interface OnSpinnerSelect{
        void onSelect(int idx);
    }
}
