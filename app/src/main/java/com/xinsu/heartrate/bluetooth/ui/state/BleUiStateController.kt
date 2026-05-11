package com.xinsu.heartrate.bluetooth.ui.state

import com.xinsu.heartrate.bluetooth.core.BleState
import com.xinsu.heartrate.bluetooth.core.BleStateManager
import com.xinsu.heartrate.bluetooth.ui.DeviceListPanel
import com.xinsu.heartrate.bluetooth.ui.ScanRadarView
import com.xinsu.heartrate.connection.effects.ConnectionAnimationView
import com.xinsu.heartrate.ui.hud.BluetoothHud
import com.xinsu.heartrate.ui.hud.HeartRateHud

class BleUiStateController(

    private val radarView: ScanRadarView,

    private val connectionView: ConnectionAnimationView,

    private val devicePanel: DeviceListPanel,

    private val bluetoothHud: BluetoothHud,

    private val heartHud: HeartRateHud

) {

    fun update() {

        when (
            BleStateManager.currentState
        ) {

            BleState.IDLE -> {

                bluetoothHud.updateState(
                    "Idle"
                )
            }

            BleState.SCANNING -> {

                radarView.startScan()

                bluetoothHud.updateState(
                    "Scanning..."
                )
            }

            BleState.CONNECTING -> {

                connectionView.showConnecting()

                bluetoothHud.updateState(
                    "Connecting..."
                )
            }

            BleState.CONNECTED -> {

                radarView.stopScan()

                connectionView.showConnected()

                bluetoothHud.updateState(
                    "Connected"
                )

                heartHud.updateHeartRate(
                    "78"
                )
            }

            BleState.DISCONNECTED -> {

                radarView.stopScan()

                connectionView.showDisconnected()

                bluetoothHud.updateState(
                    "Disconnected"
                )

                heartHud.updateHeartRate(
                    "--"
                )
            }

            BleState.FAILED -> {

                bluetoothHud.updateState(
                    "Failed"
                )
            }
        }
    }
}
