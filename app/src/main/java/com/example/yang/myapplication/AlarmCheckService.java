package com.example.yang.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.yang.myapplication.activity.WakeUpActivity;
import com.example.yang.myapplication.data.AlarmData;
import com.example.yang.myapplication.utils.AlarmUtil;

import java.util.List;

/**
 * Created by yang on 16/5/29.
 * 后台检查闹钟service
 */
public class AlarmCheckService extends Service {

    private Intent wakeUp;
    private Thread checkThread;
    private boolean flag;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        wakeUp = new Intent(this, WakeUpActivity.class);
        wakeUp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        final Context ctx = this;

        flag = true;
        checkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    List<AlarmData> alarmDataList = AlarmUtil.getAlarms(ctx);



                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        checkThread.start();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = false;
        stopForeground(true);
    }
}
