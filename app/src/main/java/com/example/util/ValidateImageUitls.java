package com.example.util;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;

public class ValidateImageUitls {
	public static void getImage(Context context, final ImageView view, final EditText text) {
//		final String validateurl = StringUtils.getUrl(context, R.string.url_validate_code);
//		HttpSetting.instance(context);
//		HttpSetting.http.download(validateurl, OldApplication.getInstance().VALIDATE_IMG_PATH + "validate.jpg", false, true, new RequestCallBack<File>() {
//
//			@Override
//			public void onSuccess(ResponseInfo<File> responseInfo) {
//				LogUtils.d("下载成功,路径：" + responseInfo.result.getPath());
//				try {
//					Bitmap bitmap = BitmapFactory.decodeFile(responseInfo.result.getPath());
//					view.setImageBitmap(bitmap);
//				} catch (Exception e) {
//					LogUtils.d("装载本地图片异常", e);
//				}
//			}
//
//			@Override
//			public void onFailure(HttpException error, String msg) {
//				LogUtils.d("弹出异常", error);
//				LogUtils.d("异常信息：" + msg);
//				view.setImageResource(R.drawable.ic_launcher);
//			}
//
//			@Override
//			public void onStart() {
//				LogUtils.d("验证码图片url:" + validateurl);
//				view.setImageResource(R.drawable.ic_launcher);
//				text.setText("");
//			}
//		});
	}
}
