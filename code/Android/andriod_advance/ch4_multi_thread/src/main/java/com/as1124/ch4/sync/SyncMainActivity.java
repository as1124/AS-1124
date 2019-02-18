package com.as1124.ch4.sync;

import android.app.Activity;
import android.os.Bundle;

import com.as1124.ch4.R;

/**
 * 线程同步问题
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class SyncMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_main);
    }
}
