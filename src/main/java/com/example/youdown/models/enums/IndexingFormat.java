package com.example.youdown.models.enums;

public enum IndexingFormat {
    AUDIO("A"),
    VIDEO("V"),
    VIDEO_WITH_AUDIO("VA"),
    MERGED_AUDIO_WITH_VIDEO("MVA");

    private final String formatCode;

    IndexingFormat(String formatCode) {
        this.formatCode = formatCode;
    }

    public String getFormatCode() {
        return formatCode;
    }

    public static IndexingFormat fromFormatCode(String formatCode) {
        for (IndexingFormat format : IndexingFormat.values()) {
            if (format.getFormatCode().equalsIgnoreCase(formatCode)) {
                return format;
            }
        }
        throw new IllegalArgumentException("Unknown format code: " + formatCode);
    }
}
