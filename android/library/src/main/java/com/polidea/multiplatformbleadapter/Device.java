package com.polidea.multiplatformbleadapter;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.UUID;

public class Device {

    private String id;
    private String name;
    private BluetoothDevice bluetoothDevice;
    @Nullable
    private Integer rssi;
    @Nullable
    private Integer mtu;
    @Nullable
    private List<Service> services;

    public Device(String id, String name, BluetoothDevice bluetoothDevice) {
        this.id = id;
        this.name = name;
        this.bluetoothDevice = bluetoothDevice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    @Nullable
    public Integer getRssi() {
        return rssi;
    }

    public void setRssi(Integer rssi) {
        this.rssi = rssi;
    }

    @Nullable
    public Integer getMtu() {
        return mtu;
    }

    public void setMtu(Integer mtu) {
        this.mtu = mtu;
    }

    @Nullable
    public List<Service> getServices() {
        return services;
    }

    public void setServices(@Nullable List<Service> services) {
        this.services = services;
    }

    @Nullable
    public Service getServiceByUUID(@NonNull UUID uuid) {
        if (services == null) {
            return null;
        }

        for (Service service : services) {
            if (uuid.equals(service.getUuid()))
                return service;
        }
        return null;
    }
}
