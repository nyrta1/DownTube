package com.example.youdown.storage;

import com.example.youdown.models.ContainerData;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class HashRamMemory {
    private final HashMap<String, ContainerData> containerDataHashMap = new HashMap<>();
    private HashRamMemory() {};

    private static class HashRamMemoryHolder {
        public static final HashRamMemory HOLDER_INSTANCE = new HashRamMemory();
    }

    public static HashRamMemory getInstance() {
        return HashRamMemoryHolder.HOLDER_INSTANCE;
    }

    public ContainerData getData(final String videoId) {
        ContainerData data = containerDataHashMap.get(videoId);

        if (data == null || data.isEmpty()) {
            log.info("Data not found or empty for dataID: {}", videoId);
        } else {
            log.info("Getting data for dataID: {}", videoId);
        }

        return data;
    }

    public void saveData(final String videoId, final ContainerData containerData) {
        log.info("Saving data for dataID: {}", videoId);
        containerDataHashMap.put(videoId, containerData);
    }
}
