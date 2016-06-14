package com.example.yang.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.yang.myapplication.ConstantValue;
import com.example.yang.myapplication.R;
import com.example.yang.myapplication.activity.fragment.CheckBoxFragment;
import com.example.yang.myapplication.activity.fragment.DatePickerFragment;
import com.example.yang.myapplication.activity.fragment.IntervalFragment;
import com.example.yang.myapplication.adapter.DefineFragmentPaperAdapter;
import com.example.yang.myapplication.data.RepeatType;
import com.example.yang.myapplication.data.WeekDay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mingjobo on 2016/5/28.
 */

public class CustomUserDefinedActivity extends FragmentActivity implements View.OnClickListener {

    private String TAG = "DefinedActivity";

    TextView weekTv;
    TextView dayTV;
    TextView monthTv;
    TextView specialDayTv;
    TextView intervalDayTv;
    TextView leftTv;
    TextView middleTv;
    TextView rightTv;

    DatePickerFragment specialFrament;
    DatePickerFragment dayFragment;
    DatePickerFragment monthFragment;
    CheckBoxFragment weekFragment;
    IntervalFragment intervalFragment;

    ViewPager mViewPager;
    DatePicker mDatePicker;
    public static int currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_custom_user_defined);
        weekTv = (TextView) findViewById(R.id.define_activity_week);
        dayTV = (TextView) findViewById(R.id.define_activity_day);
        monthTv = (TextView) findViewById(R.id.define_activity_month);
        specialDayTv = (TextView) findViewById(R.id.define_activity_specialday);
        intervalDayTv = (TextView) findViewById(R.id.define_activity_interval);
        mViewPager = (ViewPager) findViewById(R.id.custom_user_defined_viewpager);
        leftTv = (TextView) findViewById(R.id.title_left_tv);
        middleTv = (TextView) findViewById(R.id.title_middle_tv);
        rightTv = (TextView) findViewById(R.id.title_right_tv);


        mDatePicker = (DatePicker) findViewById(R.id.custom_user_defined_datepicker);

        InitViewPager();

        leftTv.setText(R.string.cancel);
        middleTv.setText(R.string.define);
        rightTv.setText(R.string.ok);

        leftTv.setOnClickListener(this);
        rightTv.setOnClickListener(this);
        weekTv.setOnClickListener(this);
        dayTV.setOnClickListener(this);
        monthTv.setOnClickListener(this);
        specialDayTv.setOnClickListener(this);
        intervalDayTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_tv:
                finish();
                break;
            case R.id.title_right_tv:
                submitData();
            case R.id.define_activity_week:
                Log.d(TAG, "week");
                showPager((TextView) v, 0);
                break;
            case R.id.define_activity_day:
                Log.d(TAG, "day");
                showPager((TextView) v, 1);
                break;
            case R.id.define_activity_month:
                Log.d(TAG, "month");
                showPager((TextView) v, 2);
                break;
            case R.id.define_activity_specialday:
                Log.d(TAG, "specialday");
                showPager((TextView) v, 3);
                break;
            case R.id.define_activity_interval:
                Log.d(TAG, "interval");
                showPager((TextView) v, 4);
                break;
        }
    }

    private void submitData() {
        RepeatType mRepeatType = null;
        if (currentItem == 0) {
            ArrayList<WeekDay> mWeekDays = (ArrayList<WeekDay>) weekFragment.findSelected();
            mRepeatType = new RepeatType((WeekDay[]) mWeekDays.toArray(new WeekDay[mWeekDays.size()]));
        }
        if (currentItem == 1) {
            mRepeatType = new RepeatType(dayFragment.getYear(), dayFragment.getMonth(), dayFragment.getDay());
        }
        if (currentItem == 2) {
            mRepeatType = new RepeatType(-1, -1, monthFragment.getDay());
        }
        if (currentItem == 3) {
            mRepeatType = new RepeatType(-1, specialFrament.getMonth(), specialFrament.getDay());
        }
        if (currentItem == 4) {
            mRepeatType = intervalFragment.getRepeatType();
        }
        setResult(RESULT_OK, new Intent().putExtra(ConstantValue.repeatKeyString, mRepeatType));
        finish();
    }


    private void showPager(TextView v, int index) {
        initTextColor();
//        if (index == 2 ) {//ture 消失年月
//            monthFragment.disappearMouthAndYearNum();
//        } else if (index == 3){//false 消失年
//            specialFrament.disappearYearNum();
//        }
        v.setTextColor(getApplicationContext().getResources().getColor(R.color.title_red));
        mViewPager.setCurrentItem(index);
    }

    private void initTextColor() {
        int color = getApplicationContext().getResources().getColor(R.color.gray);
        dayTV.setTextColor(color);
        monthTv.setTextColor(color);
        weekTv.setTextColor(color);
        specialDayTv.setTextColor(color);
    }

    public void InitViewPager() {

        final List<Fragment> paperFragments = new ArrayList<Fragment>();
        specialFrament = new DatePickerFragment();
        dayFragment = new DatePickerFragment();
        monthFragment = new DatePickerFragment();
        weekFragment = new CheckBoxFragment();
        intervalFragment = new IntervalFragment();

        paperFragments.add(weekFragment);
        paperFragments.add(dayFragment);
        paperFragments.add(monthFragment);
        paperFragments.add(specialFrament);
        paperFragments.add(intervalFragment);

        mViewPager.setAdapter(new DefineFragmentPaperAdapter(getSupportFragmentManager(), paperFragments));
        mViewPager.setCurrentItem(0);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentItem = position;
                Log.d(TAG, position + "");
                if (position == 0) {
                    initTextColor();
                    weekTv.setTextColor(getApplicationContext().getResources().getColor(R.color.title_red));
                }
                if (position == 1) {
                    initTextColor();
                    dayTV.setTextColor(getApplicationContext().getResources().getColor(R.color.title_red));
                }
                if (position == 2) {
                    initTextColor();
                    monthFragment.displayEveryMonth();
                    monthTv.setTextColor(getApplicationContext().getResources().getColor(R.color.title_red));
                }
                if (position == 3) {
                    initTextColor();
                    specialFrament.displaySpecialDay();
                    specialDayTv.setTextColor(getApplicationContext().getResources().getColor(R.color.title_red));
                }
                if (position == 4) {
                    initTextColor();
                    //这是干啥的?
                    //monthFragment.displayEveryMonth();
                    //specialFrament.displaySpecialDay();
                    intervalDayTv.setTextColor(getApplicationContext().getResources().getColor(R.color.title_red));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}