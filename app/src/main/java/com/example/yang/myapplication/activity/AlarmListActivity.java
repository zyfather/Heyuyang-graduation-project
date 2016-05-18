package com.example.yang.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.yang.myapplication.R;

public class AlarmListActivity extends AppCompatActivity {

    RecyclerView mAlarmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        mAlarmList = (RecyclerView) findViewById(R.id.alarm_list);


    }
}
