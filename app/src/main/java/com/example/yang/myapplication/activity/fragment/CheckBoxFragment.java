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
        View v = inflater.inflate(R.layout.fragment_checkboxes, container, false);
        viewGroup = (ViewGroup) v;
        return v;
    }

    public List<WeekDay> findSelected() {
        List<WeekDay> mWeekDays = new ArrayList<WeekDay>();
        if (viewGroup != null) {
            for (int i = 0; viewGroup.getChildCount() > i; i++) {
                ViewGroup wrap = (ViewGroup) viewGroup.getChildAt(i);
                if (wrap.getChildAt(1) instanceof CheckBox)
                    if (((CheckBox) wrap.getChildAt(1)).isChecked()) {
                        mWeekDays.add(WeekDay.getValue(i + 1));
                    }
            }
        }
        return mWeekDays;
    }
}