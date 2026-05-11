package com.xinsu.heartrate.core.render

interface RenderListener {

    /**
     * 每帧更新
     *
     * @param deltaTime
     * 秒
     */
    fun onRender(
        deltaTime: Float
    )
}
