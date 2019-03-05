package as1124.com.helloworld;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.text.MessageFormat;

import as1124.com.helloworld.ch3.LayoutTestActivity;
import as1124.com.helloworld.dialog.DialogActivity;
import as1124.com.helloworld.dialog.NormalActivity;
import as1124.com.helloworld.widget.WidgetShortcutActivity;

/**
 * 内容包括
 * <ol>
 * <li>Activity的生命周期</li>
 * <li>Activity组件的基本使用：无返回跳转、有返回值跳转</li>
 * <li>显式、隐式Intent用法</li>
 * <li>Deep Link&URL-Schema的使用</li>
 * <li>对话框</li>
 * <li>常见布局</li>
 * <li>Listview的使用</li>
 * <li>RecyclerListview的使用</li>
 * <li>Link问题</li>
 * <li>Recent Screen相关问题</li>
 * <li>Android Shortcuts and Widgets</li>
 * </ol>
 *
 * @author as-1124 (mailto:as1124huang@gmail.com)
 */
public class HelloWorldActivity extends Activity {

    private static final String LOG_TAG = "ch3test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);

        String appInfo = MessageFormat.format("packageName={0}, processName={1}, taskAffinity={2}, taskID={3}",
                getApplicationInfo().packageName, getApplicationInfo().processName, getApplicationInfo().taskAffinity, getTaskId());
        Log.i(LOG_TAG, appInfo);
        Log.i(LOG_TAG, "HelloWorldActivity is created!!");
        if (savedInstanceState != null) {
            Log.i(LOG_TAG, "onCreated called base on extras");
        } else {
            Log.i(LOG_TAG, "onCreated called on first");
        }

        findViewById(R.id.but_lifecycle).setOnClickListener(v -> {
            Intent intent = new Intent(HelloWorldActivity.this, LifeCycleActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.but_normal_intent).setOnClickListener(v -> {
            Intent intent = new Intent(HelloWorldActivity.this, FirstActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.but_hide_intent).setOnClickListener(v -> {
            String action = "as1124.com.helloworld.huangjw";
            Intent intent = new Intent(action);
            startActivity(intent);
        });
        findViewById(R.id.but_launch_mode).setOnClickListener(v -> {
            Intent intent = new Intent(HelloWorldActivity.this, SecondActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.butStartNormal).setOnClickListener(v -> {
            Intent intent = new Intent(HelloWorldActivity.this, NormalActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.butStartDialog).setOnClickListener(v -> {
            Intent intent = new Intent(HelloWorldActivity.this, DialogActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.but_deep_link).setOnClickListener(v -> {
            Intent intent = new Intent(HelloWorldActivity.this, DeepLinkActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.button_ch3_main).setOnClickListener(v -> {
            Intent intent = new Intent(HelloWorldActivity.this, LayoutTestActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.button_recent_screen).setOnClickListener(v -> {
            Intent intent = new Intent(HelloWorldActivity.this, RecentTaskActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.button_widget_shortcut).setOnClickListener(v -> {
            Intent intent = new Intent(HelloWorldActivity.this, WidgetShortcutActivity.class);
            startActivity(intent);
        });


        ActionBar bar = this.getActionBar();
        if (bar != null) {

        }

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= 21) {
            JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        android.util.Log.i(LOG_TAG, "HelloActivity is started!!");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // 当创建时即onCreate 的参数savedInstanceState不为null时才调用
        android.util.Log.i(LOG_TAG, "onRestoreInstanceState called!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        android.util.Log.i(LOG_TAG, "HelloActivity is resumed!!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        android.util.Log.i(LOG_TAG, "HelloActivity is paused!!");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // onPause -> onSaveInstanceState -> onStop
        super.onSaveInstanceState(outState);
        android.util.Log.i(LOG_TAG, "onSaveInstanceState called!!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        android.util.Log.i(LOG_TAG, "HelloActivity onStop!!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.util.Log.i(LOG_TAG, "HelloActivity is onDestroy!!");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        android.util.Log.i(LOG_TAG, "onConfigurationChanged called!!");
    }
}
