package com.example.seeker.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Vibrator;
import com.example.seeker.R;
import com.example.seeker.utils.VoiceHelper;

public class BatteryReceiver extends BroadcastReceiver {
    private static final int LOW_BATTERY_THRESHOLD = 5;
    private static final int FULL_BATTERY_THRESHOLD = 100;
    private static final long LOW_BATTERY_VIBRATION = 500;
    private static final long FULL_BATTERY_VIBRATION = 300;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;

        // Get battery data
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

        // Calculate percentage and charging status
        int batteryPercent = (int) ((level / (float) scale) * 100);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        // Handle alerts and updates
        handleBatteryAlerts(context, batteryPercent, isCharging, intent);
        sendBatteryUpdate(context, batteryPercent, isCharging);
    }

    private void handleBatteryAlerts(Context context, int percent, boolean isCharging, Intent intent) {
        // Low battery alert
        if (percent <= LOW_BATTERY_THRESHOLD) {
            VoiceHelper.playAlert(context, R.raw.battery_low, "Master, battery low");
            vibrate(context, LOW_BATTERY_VIBRATION);
        }
        // Full battery alert
        else if (percent >= FULL_BATTERY_THRESHOLD && isCharging) {
            VoiceHelper.playAlert(context, R.raw.battery_full, "Battery full");
            vibrate(context, FULL_BATTERY_VIBRATION);
        }
        // Charging started announcement
        else if (isCharging && Intent.ACTION_POWER_CONNECTED.equals(intent.getAction())) {
            VoiceHelper.speak(context, "Charging started. Current battery level: " + percent + " percent");
        }
    }

    private void sendBatteryUpdate(Context context, int percent, boolean isCharging) {
        Intent updateIntent = new Intent("BATTERY_UPDATE");
        updateIntent.putExtra("percent", percent);
        updateIntent.putExtra("isCharging", isCharging);
        context.sendBroadcast(updateIntent);
    }

    private void vibrate(Context context, long milliseconds) {
        try {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null && vibrator.hasVibrator()) {
                vibrator.vibrate(milliseconds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}