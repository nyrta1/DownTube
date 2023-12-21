package com.example.youdown.services.jsonservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.youdown.models.ContainerData;
import com.github.kiulian.downloader.downloader.response.ResponseStatus;
import com.github.kiulian.downloader.model.playlist.PlaylistDetails;
import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.formats.AudioFormat;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat;

public class JSONConverter {
    public static JSONObject containerDataToJSON(ContainerData containerData){
        JSONObject JSONDataObject = new JSONObject();

        JSONArray collectionOfVideoWithAudioFormat = extractVideoWithAudioFormats(containerData);
        JSONArray collectionOfVideoFormat = extractVideoFormats(containerData);
        JSONArray collectionOfAudioFormat = extractAudioFormats(containerData);
        JSONObject collectionOfVideoDetails = extractVideoDetails(containerData);
        JSONArray collectionOfPlaylistVideoDetails = extractPlaylistVideoDetails(containerData);
        JSONObject collectionOfPlaylistDetails = extractPlaylistDetails(containerData);
        ResponseStatus responseStatus = containerData.getResponseStatus();

        if (collectionOfVideoWithAudioFormat != null && !collectionOfVideoWithAudioFormat.isEmpty()) {
            JSONDataObject.put("videoWithAudioFormats", collectionOfVideoWithAudioFormat);
        }
        if (collectionOfVideoFormat != null && !collectionOfVideoFormat.isEmpty()) {
            JSONDataObject.put("videoFormats", collectionOfVideoFormat);
        }
        if (collectionOfAudioFormat != null && !collectionOfAudioFormat.isEmpty()) {
            JSONDataObject.put("audioFormats", collectionOfAudioFormat);
        }
        if (collectionOfVideoDetails != null && !collectionOfVideoDetails.isEmpty()) {
            JSONDataObject.put("details", collectionOfVideoDetails);
        }
        if (collectionOfPlaylistVideoDetails != null && !collectionOfPlaylistVideoDetails.isEmpty()) {
            JSONDataObject.put("playlistVideoDetails", collectionOfPlaylistVideoDetails);
        }
        if (collectionOfPlaylistDetails != null && !collectionOfPlaylistDetails.isEmpty()) {
            JSONDataObject.put("playlistDetails", collectionOfPlaylistDetails);
        }
        if (responseStatus != null) {
            JSONDataObject.put("status", responseStatus);
        }

        return JSONDataObject;
    }

    private static JSONArray extractVideoWithAudioFormats(ContainerData containerData) {
        if (containerData.getVideoWithAudioFormats() == null || containerData.getVideoWithAudioFormats().isEmpty()) {
            return null;
        }

        JSONArray collectionOfVideoWithAudioFormat = new JSONArray();
        for (VideoWithAudioFormat item : containerData.getVideoWithAudioFormats()) {
            JSONObject videoWithAudioFormats = createJSONObjectForVideoWithAudioFormat(item);
            collectionOfVideoWithAudioFormat.add(videoWithAudioFormats);
        }

        return collectionOfVideoWithAudioFormat;
    }

    private static JSONArray extractVideoFormats(ContainerData containerData) {
        if (containerData.getVideoFormats() == null || containerData.getVideoFormats().isEmpty()) {
            return null;
        }

        JSONArray collectionOfVideoFormat = new JSONArray();
        for (VideoFormat item : containerData.getVideoFormats()) {
            JSONObject videoFormats = createJSONObjectForVideoFormat(item);
            collectionOfVideoFormat.add(videoFormats);
        }

        return collectionOfVideoFormat;
    }

    private static JSONArray extractAudioFormats(ContainerData containerData) {
        if (containerData.getAudioFormats() == null || containerData.getAudioFormats().isEmpty()) {
            return null;
        }

        JSONArray collectionOfAudioFormat = new JSONArray();
        for (AudioFormat item : containerData.getAudioFormats()) {
            JSONObject audioFormats = createJSONObjectForAudioFormat(item);
            collectionOfAudioFormat.add(audioFormats);
        }
        return collectionOfAudioFormat;
    }

    private static JSONArray extractPlaylistVideoDetails(ContainerData containerData) {
        if (containerData.getPlaylistVideoDetails() == null || containerData.getPlaylistVideoDetails().isEmpty()) {
            return null;
        }

        JSONArray collectionOfPlaylistVideoDetails = new JSONArray();
        for (PlaylistVideoDetails item : containerData.getPlaylistVideoDetails()) {
            JSONObject playlistVideoDetails = createJSONObjectForPlaylistVideoDetails(item);
            collectionOfPlaylistVideoDetails.add(playlistVideoDetails);
        }

        return collectionOfPlaylistVideoDetails;
    }

    private static JSONObject extractVideoDetails(ContainerData containerData) {
        if (containerData.getDetails() == null) {
            return null;
        }

        VideoDetails details = containerData.getDetails();
        JSONObject collectionOfVideoDetails = new JSONObject();

        collectionOfVideoDetails.put("thumbnails", details.thumbnails().get((int) Math.sqrt(details.thumbnails().size())));
        collectionOfVideoDetails.put("title", details.title());
        collectionOfVideoDetails.put("viewCount", details.viewCount());
        collectionOfVideoDetails.put("author", details.author());
        collectionOfVideoDetails.put("lengthSeconds", details.lengthSeconds());
        collectionOfVideoDetails.put("description", details.description());
        collectionOfVideoDetails.put("videoID", details.videoId());

        return collectionOfVideoDetails;
    }

    private static JSONObject createJSONObjectForVideoWithAudioFormat(VideoWithAudioFormat item) {
        JSONObject videoWithAudioFormats = new JSONObject();

        videoWithAudioFormats.put("qualityLabel", item.qualityLabel());
        videoWithAudioFormats.put("extension", item.extension().value());
        videoWithAudioFormats.put("contentLength", item.contentLength());
        videoWithAudioFormats.put("url", item.url());

        return videoWithAudioFormats;
    }

    private static JSONObject createJSONObjectForVideoFormat(VideoFormat item) {
        JSONObject videoFormats = new JSONObject();

        videoFormats.put("qualityLabel", item.qualityLabel());
        videoFormats.put("extension", item.extension().value());
        videoFormats.put("contentLength", item.contentLength());
        videoFormats.put("url", item.url());

        return videoFormats;
    }

    private static JSONObject createJSONObjectForAudioFormat(AudioFormat item) {
        JSONObject audioFormats = new JSONObject();

        audioFormats.put("audioQuality", item.audioQuality());
        audioFormats.put("extension", item.extension().value());
        audioFormats.put("contentLength", item.contentLength());
        audioFormats.put("url", item.url());

        return audioFormats;
    }

    private static JSONObject createJSONObjectForPlaylistVideoDetails(PlaylistVideoDetails item) {
        JSONObject playlistVideoDetails = new JSONObject();

        playlistVideoDetails.put("thumbnails", item.thumbnails().get((int) Math.sqrt(item.thumbnails().size())));
        playlistVideoDetails.put("index", item.index());
        playlistVideoDetails.put("title", item.title());
        playlistVideoDetails.put("videoId", item.videoId());

        return playlistVideoDetails;
    }

    private static JSONObject extractPlaylistDetails(ContainerData containerData) {
        if (containerData.getPlaylistDetails() == null) {
            return null;
        }

        PlaylistDetails details = containerData.getPlaylistDetails();
        JSONObject collectionOfPlaylistDetails = new JSONObject();

        collectionOfPlaylistDetails.put("title", details.title());
        collectionOfPlaylistDetails.put("viewCount", details.viewCount());
        collectionOfPlaylistDetails.put("author", details.author());
        collectionOfPlaylistDetails.put("videoCount", details.videoCount());

        return collectionOfPlaylistDetails;
    }
}
