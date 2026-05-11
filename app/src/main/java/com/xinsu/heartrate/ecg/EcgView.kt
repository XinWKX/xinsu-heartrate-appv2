package com.xinsu.heartrate.ecg

import android.content.Context
import android.graphics.Canvas
import android.view.View
import com.xinsu.heartrate.core.render.RenderListener
import com.xinsu.heartrate.core.render.RenderLoop

class EcgView(

    context: Context

) : View(context),
    RenderListener {

    private val renderer =
        EcgRenderer()

    init {

        setLayerType(
            LAYER_TYPE_SOFTWARE,
            null
        )

        RenderLoop.addListener(this)
    }

    override fun onDetachedFromWindow() {

        super.onDetachedFromWindow()

        RenderLoop.removeListener(this)
    }

    override fun onRender(
        deltaTime: Float
    ) {

        EcgEngine.update(
            deltaTime
        )

        postInvalidateOnAnimation()
    }

    override fun onDraw(
        canvas: Canvas
    ) {

        super.onDraw(canvas)

        renderer.render(

            canvas,

            width,

            height
        )
    }
    }
