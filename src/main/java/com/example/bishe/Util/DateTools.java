package com.example.bishe.Util;


import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DateTools {

   public static boolean compareTime(String time1,String time2){
        SimpleDateFormat sdf  =new SimpleDateFormat("yyyy-MM-dd"); //创建日期转换对象：年 月 日
        try {
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            boolean flag = date1.getTime() >= date2.getTime();
            return true;
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return false;
    }

    public static Date getNowTime(){
        Date date = new Date(System.currentTimeMillis());
        return date;
    }

    //推算周几
    public static int dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return (w+7)%8-1;
    }

    //推算周几
    public static int dateToWeek(Date datetime) {
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        cal.setTime(datetime);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w <= 0)
            w = 7;
        Log.e(TAG, "dateToWeek: " + w );
        return (w+7)%8-1;
    }


    //拿到每天的日期
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getWeakTime(String Date, int day){
        Calendar cld = Calendar.getInstance();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        try {
            cld.setTime(Objects.requireNonNull(formatter.parse(Date)));
            cld.add(cld.DATE,day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter.format(cld.getTime());
    }

    public static int getWeak(String startTime){
        long nd = 1000 * 24 * 60 * 60;
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = formatter.parse(startTime);
            Date nowTime = getNowTime();
            long diff = nowTime.getTime() - start.getTime();
            int day = (int) (diff / nd)/7;
            return day;
        } catch (ParseException e) {
            Log.e(TAG, "getWeak: " + e.getMessage() );
        }
        return -1;
    }
}
