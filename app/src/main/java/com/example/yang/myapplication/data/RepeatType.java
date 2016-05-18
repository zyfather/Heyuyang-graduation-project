package com.example.yang.myapplication.data;

/**
 * Created by yang on 16/5/18.
 */
public class RepeatType {
    int year = -1;
    int month = -1;
    int day = -1;

    WeekDay[] weekDays;

    public RepeatType(int year, int month, int day) {
        year = year;
        month = month;
        day = day;
    }

    public RepeatType(WeekDay... weekDays) {

        this.weekDays = weekDays;

    }

    public int getYear(){
        return this.year;
    }
}
