package com.xinsu.heartrate.ui.widgets

import android.content.Context
import android.graphics.Canvas
import android.view.View
import com.xinsu.heartrate.core.render.RenderListener
import com.xinsu.heartrate.core.render.RenderLoop
import com.xinsu.heartrate.ecg.engine.EcgEngine
import com.xinsu.heartrate.ecg.renderer.EcgRenderer

class EcgHudView(

    context: Context

) : View(context),
    RenderListener {

    private val engine =
        EcgEngine()

    private val renderer =
        EcgRenderer()

    init {

        RenderLoop.addListener(this)
    }

    override fun onAttachedToWindow() {

        super.onAttachedToWindow()

        post {

            engine.initialize(width)
        }
    }

    override fun onDetachedFromWindow() {

        super.onDetachedFromWindow()

        RenderLoop.removeListener(this)
    }

    override fun onRender(
        deltaTime: Float
    ) {

        engine.update(
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

            engine,

            height / 2f
        )
    }
    }
