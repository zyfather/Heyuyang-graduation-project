package com.example.yang.myapplication.data;

/**
 * Created by yang on 16/5/18.
 */
public enum WeekDay {
    MON, TUE, WEN, THR, FRI, SAT, SUN;

    public int getValue() {
        switch (this) {
            case SUN:
                return 1;
            case MON:
                return 2;
            case TUE:
                return 3;
            case WEN:
                return 4;
            case THR:
                return 5;
            case FRI:
                return 6;
            case SAT:
                return 7;
        }
        return 0;
    }

    @Override
    public String toString() {
        switch (this) {
            case SUN:
                return "星期日";
            case MON:
                return "星期一";
            case TUE:
                return "星期二";
            case WEN:
                return "星期三";
            case THR:
                return "星期四";
            case FRI:
                return "星期五";
            case SAT:
                return "星期六";
        }
        return "";
    }

}
