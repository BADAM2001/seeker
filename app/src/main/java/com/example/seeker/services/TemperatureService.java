package com.example.seeker.services;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.Vibrator;
import com.example.seeker.R;
import com.example.seeker.utils.TemperatureUtils;
import com.example.seeker.utils.VoiceHelper;

public class TemperatureService extends Service implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor tempSensor;
    private Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        if (tempSensor != null) {
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float temperature = event.values[0];
        if (TemperatureUtils.isDeviceOverheated(temperature)) {
            triggerOverheatAlert();
        }
    }

    private void triggerOverheatAlert() {
        // Voice alert
        VoiceHelper.playAlert(this, R.raw.overheat_warning,
                "Warning! Phone is overheating. Please switch off for some time.");

        // Vibration feedback
        if (vibrator != null && vibrator.hasVibrator()) {
            long[] pattern = {0, 1000, 500, 1000}; // Vibrate pattern
            vibrator.vibrate(pattern, -1); // Repeat indefinitely
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}