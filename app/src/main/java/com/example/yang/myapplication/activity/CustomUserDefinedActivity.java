package com.example.yang.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.yang.myapplication.R;

/**
 * Created by mingjobo on 2016/5/28.
 */

public class CustomUserDefinedActivity extends Activity implements View.OnClickListener{

    private String TAG = "DefinedActivity";

    TextView leftTv;
    TextView middleTv;
    TextView rightTv;

    TextView weekTv;
    TextView dayTV;
    TextView monthTv;
    TextView SpecialDayTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_user_defined);

        leftTv = (TextView) findViewById(R.id.title_left_tv);
        middleTv = (TextView) findViewById(R.id.title_middle_tv);
        rightTv = (TextView) findViewById(R.id.title_right_tv);
        weekTv = (TextView) findViewById(R.id.define_activity_week);
        dayTV = (TextView) findViewById(R.id.define_activity_day);
        monthTv = (TextView) findViewById(R.id.define_activity_month);
        SpecialDayTv = (TextView) findViewById(R.id.define_activity_specialday);


        leftTv.setText(R.string.cancel);
        middleTv.setText(R.string.define);
        rightTv.setText(R.string.ok);

        leftTv.setOnClickListener(this);
        rightTv.setOnClickListener(this);
        weekTv.setOnClickListener(this);
        dayTV.setOnClickListener(this);
        monthTv.setOnClickListener(this);
        SpecialDayTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_tv:
                finish();
                break;
            case R.id.title_right_tv:
                finish();
                break;
            case R.id.define_activity_week:
                Log.d(TAG,"week");
                break;
            case R.id.define_activity_day:
                Log.d(TAG,"day");
                break;
            case R.id.define_activity_month:
                Log.d(TAG,"month");
                break;
            case R.id.define_activity_specialday:
                Log.d(TAG,"specialday");
                break;
        }
    }
}
