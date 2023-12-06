package com.example.youdown.converters;

public class TimeConverter {
    public static String convertSecondsToFormattedTime(int seconds) {
        if (seconds < 0) {
            return "00:00";
        }

        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;

        return String.format("%02d:%02d", minutes, remainingSeconds);
    }
}
