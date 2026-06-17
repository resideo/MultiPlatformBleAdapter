package com.polidea.multiplatformbleadapter.utils.mapper;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.polidea.multiplatformbleadapter.Device;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDevice;

public class RxBleDeviceToDeviceMapper {

    public Device map(@NonNull RxBleDevice rxDevice, @Nullable RxBleConnection connection) {
        Device device = new Device(rxDevice.getMacAddress(), rxDevice.getName(), rxDevice.getBluetoothDevice());
        if (connection != null) {
            device.setMtu(connection.getMtu());
        }
        return device;
    }
}
