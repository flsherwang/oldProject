package com.example.view;

import com.example.old.R;
import com.example.util.StringUtils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class LoadingDialog extends Dialog {

	public LoadingDialog(Context context) {
		super(context);
	}

	public LoadingDialog(Context context, int theme) {
		super(context, theme);
	}
	@SuppressLint({"Override","InflateParams"})
	public static LoadingDialog create(Context context, String str) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LoadingDialog dialog = new LoadingDialog(context, R.style.loadstyle);
		View layout = inflater.inflate(R.layout.loadding_dialog, null);
		dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		ProgressWheel probar = (ProgressWheel) layout.findViewById(R.id.load_probar);
		TextView protv = (TextView) layout.findViewById(R.id.load_tv);
		probar.spin();
		if (StringUtils.isNotEmpty(str)) {
			protv.setText(str);
		}else {
			protv.setVisibility(View.GONE);
		}
		dialog.setContentView(layout);
		dialog.setCanceledOnTouchOutside(false);
		return dialog;
	}
}
