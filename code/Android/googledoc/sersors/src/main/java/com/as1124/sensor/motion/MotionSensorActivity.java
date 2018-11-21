package com.as1124.sensor.motion;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.as1124.sensor.R;

import java.text.MessageFormat;

/**
 * 运动传感器示例
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MotionSensorActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_sensor);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        findViewById(R.id.but_gravity_sensor).setOnClickListener(v -> monitorGravity());
        findViewById(R.id.but_fingerprint_sensor).setOnClickListener(v -> detectFingerprint());
        findViewById(R.id.but_step_counter_sensor).setOnClickListener(v -> fetchSteps());
        findViewById(R.id.but_step_detect_sensor).setOnClickListener(v -> detectStepMoving());
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_GRAVITY:
                Log.i("[Sensor-Gravity]", MessageFormat.format("[x={0}],[y={1}],[z={2}]",
                        event.values[0], event.values[1], event.values[2]));
                break;
            case Sensor.TYPE_STEP_COUNTER:
                Log.i("[Sensor-Step-Counter]", "总步伐数==" + event.values[0]);
                break;
            case Sensor.TYPE_STEP_DETECTOR:
                Log.i("[Sensor-Step-Counter]", "步数发生变化==" + event.values[0] + ", 计数精度===" + event.accuracy);
                break;
            default:
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void monitorGravity() {
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        if (sensor != null) {
            mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private void detectFingerprint() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            if (fingerprintManager != null && fingerprintManager.isHardwareDetected()) {
                Toast.makeText(this, "设备支持指纹识别", Toast.LENGTH_LONG).show();
                return;
            }
        }
        Toast.makeText(this, "设备不支持指纹识别", Toast.LENGTH_SHORT).show();
    }

    private void fetchSteps() {
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (sensor != null) {
            mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "设备上没有计步传感器", Toast.LENGTH_SHORT).show();
        }
    }

    private void detectStepMoving() {
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (sensor != null) {
            mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "设备不支持计步监测", Toast.LENGTH_SHORT).show();
        }
    }
}
