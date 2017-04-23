package com.as1124.android.ch2;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * Application 定制
 * 
 * @author as1124(as1124huang@gmail.com)
 *
 */
public class MyApplication extends Application implements ActivityLifecycleCallbacks{

	private static MyApplication singleton;
	
	public MyApplication() {
	}
	
	/**
	 * 返回应用程序的单一实例
	 */
	public static MyApplication getInstance() {
		return singleton;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	public final void onCreate() {
		super.onCreate();
		
		//ActivityLifecycleCallbacks 的方法调用在Activity的相应方法创建之后执行
		this.registerActivityLifecycleCallbacks(this);
		
		singleton = this;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Application#onLowMemory()
	 */
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		
		//系统过载时操作系统调用
	}
	
	/* (non-Javadoc)
	 * @see android.app.Application#onTrimMemory(int)
	 */
	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
		
		//主动减少内存
	}
	
	/* (non-Javadoc)
	 * @see android.app.Application#onConfigurationChanged(android.content.res.Configuration)
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		//物理设备配置发生更改时
	}
	
	/* (non-Javadoc)
	 * @see android.app.Application#onTerminate()
	 */
	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Application.ActivityLifecycleCallbacks#onActivityCreated(android.app.Activity, android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
		
	}

	/* (non-Javadoc)
	 * @see android.app.Application.ActivityLifecycleCallbacks#onActivityStarted(android.app.Activity)
	 */
	@Override
	public void onActivityStarted(Activity activity) {
		
	}

	/* (non-Javadoc)
	 * @see android.app.Application.ActivityLifecycleCallbacks#onActivityResumed(android.app.Activity)
	 */
	@Override
	public void onActivityResumed(Activity activity) {
		
	}

	/* (non-Javadoc)
	 * @see android.app.Application.ActivityLifecycleCallbacks#onActivityPaused(android.app.Activity)
	 */
	@Override
	public void onActivityPaused(Activity activity) {
		
	}

	/* (non-Javadoc)
	 * @see android.app.Application.ActivityLifecycleCallbacks#onActivityStopped(android.app.Activity)
	 */
	@Override
	public void onActivityStopped(Activity activity) {
		
	}

	/* (non-Javadoc)
	 * @see android.app.Application.ActivityLifecycleCallbacks#onActivitySaveInstanceState(android.app.Activity, android.os.Bundle)
	 */
	@Override
	public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
		
	}

	/* (non-Javadoc)
	 * @see android.app.Application.ActivityLifecycleCallbacks#onActivityDestroyed(android.app.Activity)
	 */
	@Override
	public void onActivityDestroyed(Activity activity) {
		
	}

}
