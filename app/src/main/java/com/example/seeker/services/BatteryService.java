package com.example.seeker.services;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.os.Vibrator;
import androidx.core.app.NotificationCompat;
import com.example.seeker.R;
import com.example.seeker.utils.NotificationHelper;
import com.example.seeker.utils.VoiceHelper;
import com.example.seeker.database.DBHelper;

public class BatteryService extends Service {
    private static final int NOTIFICATION_ID = 101;
    private BatteryReceiver batteryReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(NOTIFICATION_ID, NotificationHelper.createBatteryNotification(this));
        registerBatteryReceiver();
    }

    private void registerBatteryReceiver() {
        batteryReceiver = new BatteryReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(batteryReceiver, filter);
    }

    private class BatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            float batteryPct = (level / (float) scale) * 100;
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            handleBatteryAlerts(context, (int) batteryPct, isCharging);
            sendUpdateToActivity(context, (int) batteryPct, isCharging);
        }

        private void handleBatteryAlerts(Context context, int percent, boolean isCharging) {
            if (percent <= 5) {
                VoiceHelper.playAlert(context, R.raw.battery_low, "Master, battery low");
                vibrate(context, 500);
            } else if (percent >= 100 && isCharging) {
                VoiceHelper.playAlert(context, R.raw.battery_full, "Battery full");
                vibrate(context, 300);
            }
        }

        private void sendUpdateToActivity(Context context, int percent, boolean isCharging) {
            Intent updateIntent = new Intent("BATTERY_UPDATE");
            updateIntent.putExtra("percent", percent);
            updateIntent.putExtra("isCharging", isCharging);
            context.sendBroadcast(updateIntent);
        }

        private void vibrate(Context context, long milliseconds) {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null && vibrator.hasVibrator()) {
                vibrator.vibrate(milliseconds);
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (batteryReceiver != null) {
            unregisterReceiver(batteryReceiver);
        }
    }
    // Add to BatteryService.java
    private void logBatteryStatus(int percent, boolean isCharging) {
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.addLog(percent, System.currentTimeMillis(), isCharging);
        dbHelper.close();
    }

}