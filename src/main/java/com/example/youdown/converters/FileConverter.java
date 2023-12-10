package com.example.youdown.converters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileConverter {
    public static String convertToRoundedMB(Double value) {
        if (value == null) {
            return "N/A";
        }
        return String.format("%.2f MB", Math.round(value * 100.0) / 100.0);
    }

    public static byte[] convertFileToBytes(File file) throws IOException {
        Path filePath = Paths.get(file.getAbsolutePath());
        return Files.readAllBytes(filePath);
    }
}
