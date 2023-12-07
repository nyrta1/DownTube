package com.example.youdown.extractors;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Slf4j
public class YouTubeLinkExtractor {
    public static String extractVideoId(String link) {
        String videoId = null;

        // Regular expression pattern to match YouTube video IDs
        Pattern pattern = Pattern.compile(
//                "(?:https?://)?(?:www\\.)?(?:youtube\\.com/watch\\?v=|youtu\\.be/|youtube\\.com/watch\\?.*?&v=)?([a-zA-Z0-9_-]{11})"
                "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*"
        );

        Matcher matcher = pattern.matcher(link);

        if (matcher.find()) {
            videoId = matcher.group();
        }

        return videoId;
    }

    public static String extractPlaylistId(String url) {
        Pattern pattern = Pattern.compile(
                "(?:watch\\?v=\\w+&list=|playlist\\?list=)?([A-Za-z0-9_-]+)$"
        );
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }
}
