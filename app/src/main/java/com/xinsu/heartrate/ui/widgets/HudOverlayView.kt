package com.xinsu.heartrate.ui.widgets

import android.content.Context
import android.view.Gravity
import android.widget.FrameLayout
import com.xinsu.heartrate.bluetooth.model.HeartRateDevice
import com.xinsu.heartrate.bluetooth.ui.DeviceListPanel
import com.xinsu.heartrate.bluetooth.ui.ScanRadarView
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

    init {

        initUI()
    }

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
        val radar =
            ScanRadarView(context)

        val radarParams =
            LayoutParams(

                700,

                700
            )

        radarParams.gravity =
            Gravity.CENTER

        addView(
            radar,
            radarParams
        )

        // Heart HUD
        val heartHud =
            HeartRateHud(context)

        val heartParams =
            LayoutParams(

                LayoutParams.WRAP_CONTENT,

                LayoutParams.WRAP_CONTENT
            )

        heartParams.gravity =
            Gravity.CENTER

        addView(
            heartHud,
            heartParams
        )

        // Bluetooth HUD
        val bluetoothHud =
            BluetoothHud(context)

        val bluetoothParams =
            LayoutParams(

                360,

                100
            )

        bluetoothParams.gravity =
            Gravity.BOTTOM or
                    Gravity.CENTER_HORIZONTAL

        bluetoothParams.bottomMargin =
            280

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

        devicePanel.alpha = 0f

        devicePanel.visibility =
            GONE

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

        // 点击打开设备列表
        connectButton.setOnClickListener {

            toggleDevicePanel()
        }

        // 长按打开设置
        connectButton.setOnLongClickListener {

            toggleSettings()

            true
        }

        // 测试设备
        mockDevices()
    }

    /**
     * 测试设备
     */
    private fun mockDevices() {

        val devices =
            listOf(

                HeartRateDevice(

                    "Polar H10",

                    "00:11:22",

                    -42
                ),

                HeartRateDevice(

                    "Mi Band 9",

                    "11:22:33",

                    -58
                ),

                HeartRateDevice(

                    "Galaxy Watch",

                    "22:33:44",

                    -63
                )
            )

        devicePanel.updateDevices(
            devices
        )
    }

    /**
     * 切换设备面板
     */
    private fun toggleDevicePanel() {

        if (
            devicePanel.visibility == GONE
        ) {

            devicePanel.visibility =
                VISIBLE

            devicePanel.animate()

                .alpha(1f)

                .setDuration(280)

                .start()

        } else {

            devicePanel.animate()

                .alpha(0f)

                .setDuration(220)

                .withEndAction {

                    devicePanel.visibility =
                        GONE
                }

                .start()
        }
    }

    /**
     * 设置
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
}
