package com.as1124.ch3.views;

import android.app.Application;
import android.util.Log;

public class As1124Application extends Application {

    private static final String LOG_TAG = "CH3-Application";

    public As1124Application() {
        Log.i(LOG_TAG, "application init!");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(LOG_TAG, "onCreate called!");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        Log.i(LOG_TAG, "onTerminate called!");
    }
}
