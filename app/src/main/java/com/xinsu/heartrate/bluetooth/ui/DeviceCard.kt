package com.xinsu.heartrate.bluetooth.ui

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.xinsu.heartrate.bluetooth.model.HeartRateDevice
import com.xinsu.heartrate.ui.glass.GlassPanel

class DeviceCard(

    context: Context

) : FrameLayout(context) {

    private val nameText =
        TextView(context)

    private val signalText =
        TextView(context)

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

        val container =
            LinearLayout(context)

        container.orientation =
            LinearLayout.VERTICAL

        container.gravity =
            Gravity.CENTER_VERTICAL

        container.setPadding(

            40,
            20,
            40,
            20
        )

        addView(

            container,

            LayoutParams(

                LayoutParams.MATCH_PARENT,

                LayoutParams.MATCH_PARENT
            )
        )

        nameText.textSize =
            18f

        nameText.setTextColor(
            Color.WHITE
        )

        signalText.textSize =
            13f

        signalText.setTextColor(

            Color.argb(

                180,

                255,

                255,

                255
            )
        )

        container.addView(nameText)

        container.addView(signalText)
    }

    /**
     * 绑定设备
     */
    fun bind(
        device: HeartRateDevice
    ) {

        nameText.text =
            device.name

        signalText.text =
            "RSSI ${device.rssi}"
    }
}
