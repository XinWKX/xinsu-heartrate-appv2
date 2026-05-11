package com.xinsu.heartrate.bluetooth.ui

import android.content.Context
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.xinsu.heartrate.bluetooth.model.HeartRateDevice
import com.xinsu.heartrate.ui.glass.GlassPanel

class DeviceListPanel(

    context: Context

) : FrameLayout(context) {

    private val container =
        LinearLayout(context)

    init {

        initUI()
    }

    private fun initUI() {

        val glass =
            GlassPanel(context)

        addView(

            glass,

            LayoutParams(

                LayoutParams.MATCH_PARENT,

                LayoutParams.MATCH_PARENT
            )
        )

        container.orientation =
            LinearLayout.VERTICAL

        container.gravity =
            Gravity.TOP

        container.setPadding(

            24,
            24,
            24,
            24
        )

        addView(

            container,

            LayoutParams(

                LayoutParams.MATCH_PARENT,

                LayoutParams.MATCH_PARENT
            )
        )
    }

    /**
     * 更新设备列表
     */
    fun updateDevices(
        devices: List<HeartRateDevice>
    ) {

        container.removeAllViews()

        devices.forEach {

            val card =
                DeviceCard(context)

            card.bind(it)

            val params =
                LinearLayout.LayoutParams(

                    LayoutParams.MATCH_PARENT,

                    180
                )

            params.bottomMargin =
                20

            container.addView(
                card,
                params
            )
        }
    }
}
