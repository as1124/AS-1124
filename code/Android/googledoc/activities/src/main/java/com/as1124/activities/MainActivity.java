package com.as1124.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.as1124.activities.loaders.LoaderMainActivity;
import com.as1124.activities.multiwindow.MultiWindowActivity;
import com.as1124.activities.shortcuts.ShortcutMainActivity;
import com.as1124.activities.widgets.WidgetMainActivity;

/**
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);

        Log.i(LOG_TAG, "[attachBaseContext called]");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        Log.i(LOG_TAG, "[onAttachedToWindow called]");
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = super.onCreateView(parent, name, context, attrs);
        Log.i(LOG_TAG, "[onCreateView called]");
        return view;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        Log.i(LOG_TAG, "[onDetachedFromWindow called]");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(LOG_TAG, "[onCreate called] saved-instance-state == " + (savedInstanceState != null));
        findViewById(R.id.but_alert_dialog).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("测试").setMessage("消息弹框").setIcon(getApplicationInfo().icon)
                    .setPositiveButton("好的", (dialog, which) -> dialog.dismiss())
                    .setNegativeButton("那就这样吧", (dialog, which) -> dialog.dismiss())
                    .setCancelable(false).show();
        });
        findViewById(R.id.but_to_data).setOnClickListener(v -> {
        });
        findViewById(R.id.but_to_loader).setOnClickListener(v -> startActivity(new Intent(this, LoaderMainActivity.class)));
        findViewById(R.id.but_multi_window).setOnClickListener(v -> startActivity(new Intent(this, MultiWindowActivity.class)));
        findViewById(R.id.but_to_shortcut).setOnClickListener(v -> startActivity(new Intent(this, ShortcutMainActivity.class)));
        findViewById(R.id.but_to_widget).setOnClickListener(v -> startActivity(new Intent(this, WidgetMainActivity.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(LOG_TAG, "[onStart called]");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.i(LOG_TAG, "[onRestoreInstanceState called]");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(LOG_TAG, "[onResume called]");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(LOG_TAG, "[onPause called]");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i(LOG_TAG, "[onSaveInstanceState called]");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(LOG_TAG, "[onStop called]");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i(LOG_TAG, "[onRestart called]");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(LOG_TAG, "[onDestroy called]");
    }
}
