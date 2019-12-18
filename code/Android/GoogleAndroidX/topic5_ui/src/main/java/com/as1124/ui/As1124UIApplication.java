package com.as1124.ui;

import android.app.Application;
import android.content.Context;


/**
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class As1124UIApplication extends Application {

    public As1124UIApplication() {
        // 系统启动时调用，加载LoadedApk时：
        // 1. ActivityManagerService 解析am命令，
        // 2. ActivityManagerService 成员变量 ApplicationThread.bindApplication
        // 3. 发送Message 到 ActivityThread$H.handleMessage 进行处理
        // 4. 经由 Instrumentation 路由，最终反射调用到 Application 中
        // 5. 创建实例之后立即 attacheBaseContext()
        super();
    }

    @Override
    protected void attachBaseContext(Context base) {
        // 创建Application之后立即调用
        super.attachBaseContext(base);
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }

}
