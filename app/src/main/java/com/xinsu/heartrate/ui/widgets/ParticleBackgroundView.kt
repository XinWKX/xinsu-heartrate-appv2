package com.xinsu.heartrate.ui.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import com.xinsu.heartrate.core.pulse.PulseEngine
import com.xinsu.heartrate.core.render.RenderListener
import com.xinsu.heartrate.core.render.RenderLoop
import com.xinsu.heartrate.particles.effects.DepthFogLayer
import com.xinsu.heartrate.particles.effects.GlowLayer
import com.xinsu.heartrate.particles.engine.ParticleEngine
import com.xinsu.heartrate.particles.renderer.ParticleRenderer

class ParticleBackgroundView(

    context: Context

) : View(context),
    RenderListener {

    private val particleEngine =
        ParticleEngine()

    private val particleRenderer =
        ParticleRenderer()

    private val glowLayer =
        GlowLayer()

    private val fogLayer =
        DepthFogLayer()

    init {

        setBackgroundColor(
            Color.BLACK
        )

        RenderLoop.addListener(this)

        RenderLoop.start()
    }

    override fun onAttachedToWindow() {

        super.onAttachedToWindow()

        post {

            particleEngine.initialize(

                width,

                height
            )
        }
    }

    override fun onDetachedFromWindow() {

        super.onDetachedFromWindow()

        RenderLoop.removeListener(this)
    }

    override fun onRender(
        deltaTime: Float
    ) {

        PulseEngine.update(deltaTime)

        particleEngine.update(

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

        // 深空雾层
        fogLayer.render(

            canvas,

            width,

            height
        )

        // 环境 Glow
        glowLayer.render(

            canvas,

            width,

            height
        )

        // 粒子层
        particleRenderer.render(

            canvas,

            particleEngine
        )
    }
    }
