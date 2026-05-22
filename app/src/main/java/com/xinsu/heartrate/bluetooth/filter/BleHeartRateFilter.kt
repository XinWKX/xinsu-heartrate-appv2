package com.xinsu.heartrate.bluetooth.filter

import android.bluetooth.le.ScanResult
import android.os.ParcelUuid

object BleHeartRateFilter {

    private val HEART_RATE_UUID =

        ParcelUuid.fromString(

            "0000180D-0000-1000-8000-00805F9B34FB"
        )

    fun isHeartRateDevice(
        result: ScanResult
    ): Boolean {

        val record =
            result.scanRecord ?: return false

        val uuids =
            record.serviceUuids ?: return false

        return uuids.contains(
            HEART_RATE_UUID
        )
    }
}
