package com.xinsu.heartrate.ui.widgets

import android.content.Context
import android.graphics.Color
import android.widget.FrameLayout

class RootHudLayout(

    context: Context

) : FrameLayout(context) {

    init {

        initLayout()
    }

    /**
     * 初始化根布局
     */
    private fun initLayout() {

        setBackgroundColor(Color.BLACK)

        createLayers()
    }

    /**
     * 创建 UI 层
     */
    private fun createLayers() {

        // 背景粒子层
        addView(

            ParticleBackgroundView(context)
        )

        // ECG 层
        addView(

            EcgHudView(context)
        )

        // HUD 顶层
        addView(

            HudOverlayView(context)
        )
    }
}
