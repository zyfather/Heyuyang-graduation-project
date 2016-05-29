package com.example.yang.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.yang.myapplication.R;

/**
 * 闹钟响铃界面
 */
public class WakeUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_wake_up);
    }

    public void close(View view) {
        finish();
    }
}
