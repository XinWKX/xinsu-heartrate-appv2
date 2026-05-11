package com.xinsu.heartrate.bluetooth.core

object BleStateManager {

    var currentState:
            BleState = BleState.IDLE

    var connectedDeviceName:
            String = "Unknown"

    var currentRssi:
            Int = -100

    fun setState(
        state: BleState
    ) {

        currentState = state
    }

    fun updateDevice(
        name: String,
        rssi: Int
    ) {

        connectedDeviceName = name

        currentRssi = rssi
    }
}
