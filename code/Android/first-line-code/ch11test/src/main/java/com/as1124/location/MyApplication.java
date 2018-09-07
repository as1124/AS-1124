package com.as1124.location;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 使用百度地图必须先初始化SDK
        SDKInitializer.initialize(getApplicationContext());
    }


}
