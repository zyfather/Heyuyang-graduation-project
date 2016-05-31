package com.example.yang.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.yang.myapplication.R;


/**
 * Created by yang on 16/5/31.
 */
public class RepetActivity extends Activity implements View.OnClickListener {

    public static String TAG = "RepetActivity";
    TextView leftTv;
    TextView middleTv;
    TextView rightTv;

    int currentRadioButton;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_repet);

        leftTv = (TextView) findViewById(R.id.title_left_tv);
        middleTv = (TextView) findViewById(R.id.title_middle_tv);
        rightTv = (TextView) findViewById(R.id.title_right_tv);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);

        leftTv.setText(getResources().getString(R.string.cancel));
        rightTv.setText(getResources().getString(R.string.ok));
        middleTv.setText(getResources().getString(R.string.repet));
        leftTv.setOnClickListener(this);
        rightTv.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG,checkedId + "");
                currentRadioButton = checkedId;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_right_tv:
                finish();
                break;
            case R.id.title_left_tv:
                submitData();
                break;
        }
    }

    private void submitData() {
        if (currentRadioButton == 0){

        }
        if (currentRadioButton == 1){

        }
        if (currentRadioButton == 2){

        }
        if (currentRadioButton == 3){

        }
    }
}
