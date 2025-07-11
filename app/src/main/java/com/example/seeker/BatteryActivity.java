package com.example.seeker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.seeker.R;
import com.example.seeker.services.BatteryService;
import com.example.seeker.services.BatteryMonitoringService;
import com.example.seeker.utils.BatteryUtils;
import com.example.seeker.utils.VoiceHelper;

public class BatteryActivity extends AppCompatActivity {
    private TextView tvBatteryPercent, tvBatteryStatus, tvTimeRemaining, tvChargingTime;
    private BroadcastReceiver batteryReceiver;

    // For service updates
    private BroadcastReceiver batteryUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int percent = intent.getIntExtra("percent", 0);
            boolean isCharging = intent.getBooleanExtra("isCharging", false);
            updateBatteryUI(percent, isCharging);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        // Initialize views
        tvBatteryPercent = findViewById(R.id.tv_battery_percent);
        tvBatteryStatus = findViewById(R.id.tv_battery_status);
        tvTimeRemaining = findViewById(R.id.tv_time_remaining);
        tvChargingTime = findViewById(R.id.tv_charging_time);

        // Register battery receiver
        batteryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int batteryPercent = (int) ((level / (float) scale) * 100);
                boolean isCharging = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1) == BatteryManager.BATTERY_STATUS_CHARGING;

                updateBatteryUI(batteryPercent, isCharging);
                checkBatteryAlerts(batteryPercent, isCharging);
            }
        };

        // Corrected intent filter registration
        registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));


        // Start battery monitoring services
        startService(new Intent(this, BatteryService.class));
        startService(new Intent(this, BatteryMonitoringService.class));
    }

    private void updateBatteryUI(int percent, boolean isCharging) {
        tvBatteryPercent.setText(percent + "%");
        tvBatteryStatus.setText(isCharging ? "Status: Charging" : "Status: Discharging");

        // Calculate time remaining
        String timeRemaining = BatteryUtils.calculateTimeRemaining(percent, isCharging);
        tvTimeRemaining.setText("Time remaining: " + timeRemaining);

        // Show charging time if charging
        if (isCharging) {
            String chargeTime = BatteryUtils.calculateChargeTime(percent);
            tvChargingTime.setText("Full charge in: " + chargeTime);
            tvChargingTime.setVisibility(View.VISIBLE);
        } else {
            tvChargingTime.setVisibility(View.GONE);
        }
    }

    private void checkBatteryAlerts(int percent, boolean isCharging) {
        if (percent <= 5) {
            VoiceHelper.playAlert(this, R.raw.battery_low, "Master, battery low");
            vibrateDevice(500);
        } else if (percent >= 100 && isCharging) {
            VoiceHelper.playAlert(this, R.raw.battery_full, "Battery full");
            vibrateDevice(300);
        } else if (isCharging) {
            String chargeTime = BatteryUtils.calculateChargeTime(percent);
            VoiceHelper.speak(this, "Full charge in " + chargeTime);
        }
    }

    private void vibrateDevice(long milliseconds) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(milliseconds);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister receivers safely
        try {
            unregisterReceiver(batteryReceiver);
        } catch (IllegalArgumentException e) {
            // Receiver was not registered
        }
        try {
            unregisterReceiver(batteryUpdateReceiver);
        } catch (IllegalArgumentException e) {
            // Receiver was not registered
        }
    }
}