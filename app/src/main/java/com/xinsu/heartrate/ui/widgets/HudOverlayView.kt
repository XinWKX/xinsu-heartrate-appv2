package com.xinsu.heartrate.ui.widgets

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import com.xinsu.heartrate.ui.glass.GlassButton
import com.xinsu.heartrate.ui.glass.GlassPanel

class HudOverlayView(

    context: Context

) : FrameLayout(context) {

    init {

        initUI()
    }

    private fun initUI() {

        // BPM 显示
        val bpmText =
            TextView(context)

        bpmText.text = "72"

        bpmText.textSize = 72f

        bpmText.setTextColor(
            Color.WHITE
        )

        val bpmParams =
            LayoutParams(

                LayoutParams.WRAP_CONTENT,

                LayoutParams.WRAP_CONTENT
            )

        bpmParams.gravity =
            Gravity.CENTER

        addView(
            bpmText,
            bpmParams
        )

        // Glass 按钮
        val connectButton =
            GlassButton(context)

        val buttonParams =
            LayoutParams(

                LayoutParams.WRAP_CONTENT,

                LayoutParams.WRAP_CONTENT
            )

        buttonParams.gravity =
            Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL

        buttonParams.bottomMargin =
            120

        addView(
            connectButton,
            buttonParams
        )

        // 顶部 HUD 面板
        val topPanel =
            GlassPanel(context)

        val topParams =
            LayoutParams(

                LayoutParams.MATCH_PARENT,

                140
            )

        topParams.topMargin =
            60

        topParams.leftMargin =
            40

        topParams.rightMargin =
            40

        addView(
            topPanel,
            topParams
        )
    }
}
