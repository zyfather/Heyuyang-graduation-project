package com.example.yang.myapplication.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.yang.myapplication.AlarmReceiver;
import com.example.yang.myapplication.R;
import com.example.yang.myapplication.data.AlarmData;
import com.example.yang.myapplication.data.RepeatType;
import com.example.yang.myapplication.data.WeekDay;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yang on 16/5/29.
 */
public class AlarmUtil {


    /**
     * 保存AlarmDatas to json
     */
    public static void saveAlarm(Context context, AlarmData... alarmDatas) {
        if (alarmDatas == null || alarmDatas.length == 0) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.alarm_info), Context.MODE_APPEND);
        List<AlarmData> alarmDataList = getAlarms(context);
        if (alarmDataList == null) {
            alarmDataList = new ArrayList<>();
        }
        SharedPreferences.Editor ed = sp.edit();
        for (AlarmData data : alarmDatas) {
            alarmDataList.add(data);
            windUp(context, data);
        }
        String dataStr = new Gson().toJson(alarmDataList);
        ed.putString("alarmsJson", dataStr);
        ed.commit();
    }

    /**
     * 替换AlarmDatas
     */
    public static void replaceAlarm(Context context, List<AlarmData> alarmDataList) {

        if (alarmDataList == null) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.alarm_info), Context.MODE_APPEND);
        SharedPreferences.Editor ed = sp.edit();
        String dataStr = new Gson().toJson(alarmDataList);
        ed.putString("alarmsJson", dataStr);
        ed.commit();

    }

    /**
     * 删除AlarmDatas
     */
    public static void deleteAlarm(Context context, AlarmData... alarmDatas) {
        List<AlarmData> localList = getAlarms(context);
        for (AlarmData alarmData : alarmDatas) {
            int i = 0;
            for (AlarmData localData : localList) {
                if (localData.getIds()[0] == alarmData.getIds()[0]) {
                    localList.remove(i);
                    windDown(context, localData);
                }
                i++;
            }
        }
        replaceAlarm(context, localList);
    }


    /**
     * 读取AlarmDatas from json
     *
     * @param context
     * @return
     */
    public static List<AlarmData> getAlarms(Context context) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.alarm_info), Context.MODE_APPEND);
        String alarmsJson = sp.getString("alarmsJson", null);
        Type collectionType = new TypeToken<ArrayList<AlarmData>>() {
        }.getType();
        return new Gson().fromJson(alarmsJson, collectionType);
    }

    /**
     * 上发条
     *
     * @param alarmData
     */
    public static void windUp(Context ctx, AlarmData alarmData) {

        if (alarmData == null) {
            return;
        }

        int[] ids = alarmData.getIds();

        int INTERVAL;

        Intent intent = new Intent(ctx, AlarmReceiver.class);

        for (int id : ids) {

            PendingIntent sender = PendingIntent.getBroadcast(
                    ctx, id, intent, 0);

            AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

            Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//            calendar.add(Calendar.MILLISECOND, 3);

            int hou = alarmData.getHour();
            int min = alarmData.getMinute();

            switch (alarmData.getRepeatType().getType()) {
                case RepeatType.EVERYDAY:
                    calendar.set(Calendar.HOUR_OF_DAY, hou);
                    calendar.set(Calendar.MINUTE, min);
                    INTERVAL = 1000 * 60 * 60 * 24;// 24h
                    intent.putExtra("alarmData", alarmData);
                    sender = PendingIntent.getBroadcast(
                            ctx, id, intent, 0);
                    am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), INTERVAL, sender);
                    break;
                case RepeatType.WEEKDAY:
                    for (WeekDay weekDay : alarmData.getRepeatType().getWeekDays()) {
                        calendar.set(Calendar.DAY_OF_WEEK, weekDay.getValue());
                        calendar.set(Calendar.HOUR, hou);
                        calendar.set(Calendar.MINUTE, min);
                        INTERVAL = 1000 * 60 * 60 * 24 * 7;// 7days
                        intent.putExtra("alarmData", alarmData);
                        sender = PendingIntent.getBroadcast(
                                ctx, id, intent, 0);
                        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), INTERVAL, sender);
                    }
                    break;
                case RepeatType.MONTHDAY:
                    calendar.set(Calendar.MONTH, calendar.getTime().getMonth() + 1);
                    calendar.set(Calendar.DATE, alarmData.getRepeatType().getDay());
                    calendar.set(Calendar.HOUR, hou);
                    calendar.set(Calendar.MINUTE, min);

                    intent.putExtra("isMonthDay", true);
                    intent.putExtra("alarmData", alarmData);
                    sender = PendingIntent.getBroadcast(
                            ctx, id, intent, 0);
                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                    break;
                case RepeatType.YEARDAY:
                    calendar.set(Calendar.DAY_OF_YEAR, alarmData.getRepeatType().getDay());
                    calendar.set(Calendar.HOUR, hou);
                    calendar.set(Calendar.MINUTE, min);
                    INTERVAL = 1000 * 60 * 60 * 24 * 365;// 365days
                    intent.putExtra("alarmData", alarmData);
                    sender = PendingIntent.getBroadcast(
                            ctx, id, intent, 0);
                    am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), INTERVAL, sender);
                    break;
                case RepeatType.ONEDAY:
                    calendar.set(alarmData.getRepeatType().getYear(), alarmData.getRepeatType().getMonth(), alarmData.getRepeatType().getDay());
                    calendar.set(Calendar.HOUR, hou);
                    calendar.set(Calendar.MINUTE, min);
                    intent.putExtra("alarmData", alarmData);
                    sender = PendingIntent.getBroadcast(
                            ctx, id, intent, 0);
                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                    break;

            }
        }


    }

    /**
     * 卸发条
     *
     * @param ctx
     * @param alarmData
     */
    public static void windDown(Context ctx, AlarmData alarmData) {

        if (alarmData == null) {
            return;
        }
        Intent intent = new Intent(ctx,
                AlarmReceiver.class);
        for (int id : alarmData.getIds()) {
            PendingIntent sender = PendingIntent.getBroadcast(
                    ctx, id, intent, 0);
            AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
            am.cancel(sender);
        }

        for (AlarmData localData : getAlarms(ctx)) {
            if (localData.getIds()[0] == alarmData.getIds()[0]) {
                localData.setSwitch();
            }
        }
    }


}
