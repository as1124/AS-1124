package com.as1124.background_tasks.schedule;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.job.JobScheduler;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.as1124.background_tasks.R;

/**
 * 定时任务处理
 * <ul>
 *     <li>Below API-21: Use {@link AlarmManager}</li>
 *     <li>Above API-21: Use {@link JobScheduler}</li>
 * </ul>
 * @author as-1124(mailto:as1124huang@primeton.com)
 */
public class SchedulerActivity extends Activity {

    private JobScheduler jobManager;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            jobManager = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        } else {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }

    }
}
