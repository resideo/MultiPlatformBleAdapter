package com.polidea.multiplatformbleadapter.utils;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.List;

/**
 * Native Android BLE ScanCallback that captures isConnectable information.
 * Works alongside RxAndroidBle to capture native ScanResult data.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NativeScanCallbackWrapper extends ScanCallback {

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        super.onScanResult(callbackType, result);
        captureIsConnectable(result);
    }

    @Override
    public void onBatchScanResults(List<ScanResult> results) {
        super.onBatchScanResults(results);
        for (ScanResult result : results) {
            captureIsConnectable(result);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void captureIsConnectable(ScanResult result) {
        if (result == null || result.getDevice() == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String macAddress = result.getDevice().getAddress();
            boolean isConnectable = result.isConnectable();
            IsConnectableCache.getInstance().put(macAddress, isConnectable);
        }
    }
}
