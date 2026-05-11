package com.xinsu.heartrate.bluetooth.ui.state

import android.view.View
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

    /**
     * 更新 UI
     */
    fun update() {

        when (

            BleStateManager.state

        ) {

            BleState.SCANNING -> {

                radarView.visibility =
                    View.VISIBLE

                connectionView.hide()

                devicePanel.visibility =
                    View.VISIBLE

                bluetoothHud.refresh()
            }

            BleState.CONNECTING -> {

                radarView.visibility =
                    View.VISIBLE

                connectionView.show()

                bluetoothHud.refresh()
            }

            BleState.CONNECTED -> {

                connectionView.hide()

                radarView.visibility =
                    View.GONE

                bluetoothHud.refresh()

                heartHud.alpha = 0f

                heartHud.animate()

                    .alpha(1f)

                    .setDuration(800)

                    .start()
            }

            BleState.RECONNECTING -> {

                connectionView.show()

                bluetoothHud.refresh()
            }

            BleState.DISCONNECTED -> {

                radarView.visibility =
                    View.VISIBLE

                bluetoothHud.refresh()
            }

            BleState.FAILED -> {

                connectionView.hide()

                bluetoothHud.refresh()
            }

            else -> {

                bluetoothHud.refresh()
            }
        }
    }
}
