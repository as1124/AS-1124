package com.as1124.sensor.position;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import com.as1124.selflib.WindowUtils;
import com.as1124.sensor.R;

/**
 * 位置传感器, 主要有以下三个
 * <ol>
 * <li>地磁传感器</li>
 * <li>加速度器</li>
 * <li>接近传感器（proximity sensor），如电话时监测是否靠近听筒</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class PositionSensorActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_sensor);
        WindowUtils.fullScreen(this);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        if (sensor != null) {
            Log.i("Position-Sensor", "有类型为 GAME_ROTATION_VECTOR 的传感器");
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
