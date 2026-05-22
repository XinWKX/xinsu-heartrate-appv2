package com.xinsu.heartrate.bluetooth.ui.state

import com.xinsu.heartrate.bluetooth.core.BleState
import com.xinsu.heartrate.bluetooth.core.BleStateManager
import com.xinsu.heartrate.bluetooth.ui.DeviceListPanel
import com.xinsu.heartrate.bluetooth.ui.ScanRadarView
import com.xinsu.heartrate.connection.effects.ConnectionAnimationView
import com.xinsu.heartrate.ui.hud.BluetoothHud
import com.xinsu.heartrate.ui.hud.HeartRateHud
import kotlin.random.Random

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

    fun update() {

        when (
            BleStateManager.getState()
        ) {

            BleState.IDLE -> {

                bluetoothHud.invalidate()
            }

            BleState.SCANNING -> {

                radarView.startScan()

                bluetoothHud.invalidate()
            }

            BleState.CONNECTING -> {

                connectionView.showConnecting()

                bluetoothHud.invalidate()
            }

            BleState.CONNECTED -> {

                radarView.stopScan()

                connectionView.showConnected()

                bluetoothHud.invalidate()

                val bpm =
                    Random.nextInt(
                        65,
                        95
                    )

                heartHud.updateHeartRate(
                    bpm
                )
            }

            BleState.DISCONNECTED -> {

                radarView.stopScan()

                connectionView.showDisconnected()

                bluetoothHud.invalidate()

                heartHud.updateHeartRate(
                    0
                )
            }

            BleState.RECONNECTING -> {

                bluetoothHud.invalidate()
            }
        }
    }
}
