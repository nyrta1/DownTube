package com.example.youdown.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class FileFinder {

    public static File findFileInDirectory(String directoryPath, String fileNameToFind) throws IOException {
        Path start = Paths.get(directoryPath);

        try (Stream<Path> stream = Files.walk(start)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith(fileNameToFind))
                    .findFirst()
                    .map(Path::toFile)
                    .orElse(null);
        }
    }
}
