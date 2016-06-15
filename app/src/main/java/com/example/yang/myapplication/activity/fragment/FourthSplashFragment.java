package com.example.yang.myapplication.activity.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yang.myapplication.ConstantValue;
import com.example.yang.myapplication.R;
import com.example.yang.myapplication.activity.AlarmListActivity;
import com.example.yang.myapplication.activity.SplashActivity;

/**
 * Created by yang on 16/6/15.
 */
public class FourthSplashFragment extends Fragment{
    @Nullable
    private Button goinButton;
    private TextView goinTextView;

    private static final int[] pics = { R.mipmap.guide1, R.mipmap.guide2,
            R.mipmap.guide3,R.mipmap.guide4 };

    private static int currentItem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goinTextView = new TextView(getActivity());
        goinButton = new Button(getActivity());
        currentItem = SplashActivity.currentIndex;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth_splash,container,false);
        goinButton = (Button) view.findViewById(R.id.bt_goin);
        goinTextView = (TextView) view.findViewById(R.id.im_goin);
        Log.d("FourthSplashFragment",currentItem+"");
        goinTextView.setBackground(getActivity().getDrawable(pics[currentItem]));

        if (currentItem == pics.length-1){
            goinButton.setVisibility(View.VISIBLE);
            goinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AlarmListActivity.class);
                startActivity(intent);
                SharedPreferences sp = getActivity().getSharedPreferences(getActivity().getString(R.string.alarm_info),0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean(ConstantValue.FIRST_IN,false);
                editor.commit();
            }
        });
        }
        return view;
    }
}
