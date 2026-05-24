package com.xinsu.heartrate.bluetooth.core

object BleStateManager {

    /**
     * 当前 BLE 状态
     */
    private var currentState =
        BleState.IDLE

    /**
     * 已连接设备名
     */
    var connectedDeviceName =
        "NONE"

    /**
     * 当前心率
     */
    var currentHeartRate =
        0

    /**
     * 当前 RSSI
     */
    var currentRssi =
        0

    /**
     * 状态监听
     */
    private val listeners =
        mutableListOf<
                (BleState) -> Unit>()

    /**
     * 状态变化监听
     */
    fun addListener(

        listener:
        (BleState) -> Unit
    ) {

        listeners.add(listener)
    }

    /**
     * 移除监听
     */
    fun removeListener(

        listener:
        (BleState) -> Unit
    ) {

        listeners.remove(listener)
    }

    /**
     * 设置状态
     */
    fun setState(
        state: BleState
    ) {

        currentState = state

        listeners.forEach {

            it.invoke(state)
        }
    }

    /**
     * 获取状态
     */
    fun getState():
            BleState {

        return currentState
    }

    /**
     * 是否已连接
     */
    fun isConnected():
            Boolean {

        return currentState ==
                BleState.CONNECTED
    }

    /**
     * 是否扫描中
     */
    fun isScanning():
            Boolean {

        return currentState ==
                BleState.SCANNING
    }

    /**
     * 重置状态
     */
    fun reset() {

        currentState =
            BleState.IDLE

        connectedDeviceName =
            "NONE"

        currentHeartRate =
            0

        currentRssi =
            0
    }
}
