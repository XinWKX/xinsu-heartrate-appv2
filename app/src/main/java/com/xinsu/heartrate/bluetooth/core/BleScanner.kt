package com.xinsu.heartrate.bluetooth.core

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.*
import android.os.ParcelUuid
import com.xinsu.heartrate.bluetooth.model.HeartRateDevice

class BleScanner(

    private val adapter:
    BluetoothAdapter
) {

    private var callback:
            ((HeartRateDevice) -> Unit)?
        = null

    private val scanner:
            BluetoothLeScanner?

        get() = adapter.bluetoothLeScanner

    /**
     * 扫描回调
     */
    private val scanCallback =
        object : ScanCallback() {

            override fun onScanResult(

                callbackType: Int,

                result: ScanResult
            ) {

                val device =
                    result.device

                val name =
                    device.name
                        ?: return

                callback?.invoke(

                    HeartRateDevice(

                        name = name,

                        address =
                            device.address,

                        rssi = result.rssi
                    )
                )
            }
        }

    /**
     * 开始扫描
     */
    @SuppressLint("MissingPermission")
    fun startScan(

        onDevice:
        (HeartRateDevice) -> Unit
    ) {

        callback = onDevice

        val filter =
            ScanFilter.Builder()

                .setServiceUuid(

                    ParcelUuid(

                        BleConstants
                            .HEART_RATE_SERVICE_UUID
                    )
                )

                .build()

        val settings =
            ScanSettings.Builder()

                .setScanMode(

                    ScanSettings
                        .SCAN_MODE_LOW_LATENCY
                )

                .build()

        scanner?.startScan(

            listOf(filter),

            settings,

            scanCallback
        )
    }

    /**
     * 停止扫描
     */
    @SuppressLint("MissingPermission")
    fun stopScan() {

        scanner?.stopScan(
            scanCallback
        )
    }
}
