package com.xinsu.heartrate.ui.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class EcgHudView(

    context: Context

) : View(context) {

    private val paint = Paint().apply {

        color = Color.GREEN

        strokeWidth = 4f

        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {

        super.onDraw(canvas)

        val centerY = height / 2f

        canvas.drawLine(

            0f,
            centerY,
            width.toFloat(),
            centerY,
            paint
        )
    }
}
