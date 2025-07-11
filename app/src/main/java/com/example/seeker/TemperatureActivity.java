package com.example.seeker;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.seeker.R;
import com.example.seeker.services.TemperatureService;
import com.example.seeker.utils.TemperatureUtils;
import com.example.seeker.utils.VoiceHelper;

public class TemperatureActivity extends AppCompatActivity implements SensorEventListener {
    private TextView tvTemperature;
    private SensorManager sensorManager;
    private Sensor tempSensor;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        tvTemperature = findViewById(R.id.tv_temperature);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Initialize sensor manager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        // Start background service
        startService(new Intent(this, TemperatureService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tempSensor != null) {
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float temperature = event.values[0];
        tvTemperature.setText(String.format("Temperature: %.1f°C", temperature));

        // Check for overheating
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
            vibrator.vibrate(pattern, 0); // Don't repeat (-1 would repeat)
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes if needed
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}