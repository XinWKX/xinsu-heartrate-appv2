package com.xinsu.heartrate.ui.widgets

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.FrameLayout
import com.xinsu.heartrate.bluetooth.core.BleState
import com.xinsu.heartrate.bluetooth.core.BleStateManager
import com.xinsu.heartrate.bluetooth.runtime.BleRuntimeController
import com.xinsu.heartrate.bluetooth.ui.DeviceListPanel
import com.xinsu.heartrate.bluetooth.ui.ScanRadarView
import com.xinsu.heartrate.bluetooth.ui.state.BleUiStateController
import com.xinsu.heartrate.connection.effects.ConnectionAnimationView
import com.xinsu.heartrate.core.pulse.PulseRuntimeEngine
import com.xinsu.heartrate.settings.ui.SettingsPanel
import com.xinsu.heartrate.ui.glass.GlassButton
import com.xinsu.heartrate.ui.hud.BluetoothHud
import com.xinsu.heartrate.ui.hud.HeartRateHud
import com.xinsu.heartrate.ui.hud.TopStatusBar

class HudOverlayView(

    context: Context

) : FrameLayout(context) {

    private lateinit var settingsPanel:
            SettingsPanel

    private lateinit var devicePanel:
            DeviceListPanel

    private lateinit var radarView:
            ScanRadarView

    private lateinit var bluetoothHud:
            BluetoothHud

    private lateinit var heartHud:
            HeartRateHud

    private lateinit var connectionView:
            ConnectionAnimationView

    private lateinit var uiStateController:
            BleUiStateController

    private lateinit var bleRuntime:
            BleRuntimeController

    private val handler =
        Handler(
            Looper.getMainLooper()
        )

    private val uiRunnable =
        object : Runnable {

            override fun run() {

                PulseRuntimeEngine.update()

                uiStateController.update()

                handler.postDelayed(
                    this,
                    16
                )
            }
        }

    init {

        initUI()

        initBle()

        handler.post(uiRunnable)
    }

    /**
     * 初始化 UI
     */
    private fun initUI() {

        // Top Bar
        val topBar =
            TopStatusBar(context)

        val topParams =
            LayoutParams(

                LayoutParams.MATCH_PARENT,

                120
            )

        topParams.topMargin = 48

        topParams.leftMargin = 32

        topParams.rightMargin = 32

        addView(
            topBar,
            topParams
        )

        // Radar
        radarView =
            ScanRadarView(context)

        val radarParams =
            LayoutParams(

                700,

                700
            )

        radarParams.gravity =
            Gravity.CENTER

        addView(
            radarView,
            radarParams
        )

        // Connection Animation
        connectionView =
            ConnectionAnimationView(
                context
            )

        val connectionParams =
            LayoutParams(

                LayoutParams.MATCH_PARENT,

                LayoutParams.MATCH_PARENT
            )

        addView(
            connectionView,
            connectionParams
        )

        // Heart HUD
        heartHud =
            HeartRateHud(context)

        val heartParams =
            LayoutParams(

                LayoutParams.MATCH_PARENT,

                LayoutParams.MATCH_PARENT
            )

        addView(
            heartHud,
            heartParams
        )

        // Bluetooth HUD
        bluetoothHud =
            BluetoothHud(context)

        val bluetoothParams =
            LayoutParams(

                500,

                120
            )

        bluetoothParams.gravity =
            Gravity.BOTTOM or
                    Gravity.CENTER_HORIZONTAL

        bluetoothParams.bottomMargin =
            260

        addView(
            bluetoothHud,
            bluetoothParams
        )

        // Connect Button
        val connectButton =
            GlassButton(context)

        val connectParams =
            LayoutParams(

                LayoutParams.WRAP_CONTENT,

                LayoutParams.WRAP_CONTENT
            )

        connectParams.gravity =
            Gravity.BOTTOM or
                    Gravity.CENTER_HORIZONTAL

        connectParams.bottomMargin =
            120

        addView(
            connectButton,
            connectParams
        )

        // Device Panel
        devicePanel =
            DeviceListPanel(context)

        val deviceParams =
            LayoutParams(

                LayoutParams.MATCH_PARENT,

                800
            )

        deviceParams.gravity =
            Gravity.CENTER_HORIZONTAL

        deviceParams.topMargin =
            220

        deviceParams.leftMargin =
            40

        deviceParams.rightMargin =
            40

        addView(
            devicePanel,
            deviceParams
        )

        // Settings Panel
        settingsPanel =
            SettingsPanel(context)

        settingsPanel.alpha = 0f

        settingsPanel.visibility =
            GONE

        val settingsParams =
            LayoutParams(

                LayoutParams.MATCH_PARENT,

                LayoutParams.MATCH_PARENT
            )

        settingsParams.leftMargin =
            40

        settingsParams.rightMargin =
            40

        settingsParams.topMargin =
            220

        settingsParams.bottomMargin =
            220

        addView(
            settingsPanel,
            settingsParams
        )

        // UI Controller
        uiStateController =

            BleUiStateController(

                radarView,

                connectionView,

                devicePanel,

                bluetoothHud,

                heartHud
            )

        // Click
        connectButton.setOnClickListener {

            startBle()
        }

        // Long Click
        connectButton.setOnLongClickListener {

            toggleSettings()

            true
        }
    }

    /**
     * 初始化 BLE
     */
    private fun initBle() {

        bleRuntime =
            BleRuntimeController(
                context
            )

        bleRuntime.onDevicesUpdated = {

            devices ->

            devicePanel.updateDevices(
                devices
            )
        }

        bleRuntime.onConnectionStateChanged = {

            connected ->

            if (connected) {

                BleStateManager.setState(
                    BleState.CONNECTED
                )

            } else {

                BleStateManager.setState(
                    BleState.DISCONNECTED
                )
            }
        }
    }

    /**
     * 开始 BLE
     */
    private fun startBle() {

        BleStateManager.setState(
            BleState.SCANNING
        )

        bleRuntime.startScan()
    }

    /**
     * Toggle Settings
     */
    private fun toggleSettings() {

        if (
            settingsPanel.visibility == GONE
        ) {

            settingsPanel.visibility =
                VISIBLE

            settingsPanel.animate()

                .alpha(1f)

                .setDuration(280)

                .start()

        } else {

            settingsPanel.animate()

                .alpha(0f)

                .setDuration(220)

                .withEndAction {

                    settingsPanel.visibility =
                        GONE
                }

                .start()
        }
    }

    override fun onDetachedFromWindow() {

        super.onDetachedFromWindow()

        handler.removeCallbacks(
            uiRunnable
        )

        bleRuntime.stopScan()
    }
}
