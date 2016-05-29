package com.example.yang.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.yang.myapplication.activity.WakeUpActivity;

/**
 * Created by yang on 16/5/29.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent wake = new Intent(context, WakeUpActivity.class);
        wake.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(wake);
        //Toast.makeText(context, "....", Toast.LENGTH_SHORT).show();
    }
}
