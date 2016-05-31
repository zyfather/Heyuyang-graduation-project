package com.example.yang.myapplication.activity;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.data.AlarmData;

import java.io.IOException;

/**
 * 闹钟响铃界面
 */
public class WakeUpActivity extends Activity {

    TextView mTag;
    TextView mContent;
    MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_wake_up);

        initViews();

        AlarmData alarmData = (AlarmData) getIntent().getSerializableExtra("alarmData");
        if (alarmData != null) {
            mTag.setText(alarmData.getName());
            mContent.setText(alarmData.getDetails());
            //playRing(alarmData);
        }
        wakeUpAndUnlock(this);
        //AlarmUtil.windDown(this, alarmData);
    }

    private void playRing(AlarmData alarmData) {
        RingtoneManager manager = new RingtoneManager(WakeUpActivity.this);
        manager.setType(RingtoneManager.TYPE_ALARM);
        Uri ringUri = manager.getRingtoneUri(alarmData.getRing());

        mPlayer = MediaPlayer.create(WakeUpActivity.this, ringUri);
        try {
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Toast.makeText(WakeUpActivity.this, "ring error", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initViews() {
        mTag = (TextView) findViewById(R.id.wake_tag);
        mContent = (TextView) findViewById(R.id.wake_content);
    }

    public void close(View view) {

        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
            mPlayer.stop();
            mPlayer.release();
        }
        finish();
    }

    public static void wakeUpAndUnlock(Context context) {
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        //解锁
        kl.disableKeyguard();
        //获取电源管理器对象
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
        //点亮屏幕
        wl.acquire();
        //释放
        wl.release();
    }

}
