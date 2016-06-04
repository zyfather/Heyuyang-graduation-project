package com.example.yang.myapplication.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by ShaunRain on 16/6/4.
 */
public class ScreenUtil {

    public static int dp2px(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

}
