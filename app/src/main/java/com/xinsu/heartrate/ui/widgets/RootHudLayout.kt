package com.xinsu.heartrate.ui.widgets

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.graphics.Color
import android.widget.FrameLayout
import com.xinsu.heartrate.bluetooth.core.BleConnector
import com.xinsu.heartrate.bluetooth.core.BleScanner
import com.xinsu.heartrate.bluetooth.model.HeartRateDevice
import com.xinsu.heartrate.bluetooth.ui.DeviceListPanel
import com.xinsu.heartrate.bluetooth.ui.ScanRadarView
import com.xinsu.heartrate.bluetooth.ui.state.BleUiStateController
import com.xinsu.heartrate.connection.effects.ConnectionAnimationView
import com.xinsu.heartrate.particles.ui.ParticleBackgroundView
import com.xinsu.heartrate.settings.ui.SettingsPanel
import com.xinsu.heartrate.ui.hud.BluetoothHud
import com.xinsu.heartrate.ui.hud.HeartRateHud

@SuppressLint("ViewConstructor")
class RootHudLayout(
    context: Context
) : FrameLayout(context) {

    /**
     * Particle Background
     */
    private val particleBackground =

        ParticleBackgroundView(context)

    /**
     * Radar
     */
    private val radarView =

        ScanRadarView(context)

    /**
     * Connection Animation
     */
    private val connectionView =

        ConnectionAnimationView(context)

    /**
     * Device Panel
     */
    private val devicePanel =

        DeviceListPanel(context)

    /**
     * HUD
     */
    private val bluetoothHud =

        BluetoothHud(context)

    private val heartHud =

        HeartRateHud(context)

    /**
     * Settings
     */
    private val settingsPanel =

        SettingsPanel(context)

    /**
     * BLE
     */
    private val scanner =

        BleScanner(context)

    private val connector =

        BleConnector(context)

    /**
     * UI Controller
     */
    private val uiController =

        BleUiStateController(

            scanner,

            connector,

            radarView,

            connectionView,

            devicePanel,

            bluetoothHud,

            heartHud
        )

    init {

        setBackgroundColor(
            Color.BLACK
        )

        initUI()

        initBle()

        startScan()
    }

    /**
     * UI
     */
    private fun initUI() {

        /**
         * Particle
         */
        addView(

            particleBackground,

            LayoutParams(

                LayoutParams.MATCH_PARENT,

                LayoutParams.MATCH_PARENT
            )
        )

        /**
         * Radar
         */
        addView(

            radarView,

            LayoutParams(

                LayoutParams.MATCH_PARENT,

                LayoutParams.MATCH_PARENT
            )
        )

        /**
         * Connection Animation
         */
        addView(

            connectionView,

            LayoutParams(

                LayoutParams.MATCH_PARENT,

                LayoutParams.MATCH_PARENT
            )
        )

        /**
         * Device List
         */
        val deviceParams =

            LayoutParams(

                LayoutParams.MATCH_PARENT,

                700
            )

        deviceParams.leftMargin = 40
        deviceParams.rightMargin = 40
        deviceParams.topMargin = 180

        addView(

            devicePanel,

            deviceParams
        )

        /**
         * Bluetooth HUD
         */
        val bluetoothParams =

            LayoutParams(

                320,

                120
            )

        bluetoothParams.leftMargin = 40
        bluetoothParams.topMargin = 40

        addView(

            bluetoothHud,

            bluetoothParams
        )

        /**
         * Heart HUD
         */
        val heartParams =

            LayoutParams(

                320,

                120
            )

        heartParams.rightMargin = 40
        heartParams.topMargin = 40

        heartParams.marginEnd = 40

        addView(

            heartHud,

            heartParams
        )

        /**
         * Settings
         */
        val settingsParams =

            LayoutParams(

                500,

                700
            )

        settingsParams.rightMargin = 40
        settingsParams.topMargin = 200

        settingsParams.marginEnd = 40

        addView(

            settingsPanel,

            settingsParams
        )
    }

    /**
     * BLE
     */
    private fun initBle() {

        devicePanel.onDeviceClicked = {

            connectDevice(it)
        }
    }

    /**
     * 开始扫描
     */
    private fun startScan() {

        uiController.startScan()
    }

    /**
     * 连接设备
     */
    private fun connectDevice(
        device: HeartRateDevice
    ) {

        val bluetoothDevice:

                BluetoothDevice =

            scanner
                .getBluetoothDevice(
                    device.mac
                )

                ?: return

        devicePanel.setConnectedDevice(
            device.mac
        )

        uiController.connectDevice(
            bluetoothDevice
        )
    }

    /**
     * 停止扫描
     */
    fun stopScan() {

        uiController.stopScan()
    }

    /**
     * 断开连接
     */
    fun disconnect() {

        uiController.disconnect()
    }
}
