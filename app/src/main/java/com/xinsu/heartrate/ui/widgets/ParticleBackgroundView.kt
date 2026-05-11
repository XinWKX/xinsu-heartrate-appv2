package com.xinsu.heartrate.ui.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import com.xinsu.heartrate.audio.core.AudioEngine
import com.xinsu.heartrate.audio.pulse.PulseAudioController
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

    /**
     * 粒子引擎
     */
    private val particleEngine =
        ParticleEngine()

    /**
     * 粒子渲染器
     */
    private val particleRenderer =
        ParticleRenderer()

    /**
     * 环境 Glow
     */
    private val glowLayer =
        GlowLayer()

    /**
     * 空间雾层
     */
    private val fogLayer =
        DepthFogLayer()

    /**
     * 转场渲染器
     */
    private val transitionRenderer =
        TransitionRenderer()

    /**
     * Pulse 音效控制
     */
    private val pulseAudioController =
        PulseAudioController()

    init {

        setBackgroundColor(
            Color.BLACK
        )

        // 初始化音频系统
        AudioEngine.initialize(
            context
        )

        // 注册 RenderLoop
        RenderLoop.addListener(this)

        RenderLoop.start()
    }

    /**
     * View Attached
     */
    override fun onAttachedToWindow() {

        super.onAttachedToWindow()

        post {

            particleEngine.initialize(

                width,

                height
            )
        }
    }

    /**
     * View Detached
     */
    override fun onDetachedFromWindow() {

        super.onDetachedFromWindow()

        RenderLoop.removeListener(this)

        AudioEngine.release()
    }

    /**
     * RenderLoop 更新
     */
    override fun onRender(
        deltaTime: Float
    ) {

        // 更新 Pulse
        PulseEngine.update(
            deltaTime
        )

        // 更新音效
        pulseAudioController.update()

        // 更新 Transition
        TransitionEngine.update(
            deltaTime
        )

        // 更新粒子
        particleEngine.update(

            deltaTime,

            width,

            height
        )

        // 请求重绘
        postInvalidateOnAnimation()
    }

    /**
     * 绘制
     */
    override fun onDraw(
        canvas: Canvas
    ) {

        super.onDraw(canvas)

        // 空间雾层
        fogLayer.render(

            canvas,

            width,

            height
        )

        // Glow
        glowLayer.render(

            canvas,

            width,

            height
        )

        // 粒子
        particleRenderer.render(

            canvas,

            particleEngine
        )

        // 黑场转场
        transitionRenderer.render(

            canvas,

            width,

            height
        )
    }

    /**
     * 点击触发转场测试
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
