package com.as1124.architecture.lifecycle;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.as1124.architecture.R;

/**
 * Lifecycle-Aware Components. 这个库的处理其实很像观察者模式，
 * 以Activity/Fragment生命周期作为事件源，通过注册的Observer进行观察
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class LifeAwareActivity extends Activity implements LifecycleOwner {

    private LifecycleRegistry mLifecycleRegistry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_aware);


        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 111);
            }
        }

        mLifecycleRegistry = new LifecycleRegistry(this);
        getLifecycle().addObserver(new MyLocationObserver(this, getLifecycle()));
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
    }


    @Override
    protected void onStart() {
        super.onStart();

        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LifeAwareActivity.class.getSimpleName(), "Activity is resume!");
        mLifecycleRegistry.markState(Lifecycle.State.RESUMED);
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LifeAwareActivity.class.getSimpleName(), "Activity is stopped!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LifeAwareActivity.class.getSimpleName(), "Activity is destroyed!");
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 111) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "申请定位权限成功", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }
}
