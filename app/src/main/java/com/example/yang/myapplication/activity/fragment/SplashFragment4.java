package com.example.yang.myapplication.activity.fragment;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.yang.myapplication.ConstantValue;
import com.example.yang.myapplication.R;
import com.example.yang.myapplication.activity.AlarmListActivity;

/**
 * Created by yang on 16/6/15.
 */
public class SplashFragment4 extends Fragment {
    @Nullable
    private Button btGoin;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash4,container,false);
        btGoin = (Button) view.findViewById(R.id.bt_goin);
        btGoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AlarmListActivity.class);
                startActivity(intent);
                SharedPreferences sp = getActivity().getSharedPreferences(getActivity().getString(R.string.alarm_info),0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean(ConstantValue.FIRST_IN,false);
                editor.commit();
                getActivity().finish();
            }
        });
        return view;
    }
}
