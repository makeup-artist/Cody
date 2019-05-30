package com.example.cody;

import android.app.Application;
import android.content.Context;

/**
 * @author by licheng on 2018/6/29.
 */

public class MyApplication extends Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
