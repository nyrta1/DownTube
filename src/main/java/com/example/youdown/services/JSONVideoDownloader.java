package com.example.youdown.services;

import com.alibaba.fastjson.JSONObject;
import com.example.youdown.enums.RequestData;

public interface JSONVideoDownloader {
    // JSON data
    JSONObject getJsonData(final String videoId, RequestData typeRequest);
}
