package com.as1124.android.ch2;

import android.app.Application;
import android.content.res.Configuration;

/**
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class MyApplication extends Application {

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
		singleton = this;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Application#onLowMemory()
	 */
	@Override
	public void onLowMemory() {
		super.onLowMemory();
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
}
