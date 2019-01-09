package com.as1124.googledoc.connectivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import java.util.List;

/**
 * 配置APP网络使用策略的Activity
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class NetworkSettingActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onBuildHeaders(List<Header> target) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register a listener whenever a key changes
        getSharedPreferences("as1124_connectivity", Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Unregister the listener set in onResume(). It's best practice to unregister listeners when your
        // APP isn't using them to cut down on unnecessary system overhead.
        getSharedPreferences("as1124_connectivity", Context.MODE_PRIVATE).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // When the user changes the preference selection, method called
        // Maybe need to restart you main-activity.
    }
}
