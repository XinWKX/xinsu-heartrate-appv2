package com.xinsu.heartrate.bluetooth.core

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.*
import android.content.Context
import android.os.ParcelUuid
import com.xinsu.heartrate.bluetooth.model.HeartRateDevice
import java.util.UUID

@SuppressLint("MissingPermission")
class BleScanner(

    context: Context

) {

    private val bluetoothManager =

        context.getSystemService(
            Context.BLUETOOTH_SERVICE
        ) as BluetoothManager

    private val bluetoothAdapter:
            BluetoothAdapter =

        bluetoothManager.adapter

    /**
     * Android 某些机型 scanner 可能为空
     */
    private val scanner:
            BluetoothLeScanner?

        get() =
            bluetoothAdapter
                .bluetoothLeScanner

    /**
     * 当前扫描状态
     */
    var isScanning =
        false

        private set

    /**
     * 已发现设备
     */
    private val devices =
        mutableListOf<HeartRateDevice>()

    /**
     * 设备更新回调
     */
    var onDevicesUpdated:
            ((List<HeartRateDevice>) -> Unit)?
        = null

    /**
     * 扫描错误回调
     */
    var onScanFailed:
            ((Int) -> Unit)?
        = null

    /**
     * 心率服务 UUID
     */
    private val heartRateService =

        ParcelUuid(

            UUID.fromString(

                "0000180D-0000-1000-8000-00805F9B34FB"
            )
        )

    /**
     * 扫描回调
     */
    private val scanCallback =

        object : ScanCallback() {

            override fun onScanResult(

                callbackType: Int,

                result: ScanResult
            ) {

                super.onScanResult(

                    callbackType,

                    result
                )

                parseDevice(result)
            }

            override fun onBatchScanResults(

                results:
                MutableList<ScanResult>
            ) {

                super.onBatchScanResults(
                    results
                )

                results.forEach {

                    parseDevice(it)
                }
            }

            override fun onScanFailed(
                errorCode: Int
            ) {

                super.onScanFailed(
                    errorCode
                )

                isScanning = false

                BleStateManager.setState(
                    BleState.DISCONNECTED
                )

                onScanFailed?.invoke(
                    errorCode
                )
            }
        }

    /**
     * 开始扫描
     */
    fun startScan() {

        /**
         * 避免重复扫描
         */
        if (isScanning) {

            return
        }

        /**
         * 蓝牙关闭
         */
        if (!bluetoothAdapter.isEnabled) {

            BleStateManager.setState(
                BleState.DISCONNECTED
            )

            return
        }

        devices.clear()

        val filter =

            ScanFilter.Builder()

                .setServiceUuid(
                    heartRateService
                )

                .build()

        val settings =

            ScanSettings.Builder()

                .setScanMode(

                    ScanSettings
                        .SCAN_MODE_LOW_LATENCY
                )

                .setReportDelay(0)

                .build()

        scanner?.startScan(

            listOf(filter),

            settings,

            scanCallback
        )

        isScanning = true

        BleStateManager.setState(
            BleState.SCANNING
        )
    }

    /**
     * 停止扫描
     */
    fun stopScan() {

        if (!isScanning) {

            return
        }

        try {

            scanner?.stopScan(
                scanCallback
            )

        } catch (_: Exception) {
        }

        isScanning = false

        BleStateManager.setState(
            BleState.IDLE
        )
    }

    /**
     * 清空设备
     */
    fun clearDevices() {

        devices.clear()

        onDevicesUpdated?.invoke(
            devices
        )
    }

    /**
     * 获取当前设备列表
     */
    fun getDevices():
            List<HeartRateDevice> {

        return devices
    }

    /**
     * 解析设备
     */
    private fun parseDevice(
        result: ScanResult
    ) {

        val device =
            result.device ?: return

        val scanRecord =
            result.scanRecord ?: return

        /**
         * 二次验证心率服务
         */
        val hasHeartRateService =

            scanRecord

                .serviceUuids

                ?.contains(
                    heartRateService
                ) == true

        if (!hasHeartRateService) {

            return
        }

        val mac =
            device.address ?: return

        /**
         * 已存在设备
         * 更新 RSSI
         */
        val existingDevice =

            devices.find {

                it.mac == mac
            }

        if (existingDevice != null) {

            existingDevice.rssi =
                result.rssi

            onDevicesUpdated?.invoke(
                devices
            )

            return
        }

        /**
         * 新设备
         */
        val heartRateDevice =

            HeartRateDevice(

                name =

                    device.name
                        ?: "Heart Rate Device",

                mac = mac,

                rssi = result.rssi,

                hasHeartRateService =
                    true
            )

        devices.add(
            heartRateDevice
        )

        onDevicesUpdated?.invoke(
            devices
        )
    }
}
