package com.xinsu.heartrate.bluetooth.ui

import android.content.Context
import android.graphics.*
import android.view.View
import com.xinsu.heartrate.bluetooth.model.HeartRateDevice

class DeviceListPanel(
    context: Context
) : View(context) {

    private var devices =
        emptyList<HeartRateDevice>()

    private val cardPaint = Paint().apply {

        color = Color.argb(
            40,
            255,
            255,
            255
        )

        isAntiAlias = true
    }

    private val borderPaint = Paint().apply {

        style = Paint.Style.STROKE

        strokeWidth = 2f

        color = Color.argb(
            80,
            255,
            255,
            255
        )

        isAntiAlias = true
    }

    private val titlePaint = Paint().apply {

        color = Color.WHITE

        textSize = 42f

        isAntiAlias = true

        typeface = Typeface.DEFAULT_BOLD
    }

    private val infoPaint = Paint().apply {

        color = Color.argb(
            180,
            255,
            255,
            255
        )

        textSize = 30f

        isAntiAlias = true
    }

    /**
     * 更新设备
     */
    fun updateDevices(
        newDevices:
        List<HeartRateDevice>
    ) {

        devices = newDevices

        invalidate()
    }

    override fun onDraw(
        canvas: Canvas
    ) {

        super.onDraw(canvas)

        val startY = 40f

        val spacing = 190f

        devices.forEachIndexed {

            index,
            device ->

            val top =

                startY +

                index * spacing

            drawDeviceCard(

                canvas,

                device,

                top
            )
        }
    }

    /**
     * 绘制设备卡片
     */
    private fun drawDeviceCard(

        canvas: Canvas,

        device: HeartRateDevice,

        top: Float
    ) {

        val rect = RectF(

            20f,

            top,

            width - 20f,

            top + 150f
        )

        canvas.drawRoundRect(

            rect,

            32f,

            32f,

            cardPaint
        )

        canvas.drawRoundRect(

            rect,

            32f,

            32f,

            borderPaint
        )

        // 名称
        canvas.drawText(

            device.name,

            50f,

            top + 60f,

            titlePaint
        )

        // MAC
        canvas.drawText(

            device.address,

            50f,

            top + 105f,

            infoPaint
        )

        // RSSI
        canvas.drawText(

            "RSSI ${device.rssi}",

            width - 240f,

            top + 85f,

            infoPaint
        )

        // 状态圆点
        val signalPaint = Paint().apply {

            color = when {

                device.rssi >= -50 ->
                    Color.GREEN

                device.rssi >= -70 ->
                    Color.YELLOW

                else ->
                    Color.RED
            }

            isAntiAlias = true
        }

        canvas.drawCircle(

            width - 70f,

            top + 75f,

            14f,

            signalPaint
        )
    }
}
