package com.example.view;

import com.example.old.R;
import com.example.util.StringUtils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class DownloadDialog {

	private Dialog download = null;
	public NumberProgressBar bar;
	private TextView tsizetv;
	private TextView dsizetv;

	@SuppressLint("InflateParams")
	public Dialog setDialog(final Context context) {
		if (download == null) {
			download = new Dialog(context, R.style.mystyle);
			View dialogView = LayoutInflater.from(context).inflate(R.layout.downloaddialog, null);
			download.setCanceledOnTouchOutside(false);
			download.setCancelable(true);
			download.setContentView(dialogView);
			Window dialogWindow = download.getWindow();
			dialogWindow.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
			// WindowManager.LayoutParams lp = dialogWindow.getAttributes();
			// DisplayMetrics metrics =
			// context.getApplicationContext().getResources().getDisplayMetrics();
			// lp.width = metrics.widthPixels * 5 / 6;
			// lp.height = metrics.heightPixels * 3 / 5;
			// dialogWindow.setAttributes(lp);

			bar = (NumberProgressBar) dialogView.findViewById(R.id.download_bar);
			bar.setMax(100);

			tsizetv = (TextView) dialogView.findViewById(R.id.total_size);
			dsizetv = (TextView) dialogView.findViewById(R.id.download_size);
		}
		return download;
	}

	public void setProgress(int pro) {
		bar.setProgress(pro);
	}

	public void setSize(long dszie, long tsize) {
		dsizetv.setText("已下载：" + StringUtils.getbytes2kb(dszie));
		tsizetv.setText("总大小：" + StringUtils.getbytes2kb(tsize));
	}

	public void dismiss() {
		download.dismiss();
	}
}
