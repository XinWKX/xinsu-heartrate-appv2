package com.xinsu.heartrate.bluetooth.core

import com.xinsu.heartrate.bluetooth.model.HeartRateDevice

object BleDeviceCache {

    private val devices =
        mutableMapOf<String, HeartRateDevice>()

    /**
     * 添加设备
     */
    fun add(
        device: HeartRateDevice
    ) {

        devices[device.address] =
            device
    }

    /**
     * 获取设备列表
     */
    fun getDevices():

            List<HeartRateDevice> {

        return devices.values.toList()
    }

    /**
     * 清空
     */
    fun clear() {

        devices.clear()
    }
}
