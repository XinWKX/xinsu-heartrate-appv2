package com.xinsu.heartrate.ui.widgets

import android.content.Context
import android.view.Gravity
import android.widget.FrameLayout
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

    init {

        initUI()
    }

    private fun initUI() {

        // 顶部状态栏
        val topBar =
            TopStatusBar(context)

        val topParams =
            LayoutParams(

                LayoutParams.MATCH_PARENT,

                120
            )

        topParams.topMargin =
            48

        topParams.leftMargin =
            32

        topParams.rightMargin =
            32

        addView(
            topBar,
            topParams
        )

        // Heart Rate HUD
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

        // 蓝牙状态
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

        // 长按打开设置
        connectButton.setOnLongClickListener {

            toggleSettings()

            true
        }
    }

    /**
     * 切换设置界面
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
