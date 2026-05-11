package com.xinsu.heartrate.ui.widgets

import android.content.Context
import android.graphics.Canvas
import android.view.View
import com.xinsu.heartrate.core.pulse.PulseEngine
import com.xinsu.heartrate.core.render.RenderListener
import com.xinsu.heartrate.core.render.RenderLoop
import com.xinsu.heartrate.particles.engine.ParticleEngine
import com.xinsu.heartrate.particles.renderer.ParticleRenderer

class ParticleBackgroundView(

    context: Context

) : View(context),
    RenderListener {

    private val engine =
        ParticleEngine()

    private val renderer =
        ParticleRenderer()

    init {

        RenderLoop.addListener(this)

        RenderLoop.start()
    }

    override fun onAttachedToWindow() {

        super.onAttachedToWindow()

        engine.initialize(

            width,

            height
        )
    }

    override fun onDetachedFromWindow() {

        super.onDetachedFromWindow()

        RenderLoop.removeListener(this)
    }

    override fun onRender(
        deltaTime: Float
    ) {

        PulseEngine.update(deltaTime)

        engine.update(

            deltaTime,

            width,

            height
        )

        postInvalidateOnAnimation()
    }

    override fun onDraw(
        canvas: Canvas
    ) {

        super.onDraw(canvas)

        renderer.render(

            canvas,

            engine
        )
    }
    }
