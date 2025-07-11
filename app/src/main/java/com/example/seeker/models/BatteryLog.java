package com.example.seeker.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BatteryLog {
    private int id;
    private int percentage;
    private long timestamp;
    private int isCharging; // 0=false, 1=true

    public BatteryLog(int percentage, long timestamp, boolean isCharging) {
        this.percentage = percentage;
        this.timestamp = timestamp;
        this.isCharging = isCharging ? 1 : 0;
    }

    // Getters
    public String getFormattedTime() {
        return new SimpleDateFormat("HH:mm", Locale.getDefault())
                .format(new Date(timestamp));
    }

    public String getStatus() {
        return isCharging == 1 ? "Charging" : "Discharging";
    }
    public int getPercentage() {
        return percentage;
    }
}