package com.xinsu.heartrate.bluetooth.ui.state

import android.bluetooth.BluetoothDevice
import com.xinsu.heartrate.bluetooth.core.BleConnector
import com.xinsu.heartrate.bluetooth.core.BleScanner
import com.xinsu.heartrate.bluetooth.core.BleState
import com.xinsu.heartrate.bluetooth.core.BleStateManager
import com.xinsu.heartrate.bluetooth.model.HeartRateDevice
import com.xinsu.heartrate.bluetooth.ui.DeviceListPanel
import com.xinsu.heartrate.bluetooth.ui.ScanRadarView
import com.xinsu.heartrate.connection.effects.ConnectionAnimationView
import com.xinsu.heartrate.ui.hud.BluetoothHud
import com.xinsu.heartrate.ui.hud.HeartRateHud

class BleUiStateController(

    private val scanner:
    BleScanner,

    private val connector:
    BleConnector,

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
     * BLE设备列表
     */
    private val devices =
        mutableListOf<HeartRateDevice>()

    init {

        bindScanner()

        bindConnector()

        bindStateManager()
    }

    /**
     * Scanner
     */
    private fun bindScanner() {

        scanner.onDevicesUpdated = {

            devices.clear()

            devices.addAll(it)

            devicePanel.updateDevices(
                devices
            )

            bluetoothHud.invalidate()
        }
    }

    /**
     * Connector
     */
    private fun bindConnector() {

        connector.onHeartRateChanged = {

            BleStateManager.currentHeartRate =
                it

            heartHud.updateHeartRate(
                it
            )

            heartHud.invalidate()
        }

        connector.onConnectionStateChanged = {

            BleStateManager.setState(it)

            update()
        }
    }

    /**
     * StateManager
     */
    private fun bindStateManager() {

        BleStateManager.addListener {

            update()
        }
    }

    /**
     * 更新 UI
     */
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

                heartHud.updateHeartRate(

                    BleStateManager
                        .currentHeartRate
                )

                heartHud.invalidate()
            }

            BleState.DISCONNECTED -> {

                radarView.stopScan()

                connectionView.showDisconnected()

                bluetoothHud.invalidate()

                heartHud.updateHeartRate(0)

                heartHud.invalidate()
            }

            BleState.RECONNECTING -> {

                bluetoothHud.invalidate()
            }

            BleState.FAILED -> {

                connectionView.showDisconnected()

                bluetoothHud.invalidate()
            }
        }
    }

    /**
     * 开始扫描
     */
    fun startScan() {

        scanner.startScan()
    }

    /**
     * 停止扫描
     */
    fun stopScan() {

        scanner.stopScan()
    }

    /**
     * 连接设备
     */
    fun connectDevice(
        device: BluetoothDevice
    ) {

        connector.connect(device)
    }

    /**
     * 断开设备
     */
    fun disconnect() {

        connector.disconnect()
    }

    /**
     * 获取设备
     */
    fun getDevices():
            List<HeartRateDevice> {

        return devices
    }
}
