package com.as1124.selflib;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.os.Build;

import java.util.List;

public class ComponentUtils {

    public static boolean isServiceRunning(Service service) {
        return false;
    }

    /**
     * 判断给定Activity是否正在运行, 在后台也认为Activity是活的
     *
     * @param activity
     * @return
     */
    public static boolean isActivityRunning(Activity activity) {
        return !(activity.isDestroyed() | activity.isFinishing());
    }

    /**
     * 判断给定Activity是否在前台
     *
     * @param activity
     * @return
     */
    public static boolean isActivityForeground(Activity activity) {
        ActivityManager am = (ActivityManager) activity.getSystemService(Activity.ACTIVITY_SERVICE);
        String className = activity.getClass().getName();
        if (Build.VERSION.SDK_INT >= 16) {
            List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(15);
            if (tasks != null) {
                int size = tasks.size();
                for (int i = 0; i < size; i++) {
                    if (tasks.get(i).topActivity.getClassName().equals(className)) {
                        return true;
                    }
                }
            }
        } else if (Build.VERSION.SDK_INT >= 23) {
            List<ActivityManager.AppTask> tasks = am.getAppTasks();
            if (tasks != null) {
                int size = tasks.size();
                for (int i = 0; i < size; i++) {
                    if (tasks.get(i).getTaskInfo().topActivity.getClassName().equals(className)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
