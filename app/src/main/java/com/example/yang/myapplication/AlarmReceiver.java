package com.example.yang.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.yang.myapplication.activity.WakeUpActivity;
import com.example.yang.myapplication.data.AlarmData;
import com.example.yang.myapplication.utils.AlarmUtil;

/**
 * Created by yang on 16/5/29.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        AlarmData alarmData = (AlarmData) intent.getSerializableExtra("alarmData");
        //月经连锁设置
        if (intent.getBooleanExtra("isMonthDay", false)) {
            AlarmUtil.windUp(context, alarmData);
        } else {

        }
        Intent wake = new Intent(context, WakeUpActivity.class);
        wake.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(wake);
    }
}
