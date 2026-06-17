package com.polidea.multiplatformbleadapter;

import androidx.annotation.Nullable;

import java.util.UUID;

public class ScanResult {

    private String deviceId;
    private String deviceName;
    private int rssi;
    private int mtu;
    // Nullable because older Android APIs (<26) and older rxandroidble versions may
    // not expose this.
    @Nullable
    private Boolean isConnectable;
    @Nullable
    private UUID[] overflowServiceUUIDs;
    private AdvertisementData advertisementData;

    public ScanResult(String deviceId, String deviceName, int rssi, int mtu, @Nullable Boolean isConnectable,
            UUID[] overflowServiceUUIDs, AdvertisementData advertisementData) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.rssi = rssi;
        this.mtu = mtu;
        this.isConnectable = isConnectable;
        this.overflowServiceUUIDs = overflowServiceUUIDs;
        this.advertisementData = advertisementData;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getMtu() {
        return mtu;
    }

    public void setMtu(int mtu) {
        this.mtu = mtu;
    }

    // Both getters provided for FlutterBleLib reflection compatibility
    public Boolean isConnectable() {
        return isConnectable;
    }

    public Boolean getIsConnectable() {
        return isConnectable;
    }

    public void setConnectable(@Nullable Boolean connectable) {
        isConnectable = connectable;
    }

    public UUID[] getOverflowServiceUUIDs() {
        return overflowServiceUUIDs;
    }

    public void setOverflowServiceUUIDs(UUID[] overflowServiceUUIDs) {
        this.overflowServiceUUIDs = overflowServiceUUIDs;
    }

    public AdvertisementData getAdvertisementData() {
        return advertisementData;
    }

    public void setAdvertisementData(AdvertisementData advertisementData) {
        this.advertisementData = advertisementData;
    }
}