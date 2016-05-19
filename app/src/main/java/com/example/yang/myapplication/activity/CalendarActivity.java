package com.example.yang.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;

import com.example.yang.myapplication.R;

public class CalendarActivity extends AppCompatActivity {

    CalendarView mCalendarView;
    Button mBack;
    Button mAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mCalendarView = (CalendarView) findViewById(R.id.calendar);
        mBack = (Button) findViewById(R.id.back);
        mAdd = (Button) findViewById(R.id.back);


    }

}
