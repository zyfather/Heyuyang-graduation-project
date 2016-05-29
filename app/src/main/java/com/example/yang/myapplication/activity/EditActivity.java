package com.example.yang.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.data.AlarmData;
import com.example.yang.myapplication.data.RepeatType;
import com.example.yang.myapplication.utils.AlarmUtil;

public class EditActivity extends Activity {

    TimePicker mTimePicker;
    RelativeLayout mRepeat;
    RelativeLayout mTag;
    RelativeLayout mContent;
    RelativeLayout mRing;

    TextView repeatText;
    TextView tagText;
    TextView contentText;
    TextView ringText;

    Button mDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_edit);
        initViews();
    }

    private void initViews() {
        TextView cancelTv = (TextView) findViewById(R.id.title_left_tv);
        TextView middleTv = (TextView) findViewById(R.id.title_middle_tv);
        TextView saveTv = (TextView) findViewById(R.id.title_right_tv);
        saveTv.setVisibility(View.VISIBLE);
        findViewById(R.id.title_right_iv).setVisibility(View.GONE);

        cancelTv.setText("取消");
        middleTv.setText("添加/编辑");  //TODO 判断
        saveTv.setText("存储");

        mTimePicker = (TimePicker) findViewById(R.id.edit_time_picker);
        mTimePicker.setIs24HourView(true);
        mRepeat = (RelativeLayout) findViewById(R.id.rl_repeat);
        mTag = (RelativeLayout) findViewById(R.id.rl_tag);
        mContent = (RelativeLayout) findViewById(R.id.rl_content);
        mRing = (RelativeLayout) findViewById(R.id.rl_ring);
        mDelete = (Button) findViewById(R.id.edit_delete);

        repeatText = (TextView) findViewById(R.id.tv_repeat);
        tagText = (TextView) findViewById(R.id.tv_tag);
        contentText = (TextView) findViewById(R.id.tv_content);
        ringText = (TextView) findViewById(R.id.tv_ring);

        //TODO delete 判断


        final Context ctx = this;

        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hou;
                int min;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hou = mTimePicker.getHour();
                    min = mTimePicker.getMinute();
                } else {
                    hou = mTimePicker.getCurrentHour();
                    min = mTimePicker.getCurrentMinute();
                }

                String name = tagText.getText().toString();
                String detail = (String) contentText.getText();
                String ring = (String) ringText.getText();
                AlarmUtil.saveAlarm(ctx, new AlarmData(name, detail, hou, min, false, ring, new RepeatType(), true, true));
                setResult(RESULT_OK);
                finish();
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
