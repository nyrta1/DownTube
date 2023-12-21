package com.example.youdown.services.jsonservice;

import com.alibaba.fastjson.JSONObject;
import com.example.youdown.models.enums.RequestData;

public interface JSONService {
    // JSON data
    JSONObject getJsonData(final String videoId, RequestData typeRequest);
}
