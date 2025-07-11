package com.example.seeker.utils;

public class TemperatureUtils {
    // Threshold temperature in Celsius (adjust as needed)
    private static final float OVERHEAT_THRESHOLD = 1.0f;

    public static boolean isDeviceOverheated(float temperature) {
        return temperature >= OVERHEAT_THRESHOLD;
    }

    public static String formatTemperature(float temp) {
        return String.format("%.1f°C", temp);
    }
}