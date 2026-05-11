package com.xinsu.heartrate.ui.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import com.xinsu.heartrate.core.pulse.PulseEngine
import com.xinsu.heartrate.core.render.RenderListener
import com.xinsu.heartrate.core.render.RenderLoop
import com.xinsu.heartrate.particles.effects.DepthFogLayer
import com.xinsu.heartrate.particles.effects.GlowLayer
import com.xinsu.heartrate.particles.engine.ParticleEngine
import com.xinsu.heartrate.particles.renderer.ParticleRenderer
import com.xinsu.heartrate.transition.engine.TransitionEngine
import com.xinsu.heartrate.transition.renderer.TransitionRenderer

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

    private val transitionRenderer =
        TransitionRenderer()

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

        TransitionEngine.update(
            deltaTime
        )

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

        fogLayer.render(

            canvas,

            width,

            height
        )

        glowLayer.render(

            canvas,

            width,

            height
        )

        particleRenderer.render(

            canvas,

            particleEngine
        )

        transitionRenderer.render(

            canvas,

            width,

            height
        )
    }

    /**
     * 点击触发转场
     */
    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {

        if (
            event.action ==
            MotionEvent.ACTION_DOWN
        ) {

            TransitionEngine.startTransition()
        }

        return true
    }
    }
