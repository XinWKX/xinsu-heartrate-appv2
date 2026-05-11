package com.xinsu.heartrate.settings.ui

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import com.xinsu.heartrate.settings.core.SettingsManager
import com.xinsu.heartrate.ui.glass.GlassPanel

class SettingsPanel(

    context: Context

) : FrameLayout(context) {

    init {

        initUI()
    }

    private fun initUI() {

        val glass =
            GlassPanel(context)

        addView(

            glass,

            LayoutParams(

                LayoutParams.MATCH_PARENT,

                LayoutParams.MATCH_PARENT
            )
        )

        val container =
            LinearLayout(context)

        container.orientation =
            LinearLayout.VERTICAL

        container.setPadding(

            60,
            60,
            60,
            60
        )

        addView(

            container,

            LayoutParams(

                LayoutParams.MATCH_PARENT,

                LayoutParams.MATCH_PARENT
            )
        )

        // 音效开关
        container.addView(

            createSwitch(

                "Pulse Audio",

                SettingsManager.pulseAudioEnabled

            ) {

                SettingsManager
                    .pulseAudioEnabled = it
            }
        )

        // Haptic
        container.addView(

            createSwitch(

                "Haptic Feedback",

                SettingsManager.hapticEnabled

            ) {

                SettingsManager
                    .hapticEnabled = it
            }
        )

        // Glow
        container.addView(

            createSwitch(

                "Ambient Glow",

                SettingsManager.glowEnabled

            ) {

                SettingsManager
                    .glowEnabled = it
            }
        )
    }

    /**
     * Switch Item
     */
    private fun createSwitch(

        title: String,

        checked: Boolean,

        onChanged:
        (Boolean) -> Unit

    ): LinearLayout {

        val layout =
            LinearLayout(context)

        layout.orientation =
            LinearLayout.HORIZONTAL

        layout.gravity =
            Gravity.CENTER_VERTICAL

        layout.setPadding(

            0,
            20,
            0,
            20
        )

        val text =
            TextView(context)

        text.text = title

        text.textSize = 16f

        text.setTextColor(
            Color.WHITE
        )

        val switchView =
            Switch(context)

        switchView.isChecked =
            checked

        switchView.setOnCheckedChangeListener {

                _, isChecked ->

            onChanged(isChecked)
        }

        val textParams =
            LinearLayout.LayoutParams(

                0,

                LayoutParams.WRAP_CONTENT,

                1f
            )

        layout.addView(
            text,
            textParams
        )

        layout.addView(
            switchView
        )

        return layout
    }
}
