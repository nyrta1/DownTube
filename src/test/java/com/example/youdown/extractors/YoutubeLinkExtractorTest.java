package com.example.youdown.extractors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(YouTubeLinkExtractor.class)
public class YoutubeLinkExtractorTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testExtractVideoIdByLink() {
        String VIDEO_ID = "6stlCkUDG_s";
        String[] links = {
            "https://youtu.be/6stlCkUDG_s",
            "https://www.youtube.com/watch?v=6stlCkUDG_s",
            "https://youtu.be/6stlCkUDG_s?list=PL4Gr5tOAPttLOY9IrWVjJlv4CtkYI5cI_",
            "https://www.youtube.com/watch?v=6stlCkUDG_s&list=PL4Gr5tOAPttLOY9IrWVjJlv4CtkYI5cI_"
        };

        for (String link : links) {
            String extractedId = YouTubeLinkExtractor.extractVideoId(link);
            assertEquals(VIDEO_ID, extractedId);
        }
    }
}
