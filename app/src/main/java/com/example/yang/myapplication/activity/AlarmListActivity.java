package com.example.yang.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.yang.myapplication.R;

public class AlarmListActivity extends Activity {

    RecyclerView mAlarmList;

    TextView leftTv;
    TextView middleTv;
    TextView rightTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        leftTv = (TextView) findViewById(R.id.title_left_tv);
        middleTv = (TextView) findViewById(R.id.title_middle_tv);
        rightTv = (TextView) findViewById(R.id.title_right_tv);
        mAlarmList = (RecyclerView) findViewById(R.id.alarm_list);

        leftTv.setText(R.string.about);
        middleTv.setText(R.string.alarm);
        rightTv.setText(R.string.newAlarm);


        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmListActivity.this,CustomUserDefinedActivity.class);
                startActivity(intent);
            }
        });
    }


}
