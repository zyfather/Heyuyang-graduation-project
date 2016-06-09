package com.example.yang.myapplication.data;

import java.io.Serializable;

/**
 * Created by yang on 16/5/18.
 */
public class RepeatType implements Serializable {

    public final static int EVERYDAY = 0x1;
    public final static int WEEKDAY = 0x2;
    public final static int MONTHDAY = 0x3;
    public final static int YEARDAY = 0x4;
    public final static int ONEDAY = 0x5;
    public final static int INTERVALDAY = 0x6;
    public final static int INTERVALHOUR = 0x7;
    public final static int INTERVALMIN = 0x8;

    private int year = -1;
    private int month = -1;
    private int day = -1;
    private int dInterval = -1;
    private int hInterval = -1;
    private int mInterval = -1;
    private int iDay = 0;

    private WeekDay[] weekDays = null;

    public RepeatType(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public RepeatType(WeekDay... weekDays) {

        this.weekDays = weekDays;

    }

    public RepeatType(int dInterval, int hInterval, int mInterval, int iDay){
        this.dInterval = dInterval;
        this.hInterval = hInterval;
        this.mInterval = mInterval;
        this.iDay = iDay;
    }

    public int getType() {
        int type = EVERYDAY;
        if (mInterval == -1) {
            if (hInterval == -1) {
                if (dInterval == -1) {
                    if (year == -1) {
                        if (month == -1) {
                            if (day == -1) {
                                if (weekDays != null && weekDays.length > 0 && weekDays.length != 7) {
                                    type = WEEKDAY;
                                }
                            } else {
                                type = MONTHDAY;
                            }
                        } else {
                            type = YEARDAY;
                        }
                    } else {
                        type = ONEDAY;
                    }
                } else {
                    type = INTERVALDAY;
                }
            } else {
                type = INTERVALHOUR;
            }
        }else {
            type = INTERVALMIN;
        }
        return type;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setWeekDays(WeekDay[] weekDays) {
        this.weekDays = weekDays;
    }

    public void setdInterval(int dInterval){
        this.dInterval = dInterval;
    }

    public void sethInterval(int hInterval){
        this.hInterval = hInterval;
    }

    public void setmInterval(int hInterval){
        this.mInterval = mInterval;
    }

    public void setiDay(int iDay){
        this.iDay = iDay;
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public WeekDay[] getWeekDays() {
        return weekDays;
    }

    public int getdInterval(){ return dInterval; }

    public int gethInterval(){ return hInterval; }

    public int getmInterval(){ return mInterval; }

    public int getiDay(){ return iDay; }

    public int[] getWeekValue() {
        int[] weekArray = new int[weekDays.length];
        for (int i = 0; i < weekArray.length; i++) {
            weekArray[i] = weekDays[i].getValue();
        }
        return weekArray;
    }
}
