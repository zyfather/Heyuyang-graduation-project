package com.example.yang.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.yang.myapplication.ConstantValue;
import com.example.yang.myapplication.R;
import com.example.yang.myapplication.data.RepeatType;
import com.example.yang.myapplication.data.WeekDay;


/**
 * Created by yang on 16/5/31.
 */
public class RepeatActivity extends Activity implements View.OnClickListener {

    TextView leftTv;
    TextView middleTv;
    TextView rightTv;

    int currentRadioButton;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_repeat);

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
                currentRadioButton = checkedId;
                if (checkedId == R.id.radio_defineday){
                    startActivityForResult(new Intent(RepeatActivity.this,CustomUserDefinedActivity.class),ConstantValue.repeatDefineRequestCode);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_right_tv:
                submitData();
                break;
            case R.id.title_left_tv:
                finish();
                break;
        }
    }

    private void submitData() {
        RepeatType repeatType = null;
        if (currentRadioButton == R.id.radio_null){
            repeatType = null;
        }
        if (currentRadioButton == R.id.radio_everyday){
            repeatType = new RepeatType(WeekDay.SUN,WeekDay.MON,WeekDay.TUE,
                    WeekDay.WEN,WeekDay.THR,WeekDay.FRI,WeekDay.SAT);
        }
        if (currentRadioButton == R.id.radio_weekend){
            repeatType = new RepeatType(WeekDay.SUN,WeekDay.SAT);
        }
        if (currentRadioButton == R.id.radio_workday){
            repeatType = new RepeatType(WeekDay.MON,WeekDay.TUE,
                    WeekDay.WEN,WeekDay.THR,WeekDay.FRI);
        }
        setResult(RESULT_OK,new Intent().putExtra(ConstantValue.repeatKeyString,repeatType));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode  == ConstantValue.repeatDefineRequestCode){
                if (data.getSerializableExtra(ConstantValue.repeatKeyString) instanceof RepeatType) {
                    RepeatType repeatType = (RepeatType) data.getSerializableExtra(ConstantValue.repeatKeyString);
                    setResult(RESULT_OK, new Intent().putExtra(ConstantValue.repeatKeyString, repeatType));
                    finish();
                }
            }
        }
        if (resultCode == ConstantValue.resultCodeCancel){
            finish();
        }
    }
}
