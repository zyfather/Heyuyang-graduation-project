package com.example.yang.myapplication.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.data.WeekDay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mingjobo on 2016/5/30.
 */

public class CheckBoxFragment extends Fragment {

    ViewGroup viewGroup;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_checkboxs,container,false);
        viewGroup = (ViewGroup)v;
        return v;
    }

    public List<WeekDay> findSelected() {
        List<WeekDay> mWeekDays = new ArrayList<WeekDay>();
        if (viewGroup != null){
            for (int i = 0; viewGroup.getChildCount() > i ; i++){
                if (viewGroup.getChildAt(i) instanceof CheckBox){
                    if (((CheckBox)(viewGroup.getChildAt(i))).isChecked())
                        mWeekDays.add(WeekDay.getValue(i+1));
                }
            }
        }
        return mWeekDays;
    }
}