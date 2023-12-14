package com.example.youdown.merger;

import com.example.youdown.constants.Constants;
import com.example.youdown.storage.FileFinder;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class FFmpegAudioVideoMerger {
    public static File merge(
            String audioPath,
            String videoPath,
            String outputVideoPath) {
        log.info("MERGE STARTED");

        // Construct the FFmpeg command
        List<String> command = Arrays.asList(
                "cmd.exe", "/C", "start",
                "C:/ffmpeg/bin/ffmpeg",
                "-i", audioPath,
                "-i", videoPath,
                "-c:v", "copy",
                "-c:a", "aac",
                "-strict", "experimental",
                Constants.directoryPathForMediaPackage + outputVideoPath
        );

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            Process process = processBuilder.start();

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while ((line = inputReader.readLine()) != null) {
                log.info(line);
            }
            while ((line = errorReader.readLine()) != null) {
                log.error(line);
            }

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                log.info("Audio and video merge successful!");
                return FileFinder.findFileInDirectory(Constants.directoryPathForMediaPackage, outputVideoPath);
            } else {
                log.error("Audio and video merge failed!");
                return null;
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
