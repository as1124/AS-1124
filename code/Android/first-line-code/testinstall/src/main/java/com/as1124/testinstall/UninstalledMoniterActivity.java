package com.as1124.testinstall;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author as-1124（）
 */
public class UninstalledMoniterActivity extends Activity {

    private static final String TAG = "HUANGJW_UNINSTALL";

    private native void init();

    static {
        android.util.Log.i(TAG, "load system library");
        System.loadLibrary("uninstalled_moniter");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.util.Log.i(TAG, "onCreated");

        init();
    }
}
