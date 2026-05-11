package com.xinsu.heartrate.transition.engine

import com.xinsu.heartrate.transition.model.TransitionState

object TransitionEngine {

    var state =
        TransitionState.IDLE
        private set

    /**
     * 0 ~ 1
     */
    var progress = 0f
        private set

    private var timer = 0f

    /**
     * 开始转场
     */
    fun startTransition() {

        if (state != TransitionState.IDLE) {

            return
        }

        timer = 0f

        progress = 0f

        state =
            TransitionState.COLLAPSING
    }

    /**
     * 更新
     */
    fun update(
        deltaTime: Float
    ) {

        when (state) {

            TransitionState.IDLE -> {}

            TransitionState.COLLAPSING -> {

                timer += deltaTime

                progress =
                    (timer / 0.45f)
                        .coerceIn(0f, 1f)

                if (progress >= 1f) {

                    timer = 0f

                    state =
                        TransitionState.BLACKOUT
                }
            }

            TransitionState.BLACKOUT -> {

                timer += deltaTime

                progress = 1f

                if (timer >= 0.12f) {

                    timer = 0f

                    state =
                        TransitionState.EXPANDING
                }
            }

            TransitionState.EXPANDING -> {

                timer += deltaTime

                progress =
                    1f - (
                        timer / 0.55f
                    ).coerceIn(0f, 1f)

                if (progress <= 0f) {

                    progress = 0f

                    state =
                        TransitionState.IDLE
                }
            }
        }
    }
}
