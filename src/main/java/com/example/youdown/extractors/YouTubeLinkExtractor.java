package com.example.youdown.extractors;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Slf4j
public class YouTubeLinkExtractor {
    public static String extractVideoId(String link) {
        // Regex pattern to match YouTube video IDs in various link formats
        Pattern pattern = Pattern.compile("(?:(?:youtube\\.com\\S*(?:\\/(?:\\w*\\?v=|embed\\/|v\\/))|youtu\\.be\\/)([\\w\\-]+))(?:\\S+)?");
        Matcher matcher = pattern.matcher(link);

        if (matcher.find()) {
            return matcher.group(1); // Return the matched video ID
        } else {
            log.warn("Video ID not found: {}", link);
            return null;
        }
    }
}
