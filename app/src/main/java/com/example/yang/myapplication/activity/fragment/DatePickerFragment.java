package com.example.yang.myapplication.activity.fragment;

/**
 * Created by yang on 16/5/30.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.yang.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mingjobo on 2016/5/30.
 */

public class DatePickerFragment extends Fragment {
    DatePicker mDatePicker;

    List<NumberPicker> mNumberPickers;
    NumberPicker dayNumberPicker;
    NumberPicker monthNumberPicker;
    NumberPicker yearNumberPicker;

    TextView year;
    TextView month;
    TextView day;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_datepicker, container, false);
        mDatePicker = (DatePicker) v.findViewById(R.id.custom_user_defined_datepicker);

        year = (TextView) v.findViewById(R.id.tv_year);
        month = (TextView) v.findViewById(R.id.tv_month);
        day = (TextView) v.findViewById(R.id.tv_day);

        mNumberPickers = findNumberPicker(mDatePicker);
        initNumPicker();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        initNumPicker();
    }

    private void initNumPicker() {
        dayNumberPicker = mNumberPickers.get(2);
        monthNumberPicker = mNumberPickers.get(1);
        yearNumberPicker = mNumberPickers.get(0);
    }

    private List<NumberPicker> findNumberPicker(ViewGroup v) {
        List<NumberPicker> numberPickerList = new ArrayList<NumberPicker>();
        View child = null;
        if (v != null) {
            for (int i = 0; i < v.getChildCount(); i++) {
                child = v.getChildAt(i);
                if (child instanceof NumberPicker) {
                    numberPickerList.add((NumberPicker) child);
                } else if (child instanceof LinearLayout) {
                    List<NumberPicker> result = findNumberPicker((ViewGroup) child);
                    if (result.size() > 0) {
                        return result;
                    }
                }
            }
        }
        return numberPickerList;
    }

    public void disappearMouthNum() {
        month.setVisibility(View.GONE);
        monthNumberPicker.setVisibility(View.GONE);
    }

    public void disappearYearNum() {
        day.setVisibility(View.GONE);
        yearNumberPicker.setVisibility(View.GONE);
    }

    public int getYear() {
        return mDatePicker.getYear();
    }

    public int getMonth() {
        return mDatePicker.getMonth();
    }

    public int getDay() {
        return mDatePicker.getDayOfMonth();
    }
}