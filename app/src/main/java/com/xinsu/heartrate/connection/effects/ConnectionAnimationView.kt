package com.xinsu.heartrate.connection.effects

import android.content.Context
import android.graphics.Canvas
import android.view.View
import com.xinsu.heartrate.core.render.RenderListener
import com.xinsu.heartrate.core.render.RenderLoop

class ConnectionAnimationView(

    context: Context

) : View(context),
    RenderListener {

    private val ring =
        ConnectionRing()

    private val particles =
        ConnectionParticles()

    init {

        alpha = 0f

        RenderLoop.addListener(this)
    }

    override fun onDetachedFromWindow() {

        super.onDetachedFromWindow()

        RenderLoop.removeListener(this)
    }

    override fun onRender(
        deltaTime: Float
    ) {

        ring.update(
            deltaTime
        )

        particles.update(
            deltaTime
        )

        postInvalidateOnAnimation()
    }

    override fun onDraw(
        canvas: Canvas
    ) {

        super.onDraw(canvas)

        particles.render(

            canvas,

            width,

            height
        )

        ring.render(

            canvas,

            width,

            height
        )
    }

    /**
     * 开始动画
     */
    fun show() {

        animate()

            .alpha(1f)

            .setDuration(600)

            .start()
    }

    /**
     * 结束动画
     */
    fun hide() {

        animate()

            .alpha(0f)

            .setDuration(400)

            .start()
    }
    }
