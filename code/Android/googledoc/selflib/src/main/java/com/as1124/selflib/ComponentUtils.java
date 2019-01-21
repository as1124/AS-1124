package com.as1124.selflib;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.os.Build;

import java.util.List;

public class ComponentUtils {

    /**
     * 判断Service是否运行
     *
     * @param context 具体Service实例
     * @param clazz   查询服务对应的类
     * @return true-service存活正在运行
     */
    public static boolean isServiceRunning(Context context, Class<? extends Service> clazz) {
        ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // ATTENTION android 8之后取消下面的方法
        } else {
            List<ActivityManager.RunningServiceInfo> rs = am.getRunningServices(10);
            for (ActivityManager.RunningServiceInfo info : rs) {
                if (info.service.getClassName().equals(clazz.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断给定Activity是否正在运行, 在后台也认为Activity是活的
     *
     * @param activity 具体的Activity实例
     * @return true-正在运行, 但并不代表处于前台
     */
    public static boolean isActivityRunning(Activity activity) {
        return !(activity.isDestroyed() | activity.isFinishing());
    }

    /**
     * 判断给定Activity是否在前台
     *
     * @param activity 具体Activity实例
     * @return true-前台
     */
    public static boolean isActivityForeground(Activity activity) {
        ActivityManager am = (ActivityManager) activity.getSystemService(Activity.ACTIVITY_SERVICE);
        String className = activity.getClass().getName();
        if (Build.VERSION.SDK_INT >= 23) {
            List<ActivityManager.AppTask> tasks = am.getAppTasks();
            if (tasks != null) {
                int size = tasks.size();
                for (int i = 0; i < size; i++) {
                    if (tasks.get(i).getTaskInfo().topActivity.getClassName().equals(className)) {
                        return true;
                    }
                }
            }
        } else if (Build.VERSION.SDK_INT >= 16) {
            List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(15);
            if (tasks != null) {
                int size = tasks.size();
                for (int i = 0; i < size; i++) {
                    if (tasks.get(i).topActivity.getClassName().equals(className)) {
                        return true;
                    }
                }
            }
        }

        // ATTENTION 好像这个适合所有情况, 不需要上述繁琐判断
        return activity.hasWindowFocus();
    }

}
