package com.xinsu.heartrate.settings.ui

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.*
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

        val scroll =
            ScrollView(context)

        addView(

            scroll,

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

        scroll.addView(

            container,

            LayoutParams(

                LayoutParams.MATCH_PARENT,

                LayoutParams.WRAP_CONTENT
            )
        )

        /**
         * 标题
         */
        val title =
            TextView(context)

        title.text =
            "SYSTEM SETTINGS"

        title.textSize =
            24f

        title.setTextColor(
            Color.WHITE
        )

        title.setPadding(

            0,
            0,
            0,
            40
        )

        container.addView(title)

        /**
         * 音效
         */
        container.addView(

            createSwitch(

                "Pulse Audio",

                SettingsManager
                    .pulseAudioEnabled

            ) {

                SettingsManager
                    .pulseAudioEnabled = it
            }
        )

        /**
         * Haptic
         */
        container.addView(

            createSwitch(

                "Haptic Feedback",

                SettingsManager
                    .hapticEnabled

            ) {

                SettingsManager
                    .hapticEnabled = it
            }
        )

        /**
         * Glow
         */
        container.addView(

            createSwitch(

                "Ambient Glow",

                SettingsManager
                    .glowEnabled

            ) {

                SettingsManager
                    .glowEnabled = it
            }
        )

        /**
         * ECG Glow
         */
        container.addView(

            createSwitch(

                "ECG Glow",

                SettingsManager
                    .ecgGlowEnabled

            ) {

                SettingsManager
                    .ecgGlowEnabled = it
            }
        )

        /**
         * 雷达
         */
        container.addView(

            createSwitch(

                "Radar Scanner",

                SettingsManager
                    .radarEnabled

            ) {

                SettingsManager
                    .radarEnabled = it
            }
        )

        /**
         * 粒子
         */
        container.addView(

            createSwitch(

                "Particles",

                SettingsManager
                    .particlesEnabled

            ) {

                SettingsManager
                    .particlesEnabled = it
            }
        )

        /**
         * 动态背景
         */
        container.addView(

            createSwitch(

                "Animated Background",

                SettingsManager
                    .animatedBackground

            ) {

                SettingsManager
                    .animatedBackground = it
            }
        )

        /**
         * 低功耗
         */
        container.addView(

            createSwitch(

                "Low Power Mode",

                SettingsManager
                    .lowPowerMode

            ) {

                SettingsManager
                    .lowPowerMode = it
            }
        )

        /**
         * 粒子数量
         */
        container.addView(

            createValueAdjuster(

                "Particle Count",

                SettingsManager
                    .particleCount

            ) {

                SettingsManager
                    .particleCount = it
            }
        )

        /**
         * FPS
         */
        container.addView(

            createValueAdjuster(

                "FPS Limit",

                SettingsManager
                    .targetFps

            ) {

                SettingsManager
                    .targetFps = it
            }
        )

        /**
         * 背景模式
         */
        container.addView(

            createBackgroundSelector()
        )
    }

    /**
     * Switch
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

    /**
     * 数值调节
     */
    private fun createValueAdjuster(

        title: String,

        initial: Int,

        onChanged:
        (Int) -> Unit

    ): LinearLayout {

        var current =
            initial

        val layout =
            LinearLayout(context)

        layout.orientation =
            LinearLayout.HORIZONTAL

        layout.gravity =
            Gravity.CENTER_VERTICAL

        layout.setPadding(

            0,
            25,
            0,
            25
        )

        val titleView =
            TextView(context)

        titleView.text =
            title

        titleView.textSize =
            16f

        titleView.setTextColor(
            Color.WHITE
        )

        val valueView =
            TextView(context)

        valueView.text =
            current.toString()

        valueView.setTextColor(
            Color.CYAN
        )

        valueView.textSize =
            16f

        val minus =
            Button(context)

        minus.text = "-"

        minus.setOnClickListener {

            current -= 10

            if (current < 10) {

                current = 10
            }

            valueView.text =
                current.toString()

            onChanged(current)
        }

        val plus =
            Button(context)

        plus.text = "+"

        plus.setOnClickListener {

            current += 10

            valueView.text =
                current.toString()

            onChanged(current)
        }

        val titleParams =
            LinearLayout.LayoutParams(

                0,

                LayoutParams.WRAP_CONTENT,

                1f
            )

        layout.addView(
            titleView,
            titleParams
        )

        layout.addView(minus)

        layout.addView(valueView)

        layout.addView(plus)

        return layout
    }

    /**
     * 背景模式
     */
    private fun createBackgroundSelector():
            LinearLayout {

        val layout =
            LinearLayout(context)

        layout.orientation =
            LinearLayout.VERTICAL

        layout.setPadding(

            0,
            30,
            0,
            30
        )

        val title =
            TextView(context)

        title.text =
            "Background Mode"

        title.textSize =
            16f

        title.setTextColor(
            Color.WHITE
        )

        layout.addView(title)

        val spinner =
            Spinner(context)

        val items =
            arrayOf(

                "BLACK",

                "GRID",

                "PARTICLE",

                "GRADIENT"
            )

        val adapter =
            ArrayAdapter(

                context,

                android.R.layout
                    .simple_spinner_dropdown_item,

                items
            )

        spinner.adapter =
            adapter

        spinner.setSelection(

            SettingsManager
                .backgroundMode
                .ordinal
        )

        spinner.onItemSelectedListener =

            object :
                AdapterView
                    .OnItemSelectedListener {

                override fun onItemSelected(

                    parent: AdapterView<*>?,

                    view: android.view.View?,

                    position: Int,

                    id: Long
                ) {

                    SettingsManager
                        .backgroundMode =

                        SettingsManager
                            .BackgroundMode
                            .values()[position]
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>?
                ) {
                }
            }

        layout.addView(spinner)

        return layout
    }
}
