package com.example.yang.myapplication.data;

import java.io.Serializable;

/**
 * Created by yang on 16/5/18.
 */
public class AlarmData implements Serializable {

    private String name;
    private String details;
    private int hour;
    private int minute;
    private boolean isVib;
    private String ringName;
    private RepeatType repeatType;
    private boolean isOn;
    private boolean isRepeat;
    private int[] ids;
//    private List<Alarm> alarmList;

    public AlarmData(String name, String details, int hour, int minute, boolean isVib, String ringName, RepeatType repeatType, boolean isOn, boolean isRepeat, int... ids) {
        this.ids = ids;
        this.name = name;
        this.details = details;
        this.hour = hour;
        this.minute = minute;
        this.isVib = isVib;
        this.ringName = ringName;
        this.repeatType = repeatType;
        this.isOn = isOn;
        this.isRepeat = isRepeat;


//        if (repeatType.getType() == RepeatType.WEEKDAY) {
//            for (int i = 0; i < repeatType.getWeekDays().length; i++) {
//                Alarm alarm = new Alarm().setId(id).setName(name).setDetails(details).setIsVib(isVib).setRingName(ringName)
//                        .setWeek(repeatType.getWeekDays()[i]).setHour(hour).setMinute(minute).create();
//                alarmList.add(alarm);
//            }
//        } else {
//            Alarm alarm = new Alarm().setId(id).setName(name).setDetails(details).setIsVib(isVib).setRingName(ringName)
//                    .setYear(repeatType.getYear()).setMonth(repeatType.getMonth()).setDay(repeatType.getDay()).setHour(hour).setMinute(minute).create();
//            alarmList.add(alarm);
//        }

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isVib() {
        return isVib;
    }

    public void setIsVib(boolean isVib) {
        this.isVib = isVib;
    }

    public String getRingName() {
        return ringName;
    }

    public void setRingName(String ringName) {
        this.ringName = ringName;
    }

    public RepeatType getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(RepeatType repeatType) {
        this.repeatType = repeatType;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }

    public int[] getIds() {
        return ids;
    }

    public void setIds(int[] ids) {
        this.ids = ids;
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

        if (repeatType != null) {
            int typeInt = repeatType.getType();
            switch (typeInt) {
                case RepeatType.EVERYDAY:
                    typeStr = "每天";
                    break;
                case RepeatType.WEEKDAY:
                    StringBuilder sb = new StringBuilder(typeStr);
                    if (repeatType.getWeekDays() != null) {
                        for (WeekDay weekDay : repeatType.getWeekDays()) {
                            sb.append(weekDay);
                            sb.append(" ");
                        }
                        return sb.toString();
                    }
                    break;
                case RepeatType.MONTHDAY:
                    typeStr = "每月" + repeatType.getDay() + "日";
                    break;
                case RepeatType.YEARDAY:
                    typeStr = "每年" + repeatType.getMonth() + "月" + repeatType.getDay() + "日";
                    break;
                case RepeatType.ONEDAY:
                    typeStr = repeatType.getYear() + "年" + repeatType.getMonth() + "月" + repeatType.getDay() + "日";
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
        } else {
            houStr = "" + this.hour;
        }
        if (this.minute < 10) {
            minStr = "0" + this.minute;
        } else {
            minStr = "" + this.minute;
        }
        return houStr + ":" + minStr;
    }

}
