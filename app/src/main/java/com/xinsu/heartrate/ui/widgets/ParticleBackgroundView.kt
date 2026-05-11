package com.xinsu.heartrate.ui.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class ParticleBackgroundView(

    context: Context

) : View(context) {

    private val paint = Paint().apply {

        color = Color.argb(

            40,
            0,
            255,
            100
        )

        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {

        super.onDraw(canvas)

        canvas.drawCircle(

            width / 2f,
            height / 2f,
            200f,
            paint
        )

        postInvalidateOnAnimation()
    }
}
