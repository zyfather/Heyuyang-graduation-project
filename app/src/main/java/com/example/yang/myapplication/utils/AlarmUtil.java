package com.example.yang.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.data.AlarmData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang on 16/5/29.
 */
public class AlarmUtil {

    /**
     * 保存AlarmDatas to json
     */
    public static void saveAlarm(AlarmData alarmData, Context context) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.alarm_info), Context.MODE_APPEND);
        sp.edit().putString("alarmsJson", new Gson().toJson(getAlarms(context).add(alarmData)));
    }

    /**
     * 读取AlarmDatas form json
     * @param context
     * @return
     */
    public static List<AlarmData> getAlarms(Context context) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.alarm_info), Context.MODE_APPEND);
        String alarmsJson = sp.getString("alarmsJon", "");
        Type collectionType = new TypeToken<ArrayList<AlarmData>>() {
        }.getType();
        return new Gson().fromJson(alarmsJson, collectionType);
    }
}
