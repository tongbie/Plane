package com.example.plane;

import android.app.Activity;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by BieTong on 2018/4/7.
 */

public class Utils {

    public static float dp(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return px * scale + 0.5f;
    }

    public static void hideTitleBar(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
