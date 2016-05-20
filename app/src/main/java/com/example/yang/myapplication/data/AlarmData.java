package com.example.yang.myapplication.data;

import android.content.Context;

/**
 * Created by yang on 16/5/18.
 */
public class AlarmData {

    String name;
    String details;
    int hour;
    int minute;
    boolean isVib;
    String ringName;
    RepeatType type;
    boolean isOn;
    boolean isRepeat;
    int id;

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
        this.id = ctx.getSharedPreferences("id", 0).getInt("id", 0);
        ctx.getSharedPreferences("id",0).edit().putInt("id",id+1);
        ctx.getSharedPreferences("id",0).edit().commit();

        if (this.type.getYear() == -1) {

            for (int i = 0; i < type.weekDays.length; i++) {
                Alarm alarm = new Alarm().setId(id).setName(name).setDetails(details).setIsVib(isVib).setRingName(ringName)
                        .setWeek(type.weekDays[i]).setHour(hour).setMinute(minute).create();
            }
        }
        else {
            Alarm alarm = new Alarm().setId(id).setName(name).setDetails(details).setIsVib(isVib).setRingName(ringName)
                    .setYear(type.year).setMonth(type.month).setDay(type.day).setHour(hour).setMinute(minute).create();
        }

        //TODO: 添加到本地json文件
    }

    public String getName(){
        return name;
    }
    public String getDetails(){
        return details;
    }
    public int getHour(){
        return hour;
    }
    public int getMinute(){
        return minute;
    }
    public  String getRepeatType(){
        if (type.getYear() == -1) {
            String weekdays = "";
            for (int i = 0; i < type.weekDays.length; i++){
                weekdays = weekdays + type.weekDays[i] + ",";
            }
            weekdays.substring(0,weekdays.length()-1);
            return weekdays;
        }
        else {
            String days = "";
            days = type.year + "年" + type.month + "月" + type.day + "日";
            return days;
        }
    }
    public void setOn(){

        isOn = !isOn;
    }
}
