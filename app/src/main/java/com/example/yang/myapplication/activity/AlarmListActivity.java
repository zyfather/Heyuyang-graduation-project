package com.example.yang.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.adapter.AlarmAdapter;
import com.example.yang.myapplication.data.AlarmData;
import com.example.yang.myapplication.utils.AlarmUtil;

import java.util.Arrays;
import java.util.List;

/**
 * 首页闹钟列表
 */
public class AlarmListActivity extends Activity {

    RecyclerView mAlarmList;
    AlarmAdapter mAdapter;

    TextView aboutTv;
    TextView middleTv;
    ImageView addIv;
    TextView rightTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_alarm_list);
        initViews();
    }

    public void initViews() {
        aboutTv = (TextView) findViewById(R.id.title_left_tv);
        middleTv = (TextView) findViewById(R.id.title_middle_tv);
        addIv = (ImageView) findViewById(R.id.title_right_iv);
        rightTv = (TextView) findViewById(R.id.title_right_tv);
        mAlarmList = (RecyclerView) findViewById(R.id.alarm_list);
        mAlarmList.setLayoutManager(new LinearLayoutManager(this));

        aboutTv.setText(R.string.about);
        middleTv.setText(R.string.alarm);
        addIv.setVisibility(View.VISIBLE);
        rightTv.setVisibility(View.GONE);
        findViewById(R.id.title_right_tv).setVisibility(View.GONE);

        aboutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences sp = getSharedPreferences(getString(R.string.alarm_info), Context.MODE_APPEND);
                String alarmsJson = sp.getString("alarmsJson", null);

                if (alarmsJson != null && alarmsJson.length() > 0)
                    Log.d("alarms", alarmsJson + "//" + Arrays.toString(AlarmUtil.getAlarms(AlarmListActivity.this).toArray()));
//                AlarmUtil.replaceAlarm(AlarmListActivity.this, new AlarmData[]{});
//                List<AlarmData> alarmDatas = AlarmUtil.getAlarms(AlarmListActivity.this);
//                mAdapter.setmDatas(alarmDatas);


//                Intent intent = new Intent(AlarmListActivity.this, AlarmReceiver.class);
//                intent.putExtra("alarmData", new AlarmData("><", "...", 11, 11, true, 2, new RepeatType(), true, false, new int[]{0x11}));
//                PendingIntent sender = PendingIntent.getBroadcast(
//                        AlarmListActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(System.currentTimeMillis());
//                calendar.add(Calendar.SECOND, 1);
//
//                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
            }
        });

        //aboutTv.setClickable(false);

        //aboutTv.setClickable(false);
//        aboutTv.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                AlarmUtil.saveAlarm(this, new AlarmData("吃药", "药不能停...", 8, 0, false, 1, new RepeatType(-1,-1,2,0), true, true, new int[]{0}));
//           }
//        });

        addIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmListActivity.this, EditActivity.class);
                startActivityForResult(intent, 0);
            }
        });

//        //TODO 假数据
//        AlarmUtil.saveAlarm(this, new AlarmData("吃药", "药不能停...", 8, 0, false, "", new RepeatType(), true, true, new int[]{0})
//                , new AlarmData("信用卡还贷", "药不能停...", 9, 0, false, "", new RepeatType(-1, -1, 28), true, true, new int[]{1})
//                , new AlarmData("纪念日", "药不能停...", 10, 0, false, "", new RepeatType(2016, 6, 14), true, true, new int[]{2})
//                , new AlarmData("起床", "药不能停...", 7, 45, false, "", new RepeatType(WeekDay.FRI, WeekDay.MON, WeekDay.WEN), true, true, new int[]{3}));
        //AlarmUtil.saveAlarm(this, new AlarmData("test","something like this", 16, 48 , true, 1,new RepeatType(-1,-1,3,0),true,true,new int[]{0}));
        //AlarmUtil.saveAlarm(this, new AlarmData("test2","something like this", 16, 49 , true, 1,new RepeatType(-1,-1,-1),true,true,new int[]{0}));
        //Log.i("abc", "build succeed");
        mAdapter = new AlarmAdapter(this);
        List<AlarmData> alarmDatas = AlarmUtil.getAlarms(this);
        mAlarmList.setAdapter(mAdapter);
//        mAdapter.setmDatas(alarmDatas);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.setmDatas(AlarmUtil.getAlarms(this));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                if (mAdapter != null) {
                    List<AlarmData> alarmDatas = AlarmUtil.getAlarms(this);
                    mAdapter.setmDatas(alarmDatas);
                }
            }
        }
    }
}
