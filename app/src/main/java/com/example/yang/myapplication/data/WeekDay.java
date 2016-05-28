package com.example.yang.myapplication.data;

/**
 * Created by yang on 16/5/18.
 */
public enum WeekDay {
    MON, TUE, WEN, THR, FRI, SAT, SUN;

    @Override
    public String toString() {
        switch (this) {
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
            case SUN:
                return "星期日";
        }
        return "";
    }

}
