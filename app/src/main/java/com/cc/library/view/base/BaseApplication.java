package com.cc.library.view.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhangyu on 2016-07-11 18:49.
 */
public class BaseApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
