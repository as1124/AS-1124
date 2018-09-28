package com.as1124.ch13test;

import android.app.Application;
import android.content.Context;

/**
 * APP中在Application 公开static方法方便在一些场景下获取Context；
 * getBaseContext和getApplicationContext的不同在于：
 * <ul>
 * <li>getApplicationContext返回的其实就是Application示例本身，而getBaseContext返回的不是Application类型</li>
 * </ul>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class CH13Application extends Application {

    private Context baseContext = null;

    private static Context appContext = null;

    public CH13Application() {
        super();
        // 构造函数执行完成后getBaseContext()仍然为null，因为ContextWrapper中的mBase还未初始化
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // 先于onCreate执行，此时mBase其实就是传入的参数base
        baseContext = getBaseContext();

        // onCreate未执行，getApplicationContext()仍然返回null
        appContext = getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 此时获取到了app-context对象
        appContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return appContext;
    }
}
