package com.xinsu.heartrate.ui.glass

import android.animation.SpringAnimation
import android.animation.SpringForce
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView

class GlassButton(

    context: Context

) : FrameLayout(context) {

    private val panel =
        GlassPanel(context)

    private val textView =
        TextView(context)

    init {

        initUI()

        initAnimation()
    }

    /**
     * 初始化
     */
    private fun initUI() {

        layoutParams = LayoutParams(

            320,

            120
        )

        addView(

            panel,

            LayoutParams(

                LayoutParams.MATCH_PARENT,

                LayoutParams.MATCH_PARENT
            )
        )

        textView.text = "CONNECT"

        textView.textSize = 16f

        textView.setTextColor(
            Color.WHITE
        )

        textView.gravity =
            Gravity.CENTER

        addView(

            textView,

            LayoutParams(

                LayoutParams.MATCH_PARENT,

                LayoutParams.MATCH_PARENT
            )
        )
    }

    /**
     * 弹性动画
     */
    private fun initAnimation() {

        setOnTouchListener {

                _, event ->

            when (event.action) {

                android.view.MotionEvent
                    .ACTION_DOWN -> {

                    animateScale(0.94f)
                }

                android.view.MotionEvent
                    .ACTION_UP,

                android.view.MotionEvent
                    .ACTION_CANCEL -> {

                    animateScale(1f)
                }
            }

            false
        }
    }

    /**
     * Spring Scale
     */
    private fun animateScale(
        target: Float
    ) {

        SpringAnimation(
            this,
            SCALE_X,
            target
        ).apply {

            spring.stiffness =
                SpringForce
                    .STIFFNESS_LOW

            spring.dampingRatio =
                SpringForce
                    .DAMPING_RATIO_MEDIUM_BOUNCY

        }.start()

        SpringAnimation(
            this,
            SCALE_Y,
            target
        ).apply {

            spring.stiffness =
                SpringForce
                    .STIFFNESS_LOW

            spring.dampingRatio =
                SpringForce
                    .DAMPING_RATIO_MEDIUM_BOUNCY

        }.start()
    }
}
