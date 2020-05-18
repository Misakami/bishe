package com.example.bishe.Util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.example.bishe.model.bean.Curriculum;
import com.example.bishe.model.bean.EventBean;

import java.util.Arrays;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CalendarProviderUtil {
    static String[] start = {"8.00","8.55","10.05","11.00","14.00","14.55","16.05","17.00","19.00","19.55","21.05","22.00"};
    static String[] end = {"8.45","9.40","10.50","11.45","14.45","15.40","16.50","17.45","19.45","20.40","21.50","22.45"};

    // ContentProvider的uri
    private static Uri calendarUri = CalendarContract.Calendars.CONTENT_URI;
    private static Uri eventUri = CalendarContract.Events.CONTENT_URI;
    private static Uri reminderUri = CalendarContract.Reminders.CONTENT_URI;

    private static ContentResolver contentResolver;

    /**
     * 检查是否有日历表,有返回日历id，没有-1
     * */
    private static int isHaveCalender() {
        @SuppressLint("MissingPermission") Cursor cursor = contentResolver.query(calendarUri, null, null, null, null);
        if (cursor == null || cursor.getCount() == 0){
            return -1;
        }else {
            // 如果有日历表
            try {
                cursor.moveToFirst();
                // 通过cursor返回日历表的第一行的属性值 第一个日历的id
                return cursor.getInt(cursor.getColumnIndex(CalendarContract.Calendars._ID));
            }finally {
                cursor.close();
            }
        }
    }

    /**
     * 添加日历表
     * */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static long addCalendar(){
        // 时区
        TimeZone timeZone = TimeZone.getDefault();
        // 配置Calendar
        ContentValues value = new ContentValues();
        value.put(CalendarContract.Calendars.NAME, "我的日历表");
        value.put(CalendarContract.Calendars.ACCOUNT_NAME, "myAccount");
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, "myType");
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "myDisplayName");
        value.put(CalendarContract.Calendars.VISIBLE, 1);
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE);
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, "myAccount");
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);
        value.put(CalendarContract.CALLER_IS_SYNCADAPTER,true);

        // 插入calendar
        @SuppressLint("MissingPermission") Uri insertCalendarUri = contentResolver.insert(calendarUri,value);

        if (insertCalendarUri == null){
            return -1;
        }else {
            // return Integer.parseInt(insertCalendarUri.toString());
            return ContentUris.parseId(insertCalendarUri);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean addCourse(Context context,Curriculum curriculum,String weakTime){
        String[] split = weakTime.split("-");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]) - 1;
        int day = Integer.parseInt(split[2]);
        int nstar = 0;
        int nend = 0;
        int[] section = curriculum.getSection();
        for (int i = 0; i < section.length; i++) {
            if (section[i] == 1){
                if (nstar == 0){
                    nstar = i;
                }else {
                    nend = i;
                }
            }
        }
        String starttime = start[nstar];
        String endtime = end[nend];
        Log.e(TAG, "addEvent: " + starttime + " " +endtime );
        String[] split1 = starttime.split("\\.");
        int starthour = Integer.parseInt(split1[0]);
        int startminute = Integer.parseInt(split1[1]);
        String[] split2 = endtime.split("\\.");
        int endhour = Integer.parseInt(split2[0]);
        int endminute = Integer.parseInt(split2[1]);

        // startMillis
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year,month,day,starthour,startminute);
        long startMillis = beginTime.getTimeInMillis();
        Log.e(TAG, "addEvent: " + startMillis);
        // endMillis
        Calendar endTime = Calendar.getInstance();
        endTime.set(year,month,day,endhour,endminute);
        long endMillis = endTime.getTimeInMillis();
        return addCalender(context,startMillis,endMillis,curriculum.getTitle(),curriculum.getContent());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean addEvent(Context context, EventBean.ReturnDataBean rd){
        int year,month,day,hour,minute;
        String startTime = rd.getStarttime();
        String[] split = startTime.split("-");
        year = Integer.parseInt(split[0]);
        month = Integer.parseInt(split[1]) - 1;
        day = Integer.parseInt(split[2]);
        hour = Integer.parseInt(split[3]);
        minute = Integer.parseInt(split[4]);
        // startMillis
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year,month,day,hour,minute);
        long startMillis = beginTime.getTimeInMillis();
        Log.e(TAG, "addEvent: " + startMillis);

        String[] endtime = rd.getEndtime().split("-");
        year = Integer.parseInt(endtime[0]);
        month = Integer.parseInt(endtime[1]) - 1;
        day = Integer.parseInt(endtime[2]);
        hour = Integer.parseInt(endtime[3]);
        minute = Integer.parseInt(endtime[4]);
        // endMillis
        Calendar endTime = Calendar.getInstance();
        endTime.set(year,month,day,hour,minute);
        long endMillis = endTime.getTimeInMillis();
        return addCalender(context,startMillis,endMillis,rd.getTitle(),rd.getContent());
    }

    /**
     *  添加日历事件
     * */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static boolean addCalender(Context context,long startMillis,long endMillis,String title,String content){

        // 创建contentResolver
        contentResolver = context.getContentResolver();

        // 日历表id
        int calendarId = isHaveCalender();
        if (calendarId == -1){
            addCalendar();
            calendarId = isHaveCalender();
        }


        // 准备event
        ContentValues valueEvent = new ContentValues();
        valueEvent.put(CalendarContract.Events.DTSTART,startMillis);
        valueEvent.put(CalendarContract.Events.DTEND,endMillis);
        valueEvent.put(CalendarContract.Events.TITLE,title);
        valueEvent.put(CalendarContract.Events.DESCRIPTION,content);
        valueEvent.put(CalendarContract.Events.CALENDAR_ID,calendarId);
        valueEvent.put(CalendarContract.Events.EVENT_TIMEZONE,"Asia/Shanghai");

        // 添加event
        @SuppressLint("MissingPermission") Uri insertEventUri = contentResolver.insert(eventUri,valueEvent);
        if (insertEventUri == null){
            Toast.makeText(context,"添加event失败",Toast.LENGTH_SHORT).show();
            return false;
        }

        // 添加提醒
        long eventId = ContentUris.parseId(insertEventUri);
        ContentValues valueReminder = new ContentValues();
        valueReminder.put(CalendarContract.Reminders.EVENT_ID,eventId);
        valueReminder.put(CalendarContract.Reminders.MINUTES,15);
        valueReminder.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT );
        @SuppressLint("MissingPermission") Uri insertReminderUri = contentResolver.insert(reminderUri,valueReminder);
        if (insertReminderUri == null){
            Toast.makeText(context,"添加reminder失败",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}