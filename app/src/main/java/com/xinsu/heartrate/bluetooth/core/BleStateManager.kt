package com.xinsu.heartrate.bluetooth.core

object BleStateManager {

    private var state =
        BleState.IDLE

    fun setState(
        newState: BleState
    ) {

        state = newState
    }

    fun getState():
            BleState {

        return state
    }
}
