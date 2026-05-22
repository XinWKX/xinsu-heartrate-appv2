package com.xinsu.heartrate.bluetooth.core

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.*
import android.content.Context
import android.os.ParcelUuid
import com.xinsu.heartrate.bluetooth.filter.BleHeartRateFilter

class BleScannerManager(

    private val context: Context
) {

    data class BleDevice(

        val device: BluetoothDevice,

        val name: String,

        val rssi: Int
    )

    private val bluetoothAdapter:

        BluetoothAdapter? =

        BluetoothAdapter
            .getDefaultAdapter()

    private val scanner:

        BluetoothLeScanner?

        get() =
            bluetoothAdapter
                ?.bluetoothLeScanner

    private val devices =
        mutableListOf<BleDevice>()

    var onDevicesUpdated:

        ((List<BleDevice>) -> Unit)? = null

    @SuppressLint("MissingPermission")
    fun startScan() {

        devices.clear()

        val filter = ScanFilter.Builder()

            .setServiceUuid(

                ParcelUuid(

                    BleConnectionManager
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

    @SuppressLint("MissingPermission")
    fun stopScan() {

        scanner?.stopScan(
            scanCallback
        )
    }

    private val scanCallback =

        object : ScanCallback() {

            override fun onScanResult(

                callbackType: Int,

                result: ScanResult
            ) {

                if (
                    !BleHeartRateFilter
                        .isHeartRateDevice(
                            result
                        )
                ) {

                    return
                }

                val device =
                    result.device

                val name =
                    device.name
                        ?: "Unknown"

                if (

                    devices.any {

                        it.device.address ==
                        device.address
                    }
                ) {

                    return
                }

                devices.add(

                    BleDevice(

                        device = device,

                        name = name,

                        rssi = result.rssi
                    )
                )

                onDevicesUpdated
                    ?.invoke(devices)
            }
        }
}
