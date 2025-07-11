package com.example.seeker.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.example.seeker.R;

public class NotificationHelper {
    private static final String CHANNEL_ID = "battery_monitor_channel";
    private static final String CHANNEL_NAME = "Battery Monitor";
    private static final String CHANNEL_DESC = "Shows battery monitoring status";

    public static Notification createBatteryNotification(Context context) {
        createNotificationChannel(context);

        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Battery Monitor")
                .setContentText("Monitoring battery status")
                .setSmallIcon(R.drawable.ic_battery)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .build();
    }

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription(CHANNEL_DESC);
            channel.setShowBadge(false);

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}