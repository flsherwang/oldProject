package com.example.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

import com.example.application.OldApplication;
import com.example.old.R;
import com.lidroid.xutils.util.LogUtils;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !StringUtils.isEmpty(str);
    }

    public static String LowerString(String str) {//字母转换成小写
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= 'a' && str.charAt(i) <= 'z') {
                sb.append(str.substring(i, i + 1));
            } else {
                sb.append(str.substring(i, i + 1).toLowerCase(Locale.getDefault()));
            }
        }
        return sb.toString();
    }

    public static String htmlEscape(String input) {
        if (isEmpty(input)) {
            return input;
        }
        input = input.replaceAll("&", "&amp;");
        input = input.replaceAll("<", "&lt;");
        input = input.replaceAll(">", "&gt;");
        input = input.replaceAll(" ", "&nbsp;");
        input = input.replaceAll("'", "&#39;");
        input = input.replaceAll("\"", "&quot;");
        input = input.replaceAll("\n", "<br/>");
        return input;
    }

    public static String getUrl( Context context, int urlPrefixId, int urlId ){
        return String.format( OldApplication.getInstance().getLast().getResources().getString( urlId ), OldApplication.getInstance().getLast().getResources().getString( urlPrefixId ) );
    }

    public static String getUrl(  int urlPrefixId, int urlId ){
        return String.format( OldApplication.getInstance().getLast().getResources().getString( urlId ), OldApplication.getInstance().getLast().getResources().getString( urlPrefixId ) );
    }

//    public static String getUrl(Context context, int id) {
//        return String.format(OldApplication.getInstance().getLast().getResources().getString(id), OldApplication.getInstance().HostName);
//    }

//    public static String getUrl(Context context, String id, String i) {
//        return String.format(id, OldApplication.getInstance().HostName);
//    }

//    public static String getPayUrl(Context context, int id) {
//        return String.format(OldApplication.getInstance().getLast().getResources().getString(id), OldApplication.getInstance().PayHost);
//    }

//    public static String getUrl(Context context, String end) {
//        return PreferencesUtils.getString(context, "IMGAPATH", OldApplication.getInstance().IMAGE_URL_PATH) + end;
//    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getFromAssets(Context context, String fileName) {
        String Result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result;
    }

    public static String getbytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP).floatValue();
        if (returnValue > 1)
            return (returnValue + "MB");
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP).floatValue();
        return (returnValue + "KB");
    }

    public static String ToDBC(String input) {
        if (isEmpty(input)) {
            return null;
        }
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static String toUtf8(String str) {
        String result = null;
        try {
            result = new String(str.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LogUtils.d("转码失败", e);
        }
        return result;
    }

    // 获取meta 值
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return apiKey;
    }

 


   
    
}
