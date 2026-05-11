package com.xinsu.heartrate.ui.hud

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import com.xinsu.heartrate.core.pulse.PulseEngine

class HeartRateHud(

    context: Context

) : FrameLayout(context) {

    private val bpmText =
        TextView(context)

    private val labelText =
        TextView(context)

    init {

        initUI()
    }

    private fun initUI() {

        bpmText.text = "72"

        bpmText.textSize = 84f

        bpmText.setTextColor(
            Color.WHITE
        )

        val bpmParams =
            LayoutParams(

                LayoutParams.WRAP_CONTENT,

                LayoutParams.WRAP_CONTENT
            )

        bpmParams.gravity =
            Gravity.CENTER_HORIZONTAL

        addView(
            bpmText,
            bpmParams
        )

        labelText.text =
            "BPM"

        labelText.textSize = 14f

        labelText.letterSpacing =
            0.2f

        labelText.setTextColor(

            Color.argb(

                180,

                255,

                255,

                255
            )
        )

        val labelParams =
            LayoutParams(

                LayoutParams.WRAP_CONTENT,

                LayoutParams.WRAP_CONTENT
            )

        labelParams.gravity =
            Gravity.CENTER_HORIZONTAL

        labelParams.topMargin =
            120

        addView(
            labelText,
            labelParams
        )
    }

    /**
     * 更新 BPM
     */
    fun updateBpm(
        bpm: Int
    ) {

        bpmText.text =
            bpm.toString()

        PulseEngine.bpm =
            bpm.toFloat()
    }
}
