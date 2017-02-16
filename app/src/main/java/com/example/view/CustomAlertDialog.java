package com.example.view;

import com.example.old.R;
import com.example.util.PreferencesUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Jookie.
 *
 * @date 2015/9/16
 * @Description: TODO(自定义警告框)
 */

public class CustomAlertDialog {
	private Activity context;
	private Button cancel_btn;
	private Button neutral_btn;
	private Button confirm_btn;
	private TextView message;
	private View.OnClickListener outPositiveClick;
	private View.OnClickListener outNegativeClick;
	private CustomViewDialog viewDialog;
	private View tips_wrapper;
	private CheckBox checkBox;
	private boolean once = false;
	private String key;
	private View layout;

	public CustomAlertDialog(Activity c) {
		this.context = c;
		layout = context.getLayoutInflater().inflate(R.layout.customdialog, null);
		viewDialog = new CustomViewDialog(context, layout);
		cancel_btn = (Button) layout.findViewById(R.id.cancel_btn);
		neutral_btn = (Button) layout.findViewById(R.id.neutral_btn);
		neutral_btn.setVisibility(View.GONE);
		confirm_btn = (Button) layout.findViewById(R.id.confirm_btn);
		tips_wrapper = layout.findViewById(R.id.tips_wrapper);
		confirm_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (tips_wrapper.getVisibility() == View.VISIBLE) {// 处理下次不再显示
					if (once && checkBox != null && checkBox.isChecked()) {
						PreferencesUtils.putBoolean(context, key, true);
					}
				}
				viewDialog.close();
				if (outPositiveClick != null) {
					outPositiveClick.onClick(view);
				}
			}
		});
		cancel_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				viewDialog.close();
				if (outNegativeClick != null) {
					outNegativeClick.onClick(view);
				}
			}
		});
	}

	@SuppressLint("InflateParams")
	public void show(String msg) {
		message = (TextView) layout.findViewById(R.id.message);
		message.setText(msg);
		viewDialog.show();
	}

	public void setNegativeText(String negativeText) {
		cancel_btn.setText(negativeText);
	}

	public void setPositiveText(String positiveText) {
		confirm_btn.setText(positiveText);
	}

	public void hiddenCancel() {
		cancel_btn.setVisibility(View.GONE);
	}

	public CustomAlertDialog setPositive(final View.OnClickListener onClickListener) {
		this.outPositiveClick = onClickListener;
		return this;
	}

	public void dismiss() {
		viewDialog.close();
	}

	public void setNegative(final View.OnClickListener click) {
		this.outNegativeClick = click;
	}

	/**
	 * 设置提示选项
	 * 
	 * @param key
	 *            关键字
	 * @param content
	 *            内容
	 * @param once
	 *            是否是只显示一次
	 */
	public CustomAlertDialog setTipsContent(String key, String content, boolean once) {
		this.once = once;
		this.key = key;
		tips_wrapper.setVisibility(View.VISIBLE);
		TextView tips_content = (TextView) tips_wrapper.findViewById(R.id.tips_content);
		tips_content.setText(content);
		checkBox = (CheckBox) tips_wrapper.findViewById(R.id.protocol);
		return this;
	}
}
