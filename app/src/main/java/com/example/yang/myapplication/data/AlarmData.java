package com.example.yang.myapplication.data;

import android.content.Context;

import com.example.yang.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang on 16/5/18.
 */
public class AlarmData {

    private String name;
    private String details;
    private int hour;
    private int minute;
    private boolean isVib;
    private String ringName;
    private RepeatType type;
    private boolean isOn;
    private boolean isRepeat;
    private int id;
    private List<Alarm> alarmList;


    public AlarmData(Context ctx, String name, String details, int hour, int minute, boolean isVib, String ringName, RepeatType type, boolean isOn, boolean isRepeat) {
        this.name = name;
        this.details = details;
        this.hour = hour;
        this.minute = minute;
        this.isVib = isVib;
        this.ringName = ringName;
        this.type = type;
        this.isOn = isOn;
        this.isRepeat = isRepeat;
        this.id = ctx.getSharedPreferences(ctx.getString(R.string.alarm_info), 0).getInt("id", 0);
        alarmList = new ArrayList<>();
        ctx.getSharedPreferences(ctx.getString(R.string.alarm_info), 0).edit().putInt("id", id + 1);
        ctx.getSharedPreferences(ctx.getString(R.string.alarm_info), 0).edit().commit();

        if (type.getType() == RepeatType.WEEKDAY) {
            for (int i = 0; i < type.getWeekDays().length; i++) {
                Alarm alarm = new Alarm().setId(id).setName(name).setDetails(details).setIsVib(isVib).setRingName(ringName)
                        .setWeek(type.getWeekDays()[i]).setHour(hour).setMinute(minute).create();
                alarmList.add(alarm);
            }
        } else {
            Alarm alarm = new Alarm().setId(id).setName(name).setDetails(details).setIsVib(isVib).setRingName(ringName)
                    .setYear(type.getYear()).setMonth(type.getMonth()).setDay(type.getDay()).setHour(hour).setMinute(minute).create();
            alarmList.add(alarm);
        }

    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setSwitch() {
        isOn = !isOn;
    }

    public boolean isOn() {
        return isOn;
    }


    public String getTypeStr() {

        String typeStr = "";

        if (type != null) {
            int typeInt = type.getType();
            switch (typeInt) {
                case RepeatType.EVERYDAY:
                    typeStr = "每天";
                    break;
                case RepeatType.WEEKDAY:
                    StringBuilder sb = new StringBuilder(typeStr);
                    if (type.getWeekDays() != null) {
                        for (WeekDay weekDay : type.getWeekDays()) {
                            sb.append(weekDay);
                            sb.append(" ");
                        }
                        return sb.toString();
                    }
                    break;
                case RepeatType.MONTHDAY:
                    typeStr = "每月" + type.getDay() + "日";
                    break;
                case RepeatType.YEARDAY:
                    typeStr = "每年" + type.getMonth() + "月" + type.getDay() + "日";
                    break;
                case RepeatType.ONEDAY:
                    typeStr = type.getYear() + "年" + type.getMonth() + "月" + type.getDay() + "日";
                    break;
            }
        }

        return typeStr;
    }

    public String getTimeStr() {
        String houStr = "";
        String minStr = "";
        if (this.hour < 10) {
            houStr = "0" + this.hour;
        }
        if (this.minute < 10) {
            minStr = "0" + this.minute;
        }
        return houStr + ":" + minStr;
    }

}
