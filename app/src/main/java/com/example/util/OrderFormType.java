package com.example.util;

import android.text.TextUtils;

/**
 * Created by Jookie on 2016/7/19.
 */
public class OrderFormType {//1福米兑换场，2签证，3活动
    public static Integer orderNumber(String orderNo) {
        if (!TextUtils.isEmpty(orderNo)) {
            Integer from = Integer.parseInt(orderNo.substring(0, 4)) - 1000;
            return from;
        }
        return 1;
    }
}
