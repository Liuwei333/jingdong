package com.example.imitatejingdong.utils;

import android.app.Activity;

import com.example.imitatejingdong.R;

/**
 * Created by Administrator on 2018/5/26.
 */

public class ThemeChangeUtil {
    public static boolean isChange = false;
    public static void changeTheme(Activity activity){
        if(isChange){
            activity.setTheme(R.style.NightTheme);
        }
    }


}
