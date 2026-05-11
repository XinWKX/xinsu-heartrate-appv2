package com.xinsu.heartrate.ui.hud

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import com.xinsu.heartrate.ui.glass.GlassPanel

class BluetoothHud(

    context: Context

) : FrameLayout(context) {

    private val statusText =
        TextView(context)

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

        statusText.text =
            "DISCONNECTED"

        statusText.textSize = 13f

        statusText.letterSpacing =
            0.12f

        statusText.setTextColor(

            Color.argb(

                200,

                255,

                255,

                255
            )
        )

        val params =
            LayoutParams(

                LayoutParams.WRAP_CONTENT,

                LayoutParams.WRAP_CONTENT
            )

        params.gravity =
            Gravity.CENTER

        addView(
            statusText,
            params
        )
    }

    /**
     * 更新状态
     */
    fun updateStatus(
        text: String
    ) {

        statusText.text = text
    }
}
