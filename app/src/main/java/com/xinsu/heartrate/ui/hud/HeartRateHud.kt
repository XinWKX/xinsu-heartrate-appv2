package com.xinsu.heartrate.ui.hud

import android.content.Context
import android.graphics.*
import android.view.View
import com.xinsu.heartrate.bluetooth.data.HeartRateRepository
import kotlin.math.min

class HeartRateHud(
    context: Context
) : View(context) {

    private val bpmPaint = Paint().apply {

        color = Color.WHITE

        textAlign = Paint.Align.CENTER

        isAntiAlias = true

        typeface = Typeface.DEFAULT_BOLD
    }

    private val labelPaint = Paint().apply {

        color = Color.argb(
            180,
            255,
            255,
            255
        )

        textAlign = Paint.Align.CENTER

        textSize = 42f

        isAntiAlias = true
    }

    private val ringPaint = Paint().apply {

        style = Paint.Style.STROKE

        strokeWidth = 8f

        color = Color.argb(
            120,
            255,
            255,
            255
        )

        isAntiAlias = true
    }

    override fun onDraw(
        canvas: Canvas
    ) {

        super.onDraw(canvas)

        val cx = width / 2f

        val cy = height / 2f

        val bpm =
            HeartRateRepository.currentBpm

        val radius =

            min(width, height)
                * 0.28f

        canvas.drawCircle(

            cx,

            cy,

            radius,

            ringPaint
        )

        bpmPaint.textSize =
            radius * 0.55f

        canvas.drawText(

            if (bpm <= 0)
                "--"
            else
                bpm.toString(),

            cx,

            cy + 40f,

            bpmPaint
        )

        canvas.drawText(

            "BPM",

            cx,

            cy + radius + 70f,

            labelPaint
        )

        invalidate()
    }
}
