package com.as1124.selflib;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.List;

/**
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class AppTaskUtil {

    /**
     * 从后台切换到前台
     *
     * @param context 上下文
     * @param taskID  App TaskID
     */
    public static void moveToFront(Context context, int taskID) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            activityManager.moveTaskToFront(taskID, ActivityManager.MOVE_TASK_WITH_HOME);
        }
    }

    /**
     * 回到主屏幕, 当前应用进入后台
     *
     * @param context ApplicationContext
     */
    public static void moveToBackground(Context context) {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        context.startActivity(homeIntent);
    }

    /**
     * 完全退出程序
     * <p>
     * 注,必须添加以下权限
     * &lt;uses-permission android:name="android.permission.RESTART_PACKAGES" />
     * &lt;uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />
     * </p>
     *
     * @param context ApplicationContext
     * @param taskID  AppTask栈的ID
     */
    public static void exitApp(Context context, int taskID) {
        moveToBackground(context);
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= 21 && activityManager != null) {
            List<ActivityManager.AppTask> taskList = activityManager.getAppTasks();
            for (ActivityManager.AppTask appTask : taskList) {
                if (appTask.getTaskInfo().id == taskID) {
                    // 结束任务并移除任务栈; 否则在Overview视图中能看到
                    appTask.finishAndRemoveTask();
                    break;
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);// 退出程序
    }
}
