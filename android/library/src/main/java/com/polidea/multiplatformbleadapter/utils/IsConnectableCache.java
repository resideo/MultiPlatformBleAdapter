package com.polidea.multiplatformbleadapter.utils;

import androidx.annotation.Nullable;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache for storing isConnectable information from native Android ScanResults.
 * This works around the limitation that RxAndroidBle doesn't expose the native ScanResult.
 */
public class IsConnectableCache {
    private static final IsConnectableCache INSTANCE = new IsConnectableCache();
    private final ConcurrentHashMap<String, Boolean> cache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> timestamps = new ConcurrentHashMap<>();
    private static final long CACHE_TTL_MS = 30000; // 30 seconds

    private IsConnectableCache() {}

    public static IsConnectableCache getInstance() {
        return INSTANCE;
    }

    public void put(String macAddress, @Nullable Boolean isConnectable) {
        if (macAddress != null && isConnectable != null) {
            String key = macAddress.toUpperCase();
            cache.put(key, isConnectable);
            timestamps.put(key, System.currentTimeMillis());
        }
    }

    @Nullable
    public Boolean get(String macAddress) {
        if (macAddress == null) {
            return null;
        }

        String key = macAddress.toUpperCase();
        Long timestamp = timestamps.get(key);

        if (timestamp != null && System.currentTimeMillis() - timestamp > CACHE_TTL_MS) {
            cache.remove(key);
            timestamps.remove(key);
            return null;
        }

        return cache.get(key);
    }

    public void clear() {
        cache.clear();
        timestamps.clear();
    }
}
