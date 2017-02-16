package com.example.util;

import com.example.fragment.BaseFragment;
import com.example.old.LoginActivity;
import com.example.old.R;
import com.lidroid.xutils.http.RequestParams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class UnauthorizedUtils {

    /**
     * 检测是否登录
     *
     * @param activity
     */
    public static boolean checkIsLogined(Context activity, boolean needToLogin) {
        String userUid = PreferencesUtils.getString(activity, "UserUid");
        String mobile = PreferencesUtils.getString(activity, "mobile");
        String LoginCode = PreferencesUtils.getString(activity, "LoginCode");
        if (StringUtils.isEmpty(userUid) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(LoginCode)) {
            if (needToLogin) {
                activity.startActivity(new Intent(activity, LoginActivity.class));
            }
            return false;
        }
        return true;
    }

    public static boolean checkIsLogined(Context activity) {
        return checkIsLogined(activity, true);
    }

    public static void handleActivityUnauthorized(final Activity activity) {
//        RequestParams params = new RequestParams();
//        params.addQueryStringParameter("login", PreferencesUtils.getString(activity, "Account", ""));
//        //db19406608db4fac84d5cba4d94d86db
//        //9a01256979e54441a3ca66e8c1667f26
//        params.addQueryStringParameter("loginCode", PreferencesUtils.getString(activity, "LoginCode", ""));
//
//        HttpGetDataUtil.getActivityData(activity, params, R.string.url_prefix_common, R.string.url_login_code, true, "登录过期，重新登录中...", new BaseRequestRecall() {
//            @Override
//            public void resultSuccess(Object msg) {
//                Toast.makeText(activity, "登录成功，请刷新页面！", Toast.LENGTH_LONG).show();
//				activity.sendBroadcast(new Intent().setAction("userdologin"));
////                activity.startActivity(new Intent(activity, MainActivity.class));
//            }
//
//            @Override
//            public void requestFailed(Object msg) {
//                super.requestFailed(msg);
////				activity.showToast(msg.toString());
////				WelfareApplication.getInstance().finishOthers(activity.getClass());
//                Intent intent = new Intent(activity, LoginActivity.class);
//                AnimationUtil.startActivity(activity, intent);
//            }
//
//            @Override
//            public void resultFailed(Object msg) {
//                super.resultFailed(msg);
////				activity.showToast(msg.toString());
////				WelfareApplication.getInstance().finishOthers(activity.getClass());
//                Intent intent = new Intent(activity, LoginActivity.class);
//                AnimationUtil.startActivity(activity, intent);
//            }
//
//        });
    }

    public static void handleFragmentUnauthorized(final BaseFragment fragment) {
        //modify:jookie   之前复杂了
        handleActivityUnauthorized(fragment.getActivity());

    }
}
