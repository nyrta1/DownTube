package com.example.youdown.converters;

public class FileSizeConverter {
    public static String convertToRoundedMB(Double value) {
        if (value == null) {
            return "N/A";
        }
        return String.format("%.2f MB", Math.round(value * 100.0) / 100.0);
    }
}
