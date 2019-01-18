package com.as1124.activities;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.Stack;

/**
 * 自定义{@link Application}
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124Application extends Application {

    private static final String LOG_TAG = As1124Application.class.getSimpleName();

    private static As1124Application mApplication = null;

    private Stack<Activity> activityStack = new Stack<>();

    private ActivityLifecycleCallbacks lifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.i(LOG_TAG, "[onActivityCreated] " + activity.getLocalClassName());
            activityStack.push(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            Log.i(LOG_TAG, "[onActivityStarted] " + activity.getLocalClassName());
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Log.i(LOG_TAG, "[onActivityResumed] " + activity.getLocalClassName());
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Log.i(LOG_TAG, "[onActivityPaused] " + activity.getLocalClassName());
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Log.i(LOG_TAG, "[onActivitySaveInstanceState] " + activity.getLocalClassName());
        }

        @Override
        public void onActivityStopped(Activity activity) {
            Log.i(LOG_TAG, "[onActivityStopped] " + activity.getLocalClassName());
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.i(LOG_TAG, "[onActivityDestroyed] " + activity.getLocalClassName());
            activityStack.pop();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        activityStack.clear();
        mApplication = this;
        Log.i(LOG_TAG, "[Application onCreate called]");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Log.i(LOG_TAG, "Process Name is = " + getProcessName());
        }
        this.registerActivityLifecycleCallbacks(this.lifecycleCallbacks);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.i(LOG_TAG, "[Application onConfigurationChanged called]");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        Log.i(LOG_TAG, "[Application onTerminate]");
        this.unregisterActivityLifecycleCallbacks(this.lifecycleCallbacks);

        activityStack.clear();
        mApplication = null;
    }

    public Stack<Activity> getActivityStack() {
        return activityStack;
    }

    public static As1124Application getApplicationInstance() {
        return mApplication;
    }
}
