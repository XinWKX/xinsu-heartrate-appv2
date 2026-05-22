package com.xinsu.heartrate.ui.hud

import android.content.Context
import android.graphics.*
import android.view.View
import kotlin.math.sin

class HeartRateHud(
    context: Context
) : View(context) {

    private var bpm =
        72

    private var anim =
        0f

    private val circlePaint =
        Paint().apply {

            style = Paint.Style.STROKE

            strokeWidth = 10f

            color = Color.CYAN

            isAntiAlias = true
        }

    private val bpmPaint =
        Paint().apply {

            color = Color.WHITE

            textSize = 110f

            textAlign = Paint.Align.CENTER

            isAntiAlias = true
        }

    private val subPaint =
        Paint().apply {

            color = Color.GRAY

            textSize = 34f

            textAlign = Paint.Align.CENTER

            isAntiAlias = true
        }

    fun updateHeartRate(
        value: Int
    ) {

        bpm = value

        invalidate()
    }

    override fun onDraw(
        canvas: Canvas
    ) {

        super.onDraw(canvas)

        val cx = width / 2f

        val cy = height / 2f

        anim += 0.08f

        val pulse = (

            sin(anim) * 0.5f
                    + 0.5f

        ).toFloat()

        val radius =

            180f +

            pulse * 18f

        canvas.drawCircle(

            cx,

            cy,

            radius,

            circlePaint
        )

        canvas.drawText(

            bpm.toString(),

            cx,

            cy + 25f,

            bpmPaint
        )

        canvas.drawText(

            "BPM",

            cx,

            cy + 90f,

            subPaint
        )

        invalidate()
    }
}
