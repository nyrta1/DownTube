package com.example.youdown.converters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileConverter {
    public static byte[] convertFileToBytes(File file) throws IOException {
        Path filePath = Paths.get(file.getAbsolutePath());
        return Files.readAllBytes(filePath);
    }
}
