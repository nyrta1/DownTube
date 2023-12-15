package com.example.youdown.models;

import com.example.youdown.enums.FileType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "MediaFiles")
@Data
@NoArgsConstructor
public class MediaFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "video_id")
    private String videoId;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "video_title")
    private String videoTitle;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "file_quality")
    private String fileQuality;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private FileType fileType;
}
