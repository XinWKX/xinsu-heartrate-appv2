package com.xinsu.heartrate.bluetooth.core

import java.util.UUID

object BleConstants {

    // =========================================================
    // HEART RATE PROFILE
    // =========================================================

    /**
     * Heart Rate Service
     *
     * UUID: 0x180D
     */
    val HEART_RATE_SERVICE_UUID =

        UUID.fromString(

            "0000180D-0000-1000-8000-00805F9B34FB"
        )

    /**
     * Heart Rate Measurement
     *
     * UUID: 0x2A37
     *
     * Notify
     */
    val HEART_RATE_CHARACTERISTIC_UUID =

        UUID.fromString(

            "00002A37-0000-1000-8000-00805F9B34FB"
        )

    /**
     * Body Sensor Location
     *
     * UUID: 0x2A38
     */
    val BODY_SENSOR_LOCATION_UUID =

        UUID.fromString(

            "00002A38-0000-1000-8000-00805F9B34FB"
        )

    /**
     * Heart Rate Control Point
     *
     * UUID: 0x2A39
     */
    val HEART_RATE_CONTROL_POINT_UUID =

        UUID.fromString(

            "00002A39-0000-1000-8000-00805F9B34FB"
        )

    // =========================================================
    // BATTERY SERVICE
    // =========================================================

    /**
     * Battery Service
     *
     * UUID: 0x180F
     */
    val BATTERY_SERVICE_UUID =

        UUID.fromString(

            "0000180F-0000-1000-8000-00805F9B34FB"
        )

    /**
     * Battery Level
     *
     * UUID: 0x2A19
     */
    val BATTERY_LEVEL_CHARACTERISTIC_UUID =

        UUID.fromString(

            "00002A19-0000-1000-8000-00805F9B34FB"
        )

    // =========================================================
    // DEVICE INFORMATION
    // =========================================================

    /**
     * Device Information Service
     *
     * UUID: 0x180A
     */
    val DEVICE_INFORMATION_SERVICE_UUID =

        UUID.fromString(

            "0000180A-0000-1000-8000-00805F9B34FB"
        )

    /**
     * Manufacturer Name
     *
     * UUID: 0x2A29
     */
    val MANUFACTURER_NAME_UUID =

        UUID.fromString(

            "00002A29-0000-1000-8000-00805F9B34FB"
        )

    /**
     * Model Number
     *
     * UUID: 0x2A24
     */
    val MODEL_NUMBER_UUID =

        UUID.fromString(

            "00002A24-0000-1000-8000-00805F9B34FB"
        )

    /**
     * Firmware Revision
     *
     * UUID: 0x2A26
     */
    val FIRMWARE_REVISION_UUID =

        UUID.fromString(

            "00002A26-0000-1000-8000-00805F9B34FB"
        )

    /**
     * Hardware Revision
     *
     * UUID: 0x2A27
     */
    val HARDWARE_REVISION_UUID =

        UUID.fromString(

            "00002A27-0000-1000-8000-00805F9B34FB"
        )

    /**
     * Software Revision
     *
     * UUID: 0x2A28
     */
    val SOFTWARE_REVISION_UUID =

        UUID.fromString(

            "00002A28-0000-1000-8000-00805F9B34FB"
        )

    // =========================================================
    // BLE DESCRIPTOR
    // =========================================================

    /**
     * CCCD
     *
     * Client Characteristic Configuration Descriptor
     *
     * UUID: 0x2902
     */
    val CLIENT_CHARACTERISTIC_CONFIG_UUID =

        UUID.fromString(

            "00002902-0000-1000-8000-00805F9B34FB"
        )

    // =========================================================
    // APPLE / POLAR / EXTENSIONS
    // =========================================================

    /**
     * Polar ECG Service
     */
    val POLAR_ECG_SERVICE_UUID =

        UUID.fromString(

            "FB005C80-02E7-F387-1CAD-8ACD2D8DF0C8"
        )

    /**
     * Polar ECG Characteristic
     */
    val POLAR_ECG_CHARACTERISTIC_UUID =

        UUID.fromString(

            "FB005C81-02E7-F387-1CAD-8ACD2D8DF0C8"
        )

    /**
     * Apple Continuity
     */
    val APPLE_CONTINUITY_UUID =

        UUID.fromString(

            "D0611E78-BBB4-4591-A5F8-487910AE4366"
        )

    // =========================================================
    // SCAN SETTINGS
    // =========================================================

    /**
     * 扫描周期
     */
    const val SCAN_PERIOD = 12000L

    /**
     * 重连延迟
     */
    const val RECONNECT_DELAY = 3000L

    /**
     * 连接超时
     */
    const val CONNECTION_TIMEOUT = 10000L

    /**
     * RSSI 更新间隔
     */
    const val RSSI_UPDATE_INTERVAL = 2000L
}
