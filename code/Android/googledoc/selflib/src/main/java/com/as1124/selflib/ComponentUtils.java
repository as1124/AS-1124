package com.as1124.selflib;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.io.File;
import java.util.List;

/**
 * Android基础组件相关操作封装
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class ComponentUtils {

    /**
     * 判断Service是否运行
     *
     * @param context 具体Service实例
     * @param clazz   查询服务对应的类
     * @param create  如果不存在是否创建
     * @return true-service存活正在运行
     */
    public static boolean isServiceRunning(Context context, Class<? extends Service> clazz, boolean create) {
        ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // ATTENTION android 8之后取消 getRunningServices(但doc说明中解释仍然会返回当前应用own的service, 所以其实也还是可以使用的 )
            // 尝试使用第二种方法: 通过ServiceConnection 来实现

        } else {
            List<ActivityManager.RunningServiceInfo> rs = am.getRunningServices(10);
            for (ActivityManager.RunningServiceInfo info : rs) {
                if (info.service.getClassName().equals(clazz.getName())) {
                    return true;
                }
            }
            if (create) {
                context.startService(new Intent(context, clazz));
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
        return false;
    }

    /**
     * 判断给定Activity是否在前台
     *
     * @param appContext 具体Activity实例
     * @return true-前台
     */
    public static boolean isAppForeground(Context appContext) {
        ActivityManager am = (ActivityManager) appContext.getSystemService(Activity.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return false;
        }
        for (int k = 0; k < runningApps.size(); k++) {
            ActivityManager.RunningAppProcessInfo processInfo = runningApps.get(k);
            if ((android.os.Process.myPid() == processInfo.pid)
                    && appContext.getApplicationInfo().processName.equals(processInfo.processName)) {
                return (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND);
            }
        }
        return false;
    }

    /**
     * 设备是否处于熄屏幕状态
     *
     * @param context
     * @return
     */
    public static boolean isScreenLocked(Context context) {
        KeyguardManager guardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        if (guardManager == null) {
            return false;
        } else {
            return guardManager.isKeyguardLocked();
        }
    }

    public static void installAPK(Context context, File apkFile) {
        // ATTENTION 安装APK文件
    }
}
