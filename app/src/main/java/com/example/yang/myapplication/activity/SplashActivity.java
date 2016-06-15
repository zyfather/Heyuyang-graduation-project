package com.example.yang.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yang.myapplication.ConstantValue;
import com.example.yang.myapplication.R;
import com.example.yang.myapplication.activity.fragment.SplashFragment1;
import com.example.yang.myapplication.activity.fragment.SplashFragment2;
import com.example.yang.myapplication.activity.fragment.SplashFragment3;
import com.example.yang.myapplication.activity.fragment.SplashFragment4;
import com.example.yang.myapplication.adapter.ViewPagerAdapter;

import java.util.ArrayList;

/**
 * Created by yang on 16/6/15.
 */
public class SplashActivity extends FragmentActivity implements OnClickListener,
        OnPageChangeListener {
    private TextView tvBlackGround;

    private ViewPager viewPager;

    private ViewPagerAdapter vpAdapter;

    private ArrayList<Fragment> fragments;

    private ImageView[] points;

    private boolean isFirstIn;

    public static int currentIndex = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                if (isFirstIn){
                    tvBlackGround.setVisibility(View.GONE);
                    initPoint();
                }else {
                    Intent intent = new Intent(SplashActivity.this, AlarmListActivity.class);
                    startActivity(intent);
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvBlackGround = (TextView) findViewById(R.id.tv_blackground);
        tvBlackGround.setVisibility(View.VISIBLE);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOnPageChangeListener(this);
        fragments = new ArrayList<Fragment>();
        fragments.add(new SplashFragment1());
        fragments.add(new SplashFragment2());
        fragments.add(new SplashFragment3());
        fragments.add(new SplashFragment4());
        vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(vpAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                isFirstIn = getSharedPreferences(getString(R.string.alarm_info), 0).getBoolean(ConstantValue.FIRST_IN, true);
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    //        }).start();
//        if (getSharedPreferences(getString(R.string.alarm_info),0).getBoolean(ConstantValue.FIRST_IN, true)){
//            initPoint();
//        }else {
//            Intent intent = new Intent(SplashActivity.this, AlarmListActivity.class);
//            startActivity(intent);
//        }

//        isFirstIn = getSharedPreferences(getString(R.string.alarm_info),0).getBoolean(ConstantValue.FIRST_IN, true);
////        new Handler().postDelayed(new Runnable() {
////            @Override
////            public void run() {
//                if (isFirstIn) {
//                    // start guideactivity1
//                    initPoint();
//                } else {
//                    // start TVDirectActivity
//                    Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
//                    startActivity(intent);
//                }
//
////            }
////        }, 0);



    private void initPoint() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll);

        points = new ImageView[4];

        for (int i = 0; i < 4; i++) {
            points[i] = (ImageView) linearLayout.getChildAt(i);
            points[i].setEnabled(true);
            // ∏¯√ø∏ˆ–°µ„…Ë÷√º‡Ã˝
            points[i].setOnClickListener(this);
            // …Ë÷√Œª÷√tag£¨∑Ω±„»°≥ˆ”Îµ±«∞Œª÷√∂‘”¶
            points[i].setTag(i);
        }

        // …Ë÷√µ±√Êƒ¨»œµƒŒª÷√
        currentIndex = 0;
        // …Ë÷√Œ™∞◊…´£¨º¥—°÷–◊¥Ã¨
        points[currentIndex].setEnabled(false);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        setCurDot(arg0);
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }

    private void setCurView(int position) {
        if (position < 0 || position >= fragments.size()) {
            return;
        }
        viewPager.setCurrentItem(position);
    }

    private void setCurDot(int positon) {
        if (positon < 0 || positon > fragments.size() - 1 || currentIndex == positon) {
            return;
        }
        points[positon].setEnabled(false);
        points[currentIndex].setEnabled(true);

        currentIndex = positon;
    }
}


