package com.xinsu.heartrate.bluetooth.ui.state

import com.xinsu.heartrate.bluetooth.core.BleState
import com.xinsu.heartrate.bluetooth.core.BleStateManager
import com.xinsu.heartrate.bluetooth.ui.DeviceListPanel
import com.xinsu.heartrate.bluetooth.ui.ScanRadarView
import com.xinsu.heartrate.connection.effects.ConnectionAnimationView
import com.xinsu.heartrate.ui.hud.BluetoothHud
import com.xinsu.heartrate.ui.hud.HeartRateHud

class BleUiStateController(

    private val radarView:
    ScanRadarView,

    private val connectionView:
    ConnectionAnimationView,

    private val devicePanel:
    DeviceListPanel,

    private val bluetoothHud:
    BluetoothHud,

    private val heartHud:
    HeartRateHud
) {

    private var lastState:
            BleState? = null

    fun update() {

        val state =
            BleStateManager.getState()

        if (
            state == lastState
        ) {

            return
        }

        lastState = state

        when (state) {

            BleState.IDLE -> {

                radarView.stopScan()

                connectionView.hide()
            }

            BleState.SCANNING -> {

                radarView.startScan()

                connectionView.showScanning()
            }

            BleState.CONNECTING -> {

                radarView.startScan()

                connectionView.showConnecting()
            }

            BleState.CONNECTED -> {

                radarView.stopScan()

                connectionView.showConnected()
            }

            BleState.DISCONNECTED -> {

                radarView.stopScan()

                connectionView.showDisconnected()
            }

            BleState.RECONNECTING -> {

                radarView.startScan()

                connectionView.showReconnecting()
            }
        }
    }
}
