package com.polidea.multiplatformbleadapter.utils.mapper;

import android.os.Build;

import com.polidea.multiplatformbleadapter.AdvertisementData;
import com.polidea.multiplatformbleadapter.ScanResult;
import com.polidea.multiplatformbleadapter.utils.Constants;
import com.polidea.multiplatformbleadapter.utils.IsConnectableCache;

public class RxScanResultToScanResultMapper {

    public ScanResult map(com.polidea.rxandroidble.scan.ScanResult rxScanResult) {
        String macAddress = rxScanResult.getBleDevice().getMacAddress();
        Boolean isConnectable = null;

        // Get isConnectable from cache (populated by native scan callback)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            isConnectable = IsConnectableCache.getInstance().get(macAddress);
        }

        return new ScanResult(
                macAddress,
                rxScanResult.getBleDevice().getName(),
                rxScanResult.getRssi(),
                Constants.MINIMUM_MTU,
                isConnectable,
                null, // overflowServiceUUIDs are not available on Android
                AdvertisementData.parseScanResponseData(rxScanResult.getScanRecord().getBytes()));
    }
}
