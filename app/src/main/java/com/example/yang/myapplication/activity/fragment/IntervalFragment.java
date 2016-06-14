package com.example.yang.myapplication.activity.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.activity.CustomUserDefinedActivity;
import com.example.yang.myapplication.data.RepeatType;

import java.lang.reflect.Field;

/**
 * Created by yang on 16/6/10.
 */
public class IntervalFragment extends Fragment{
    NumberPicker valuepicker;
    TextView intervalTv;

    int currentRadioButton = 0;
    RadioGroup radioGroup;

    private int current;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        current = CustomUserDefinedActivity.currentItem;

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_interval, container, false);

        valuepicker = (NumberPicker) v.findViewById(R.id.custom_user_defined_numberpicker);
        intervalTv = (EditText) v.findViewById(R.id.custom_user_defined_interval);
        radioGroup = (RadioGroup) v.findViewById(R.id.custom_user_defined_radiogroup);

        initNumPicker();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                currentRadioButton = checkedId;
            }
        });

        intervalTv.setInputType(EditorInfo.TYPE_CLASS_PHONE);

        return  v;
    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        boolean result = false;
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText) child).setTextColor(color);
                    numberPicker.invalidate();
                    result = true;
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public void initNumPicker() {
        setNumberPickerTextColor(valuepicker, R.color.black);
        String[] intervalUnit = {"天","小时","分钟"};
        valuepicker.setDisplayedValues(intervalUnit);
        valuepicker.setMinValue(0);
        valuepicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        valuepicker.setMaxValue(intervalUnit.length - 1);
        valuepicker.setValue(1);
    }

    public RepeatType getRepeatType (){
        int iDays = 0;
        int dInterval = -1;
        int hInterval = -1;
        int mInterval = -1;

        if (currentRadioButton == 1){
            iDays = 1;
        }
        if (valuepicker.getValue() == 0){
            dInterval = Integer.valueOf(intervalTv.getText().toString());
        }else if (valuepicker.getValue() == 1){
            hInterval = Integer.valueOf(intervalTv.getText().toString());
        }else{
            mInterval = Integer.valueOf(intervalTv.getText().toString());
        }

        return new RepeatType(dInterval, hInterval, mInterval, iDays);
    }

}
