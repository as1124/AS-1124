package com.as1124.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;

/**
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

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
