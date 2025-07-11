package com.example.seeker.utils;

public class BatteryUtils {
    public static String calculateTimeRemaining(int percent, boolean isCharging) {
        if (isCharging) {
            return "Calculating...";
        }

        // Simple estimation (1% = 2 minutes of usage)
        int minutesLeft = percent * 2;
        int hours = minutesLeft / 60;
        int minutes = minutesLeft % 60;

        return hours > 0 ? hours + "h " + minutes + "m" : minutes + "m";
    }

    public static String calculateChargeTime(int percent) {
        // Simple estimation (1% = 1.5 minutes to charge)
        int minutesLeft = (100 - percent) * 3 / 2;
        int hours = minutesLeft / 60;
        int minutes = minutesLeft % 60;

        return hours > 0 ? hours + "h " + minutes + "m" : minutes + "m";
    }
}