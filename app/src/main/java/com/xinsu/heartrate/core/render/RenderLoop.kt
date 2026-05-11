package com.xinsu.heartrate.core.render

import android.view.Choreographer

object RenderLoop : Choreographer.FrameCallback {

    private val listeners =
        mutableListOf<RenderListener>()

    private var running = false

    private var lastFrameTime = 0L

    /**
     * 开始渲染循环
     */
    fun start() {

        if (running) return

        running = true

        Choreographer
            .getInstance()
            .postFrameCallback(this)
    }

    /**
     * 停止渲染循环
     */
    fun stop() {

        running = false
    }

    /**
     * 添加渲染监听
     */
    fun addListener(
        listener: RenderListener
    ) {

        if (!listeners.contains(listener)) {

            listeners.add(listener)
        }
    }

    /**
     * 移除监听
     */
    fun removeListener(
        listener: RenderListener
    ) {

        listeners.remove(listener)
    }

    /**
     * 每帧更新
     */
    override fun doFrame(
        frameTimeNanos: Long
    ) {

        if (!running) return

        val currentTime =
            frameTimeNanos / 1_000_000

        val deltaTime =
            if (lastFrameTime == 0L) {

                0f

            } else {

                (
                    currentTime -
                    lastFrameTime
                ) / 1000f
            }

        lastFrameTime = currentTime

        listeners.forEach {

            it.onRender(deltaTime)
        }

        Choreographer
            .getInstance()
            .postFrameCallback(this)
    }
}
