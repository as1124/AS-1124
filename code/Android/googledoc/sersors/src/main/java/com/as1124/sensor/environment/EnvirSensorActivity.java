package com.as1124.sensor.environment;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.as1124.selflib.WindowUtils;
import com.as1124.sensor.R;

/**
 * 环境因素传感器
 * <ul>
 * <li>周围空气温度传感器</li>
 * <li>压力传感器</li>
 * <li>湿度传感器</li>
 * <li>光照强度传感器</li>
 * </ul>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class EnvirSensorActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor lightSensor;
    private Sensor pressureSensor;
    private Sensor temperatureSensor;
    private Sensor humiditySensor;

    private TextView infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envir_sensor);
        WindowUtils.fullScreen(this);

        infoText = findViewById(R.id.text_environment_sensor);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (lightSensor != null) {
            Log.i("Environment-Sensor", "设备有光照传感器");
        }
        pressureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if (pressureSensor != null) {
            Log.i("Environment-Sensor", "设备有压力监测传感器");
        }
        temperatureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (temperatureSensor != null) {
            Log.i("Environment-Sensor", "设备有温度监测传感器");
        }
        humiditySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if (humiditySensor != null) {
            Log.i("Environment-Sensor", "设备有环境湿度传感器");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (lightSensor != null) {
            mSensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (pressureSensor != null) {
            mSensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (temperatureSensor != null) {
            mSensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (humiditySensor != null) {
            mSensorManager.registerListener(this, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
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
        int type = event.sensor.getType();
        switch (type) {
            case Sensor.TYPE_LIGHT:
                Log.i("Environment-Sensor", "光照强度==" + event.values[0]);
                infoText.setText("当前光照强度===" + event.values[0]);
                break;
            case Sensor.TYPE_PRESSURE:
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                break;
            default:
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
