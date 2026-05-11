package com.xinsu.heartrate.ui.hud

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import com.xinsu.heartrate.ui.glass.GlassPanel

class TopStatusBar(

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

        val title =
            TextView(context)

        title.text =
            "XINSU MEDICAL HUD"

        title.textSize = 14f

        title.letterSpacing =
            0.12f

        title.setTextColor(

            Color.argb(

                220,

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
            Gravity.CENTER_VERTICAL

        params.leftMargin =
            36

        addView(
            title,
            params
        )
    }
}
