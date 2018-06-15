package com.adtv;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    /**
     * Context for Application
     */
    private static Context sApplicationContext;

    /**
     * to get the context of the current activity
     *
     * @return context of the current activity
     */
    public static Context getMyApplicationContext() {
        return sApplicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplicationContext = MyApplication.this;
    }
}
