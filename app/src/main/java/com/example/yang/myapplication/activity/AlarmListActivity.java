package com.example.yang.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.adapter.AlarmAdapter;
import com.example.yang.myapplication.utils.AlarmUtil;

/**
 * 首页闹钟列表
 */
public class AlarmListActivity extends Activity {

    RecyclerView mAlarmList;
    AlarmAdapter mAdapter;

    TextView aboutTv;
    TextView middleTv;
    ImageView addIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        initViews();

    }

    public void initViews() {
        aboutTv = (TextView) findViewById(R.id.title_left_tv);
        middleTv = (TextView) findViewById(R.id.title_middle_tv);
        addIv = (ImageView) findViewById(R.id.title_right_iv);
        mAlarmList = (RecyclerView) findViewById(R.id.alarm_list);

        aboutTv.setText(R.string.about);
        middleTv.setText(R.string.alarm);
        addIv.setVisibility(View.VISIBLE);
        findViewById(R.id.title_right_tv).setVisibility(View.GONE);

        addIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmListActivity.this, CustomUserDefinedActivity.class);
                startActivity(intent);
            }
        });
        mAdapter = new AlarmAdapter(this);
        mAlarmList.setAdapter(mAdapter);
        mAdapter.setmDatas(AlarmUtil.getAlarms(this));


    }


}
