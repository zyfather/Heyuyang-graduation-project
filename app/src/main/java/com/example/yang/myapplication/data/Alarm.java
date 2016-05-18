package com.example.yang.myapplication.data;

/**
 * Created by yang on 16/5/18.
 */
public class Alarm {

    int id;
    String name;
    String details;
    int year = 0;
    int month = 0;
    int day = 0;
    int hour = 0;
    int minute = 0;
    WeekDay week = null;
    boolean isRepeat = true;
    boolean isOn = true;
    boolean isVib = true;
    String ringName = "null";



    public Alarm create() {
        //TODO: 绑定service与添加到本地json文件
        return this;
    }


    public Alarm setId(int id) {
        this.id = id;
        return this;
    }
    public Alarm setName(String name) {
        this.name = name;
        return this;
    }
    public Alarm setDetails(String details) {
        this.details = details;
        return this;
    }

    public Alarm setYear(int year) {
        this.year = year;
        return this;
    }

    public Alarm setMonth(int month) {
        this.month = month;
        return this;
    }

    public Alarm setDay(int day) {
        this.day = day;
        return this;
    }

    public Alarm setHour(int hour) {
        this.hour = hour;
        return this;
    }

    public Alarm setMinute(int minute) {
        this.minute = minute;
        return this;
    }

    public Alarm setWeek(WeekDay week) {
        this.week = week;
        return this;
    }

    public Alarm setIsRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
        return this;
    }

    public Alarm setIsOn(boolean isOn) {
        this.isOn = isOn;
        return this;
    }

    public Alarm setIsVib(boolean isVib) {
        this.isVib = isVib;
        return this;
    }

    public Alarm setRingName(String ringName) {
        this.ringName = ringName;
        return this;
    }
}
