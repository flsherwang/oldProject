package com.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 时间戳转换成字符串
     */
    public static String getDateToString(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = new Date(time);
        return sf.format(d);
    }

    /**
     * 时间戳转换成年月
     */
    public static String getDateToS(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("MM月dd日");
        Date d = new Date(time);
        return sf.format(d);
    }

    /**
     * 时间戳转换成年月日时分秒
     */
    public static String LongToString(long time) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 时间戳转换成字销售分钟字符串
     */
    public static String getDateToHHmm(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
        Date d = new Date(time);
        return sf.format(d);
    }

    /**
     * 将字符串格式为yyyy-MM-dd HH:mm:ss的日期转化为long
     *
     * @param time
     * @return
     */
    public static long getLong(String time) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static String LongFormatString(long time) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static long DateToLong(String time) {
        long t = 0;
        String dateType = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateType);
        try {
            Date date = sdf.parse(time);
            t = date.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return t;
    }

    public static String changeFormat(String time) {
        SimpleDateFormat sdfS = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sdfD = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date date = null;
        try {
            date = sdfS.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (date == null) {
            return null;
        }
        return sdfD.format(date);

    }

    /**
     * 根据用户生日计算年龄
     */
    public static int getAgeByBirthday(Date birthday) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }

    /**
     * 时间段判断
     */
    public static String getTimeGap(long time) {
        String timeGaoStr = "";
        long timeL = time - System.currentTimeMillis();
        if (0 < timeL && timeL < 86400) {
            timeGaoStr = "1天前更新了故事";
        } else if (timeL > 86400 && timeL < 172800) {
            timeGaoStr = "2天前更新了故事";
        } else if (timeL > 172800 && timeL < 604800) {
            timeGaoStr = "一周前更新了故事";
        } else if (timeL > 604800 && timeL < 2592000) {
            timeGaoStr = "一月前更新了故事";
        } else if (timeL > 2592000) {
            timeGaoStr = "你已经很久没有更新故事了";
        }
        return timeGaoStr;
    }
}
