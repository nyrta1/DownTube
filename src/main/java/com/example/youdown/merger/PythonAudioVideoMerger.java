package com.example.youdown.merger;

import com.example.youdown.constants.Constants;
import com.example.youdown.storage.FileFinder;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class PythonAudioVideoMerger {
    private final static String PYTHON_SCRIPT_PATH = "src/main/python/merge_audio_video.py";
    public static File merge(
            String audioPath,
            String videoPath,
            String outputVideoPath ) {
        log.info("MERGE STARTED");

        List<String> command = Arrays.asList(
                "cmd.exe", "/C", "start",
                "python",
                PYTHON_SCRIPT_PATH,
                audioPath,
                videoPath,
                outputVideoPath
        );

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            Process process = processBuilder.start();

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                log.info("Audio and video merge successful!");
                String filename = outputVideoPath.substring(outputVideoPath.lastIndexOf('/') + 1);
                return FileFinder.findFileInDirectory(Constants.directoryPathForMediaPackage, filename);
            } else {
                log.error("Audio and video merge failed!");
                return null;
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
