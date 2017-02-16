package com.example.view;

import com.example.old.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * 自定义对话框（视图）
 * @author Jiang yongqiang 
 *
 */
public class CustomViewDialog {

	private  Dialog dialog;
	private Activity context;
	private Context c;
	private View pView;
	public CustomViewDialog(Activity context, View pView) {
		 c = new ContextThemeWrapper(context,
				R.style.custom_dialog);
		this.context = context;
		this.pView = pView;
	}
	public void show(){
		dialog = new Dialog(c);
		dialog.setTitle("");
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		if(context!=null&&!context.isFinishing()){
			dialog.show();
			final Window window = dialog.getWindow();
			final WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数�?

			final WindowManager m = context.getWindowManager();
			final Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
			p.height = WindowManager.LayoutParams.WRAP_CONTENT;
			p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.8
			window.setAttributes(p);
			//设置对话框背景空，否则会出现上下边框有黑色的空隙
			window.setBackgroundDrawable(new ColorDrawable(0));
			window.setContentView(pView);
			Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
			pView.startAnimation(animation);
		}
	}
	
	public Dialog getDialog(){
		return dialog;
	}
    public void close(){
		/*Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
		pView.startAnimation(animation);*/
    	dialog.dismiss();
    }
}
