/*
* This is created for the need to collect all the data in one class
* Functions using this class:
* 1. Collect all data in one class
* 2. isEmpty() method to check whether any list is empty or not
*/

package com.example.youdown.models;

import com.github.kiulian.downloader.model.playlist.PlaylistDetails;
import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.formats.AudioFormat;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContainerData {
    // get videos formats only with audio
    List<VideoWithAudioFormat> videoWithAudioFormats;

    // get all videos formats (may contain better quality but without audio)
    List<VideoFormat> videoFormats;

    // get audio formats
    List<AudioFormat> audioFormats;

    // video details
    VideoDetails details;

    // FOR PLAYLIST

    // get playlist information
    List<PlaylistVideoDetails> playlistVideoDetails;

    // playlist details
    PlaylistDetails playlistDetails;

    public ContainerData(List<VideoWithAudioFormat> videoWithAudioFormats, List<VideoFormat> videoFormats, List<AudioFormat> audioFormats, VideoDetails details) {
        this.videoWithAudioFormats = videoWithAudioFormats;
        this.videoFormats = videoFormats;
        this.audioFormats = audioFormats;
        this.details = details;
    }

    public ContainerData(List<PlaylistVideoDetails> playlistVideoDetails, PlaylistDetails playlistDetails) {
        this.playlistVideoDetails = playlistVideoDetails;
        this.playlistDetails = playlistDetails;
    }

    public boolean isEmpty() {
        return videoWithAudioFormats.isEmpty() &&
                videoFormats.isEmpty() &&
                audioFormats.isEmpty() &&
                details == null;
    }
}
