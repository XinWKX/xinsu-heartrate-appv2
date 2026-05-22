package com.xinsu.heartrate.bluetooth.runtime

import android.annotation.SuppressLint
import android.content.Context
import com.xinsu.heartrate.bluetooth.core.BleConnectionManager
import com.xinsu.heartrate.bluetooth.core.BleScannerManager
import com.xinsu.heartrate.bluetooth.data.HeartRateRepository
import com.xinsu.heartrate.bluetooth.model.HeartRateDevice

class BleRuntimeController(

    private val context: Context
) {

    private val scanner =
        BleScannerManager(context)

    private var connectionManager:
            BleConnectionManager? = null

    var onDevicesUpdated:

        ((List<HeartRateDevice>) -> Unit)?
        = null

    var onHeartRateUpdated:
            ((Int) -> Unit)?
        = null

    var onConnectionStateChanged:
            ((Boolean) -> Unit)?
        = null

    init {

        scanner.onDevicesUpdated = {

            devices ->

            val mapped =
                devices.map {

                    HeartRateDevice(

                        name = it.name,

                        address =
                        it.device.address,

                        rssi = it.rssi
                    )
                }

            onDevicesUpdated
                ?.invoke(mapped)

            if (
                devices.isNotEmpty()
            ) {

                autoConnect(
                    devices.first()
                )
            }
        }
    }

    fun startScan() {

        scanner.startScan()
    }

    fun stopScan() {

        scanner.stopScan()
    }

    @SuppressLint("MissingPermission")
    private fun autoConnect(

        device:
        BleScannerManager.BleDevice
    ) {

        if (
            connectionManager != null
        ) {

            return
        }

        connectionManager =
            BleConnectionManager(
                context
            )

        connectionManager
            ?.onConnected = {

                HeartRateRepository
                    .isConnected = true

                HeartRateRepository
                    .connectedDeviceName =
                    device.name

                onConnectionStateChanged
                    ?.invoke(true)
            }

        connectionManager
            ?.onDisconnected = {

                HeartRateRepository
                    .isConnected = false

                onConnectionStateChanged
                    ?.invoke(false)
            }

        connectionManager
            ?.onHeartRateChanged = {

                bpm ->

                HeartRateRepository
                    .currentBpm = bpm

                onHeartRateUpdated
                    ?.invoke(bpm)
            }

        connectionManager
            ?.connect(device.device)
    }
}
