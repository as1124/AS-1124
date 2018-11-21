package com.as1124.sensor;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.as1124.selflib.WindowUtils;
import com.as1124.sensor.motion.MotionSensorActivity;

import java.util.List;

/**
 * 共计三大类传感器
 * <ol>
 * <li>Motion Sensors</li>
 * <li>Environmental Sensors</li>
 * <li>Position Sensors</li>
 * </ol>Android 9及更高版本对传感器数据采集有以下限制：
 * <ul>
 * <li>Sensors that use the continuous reporting mode, such as accelerometers
 * and gyroscopes, don't receive events</li>
 * <li>Sensors that use the on-change or one-shot reporting modes don't receive events</li>
 * </ul>
 * So it is best to detect sensor events either when your app is in the foreground
 * or as part of a foreground service!
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mLightSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowUtils.fullScreen(this);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_LIGHT);
        if (sensors.size() > 0) {
            mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        } else {
            Toast.makeText(this, "当前手机没有光照传感器", Toast.LENGTH_SHORT).show();
        }

        findViewById(R.id.but_list_sensor).setOnClickListener(v -> listSensors());
        findViewById(R.id.but_light_sensor).setOnClickListener(v -> {
            if (mLightSensor != null) {
                mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        });
        findViewById(R.id.but_motion_sensor).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, MotionSensorActivity.class)));
        findViewById(R.id.but_position_sensor).setOnClickListener(v -> {

        });
        findViewById(R.id.but_environmental_sensor).setOnClickListener(v -> {

        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // 传感器检测的数据发生改变或者定时数据采集时触发
        Log.i("[Sensor]", "light == " + event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 传感器精度值发生改变时触发
    }

    private void listSensors() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> allSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (int i = 0; i < allSensors.size(); i++) {
            Sensor sensor = allSensors.get(i);
            Log.i("[Sensor]", sensor.getStringType() + "==" + sensor.getName()
                    + ", vendor = " + sensor.getVendor() + ", version=" + sensor.getVersion());
        }
    }
}
