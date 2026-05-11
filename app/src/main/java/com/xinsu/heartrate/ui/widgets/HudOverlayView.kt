package com.xinsu.heartrate.ui.widgets

import android.content.Context
import android.graphics.Color
import android.widget.FrameLayout
import android.widget.TextView

class HudOverlayView(

    context: Context

) : FrameLayout(context) {

    init {

        initUI()
    }

    private fun initUI() {

        val bpmText = TextView(context)

        bpmText.text = "72"

        bpmText.textSize = 64f

        bpmText.setTextColor(Color.GREEN)

        addView(bpmText)
    }
}
