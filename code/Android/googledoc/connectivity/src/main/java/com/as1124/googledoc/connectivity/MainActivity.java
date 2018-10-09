package com.as1124.googledoc.connectivity;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.as1124.googledoc.connectivity.bluetooth.BluetoothMainActivity;

/**
 * 手机网络相关学习
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        android.util.Log.i("1111111", "MainActivity OnCreated!!");
//        FragmentManager manager = getFragmentManager();
//        if (manager != null) {
//            BlankFragment.getInstance(manager);
//        }

        findViewById(R.id.but_get_network).setOnClickListener(this);
        findViewById(R.id.but_to_bluetooth).setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE}, 1001);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.but_get_network:
                showNetworkInfo();
                break;
            case R.id.but_to_bluetooth:
                Intent intent = new Intent(this, BluetoothMainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        android.util.Log.i("11111", "Configuration Changed!!");
    }

    private void showNetworkInfo() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork == null) {
            Log.i("Connectivity", "无可用网络链接");
        }
        if (Build.VERSION.SDK_INT >= 21) {
            Network[] allType = manager.getAllNetworks();
            for (Network network : allType) {
                NetworkInfo info = manager.getNetworkInfo(network);
                if (info != null) {
                    Log.i("Connectivity", info.getTypeName() + "==" + info.isAvailable() + ", isConnected=" + info.isConnected());
                }
            }
        }
    }
}
