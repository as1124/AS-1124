package com.as1124.architecture.lifecycle;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class MyLocationObserver implements LifecycleObserver {

    private Lifecycle mLifecycle;
    private Context appContext;
    private LocationManager mLocationManager;


    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.i("Location", "[onLocationChanged]: " + location.getProvider() + ", " + location.toString());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i("Location", "[onStatusChanged]: " + status);
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    public MyLocationObserver(Context context, Lifecycle lifecycle) {
        appContext = context;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.mLifecycle = lifecycle;
    }

    @SuppressLint("MissingPermission")
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void start() {
        // connect
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = mLocationManager.getBestProvider(criteria, true);
        mLocationManager.requestLocationUpdates(provider, 2000L, 0.11f, listener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void testLifeResumeState() {
        AlertDialog.Builder builder = new AlertDialog.Builder(appContext);
        builder.setTitle("Lifecycle Aware").setMessage("The Observer method called on Activity Resumed!").show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void testLifePauseState() {
        Log.i("LocationObserver", "The Observer method called on Activity Pause!");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop() {
        // disconnect if connected
        mLocationManager.removeUpdates(listener);
        Log.i("LocationObserver", "The Observer method called on Activity Stop!");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void testLifeDestroyState() {
        Log.i("LocationObserver", "The Observer method called on Activity Destroy!");
    }

}
