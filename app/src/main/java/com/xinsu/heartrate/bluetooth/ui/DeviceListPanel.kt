package com.xinsu.heartrate.bluetooth.ui

import android.content.Context
import android.graphics.*
import android.view.View
import com.xinsu.heartrate.bluetooth.model.HeartRateDevice

class DeviceListPanel(
    context: Context
) : View(context) {

    private val devices =
        mutableListOf<HeartRateDevice>()

    private val backgroundPaint =
        Paint().apply {

            color = Color.argb(
                60,
                15,
                15,
                15
            )

            isAntiAlias = true
        }

    private val borderPaint =
        Paint().apply {

            style = Paint.Style.STROKE

            strokeWidth = 2f

            color = Color.argb(
                100,
                255,
                255,
                255
            )

            isAntiAlias = true
        }

    private val titlePaint =
        Paint().apply {

            color = Color.WHITE

            textSize = 42f

            isAntiAlias = true
        }

    private val namePaint =
        Paint().apply {

            color = Color.WHITE

            textSize = 34f

            isAntiAlias = true
        }

    private val infoPaint =
        Paint().apply {

            color = Color.GRAY

            textSize = 26f

            isAntiAlias = true
        }

    private val heartPaint =
        Paint().apply {

            color = Color.CYAN

            textSize = 24f

            isAntiAlias = true
        }

    fun updateDevices(
        list: List<HeartRateDevice>
    ) {

        devices.clear()

        devices.addAll(list)

        invalidate()
    }

    override fun onDraw(
        canvas: Canvas
    ) {

        super.onDraw(canvas)

        val rect = RectF(

            0f,

            0f,

            width.toFloat(),

            height.toFloat()
        )

        canvas.drawRoundRect(

            rect,

            32f,

            32f,

            backgroundPaint
        )

        canvas.drawRoundRect(

            rect,

            32f,

            32f,

            borderPaint
        )

        canvas.drawText(

            "HEART RATE DEVICES",

            40f,

            70f,

            titlePaint
        )

        var top =
            150f

        devices.forEach {

            device ->

            drawDeviceCard(

                canvas,

                device,

                top
            )

            top += 170f
        }
    }

    private fun drawDeviceCard(

        canvas: Canvas,

        device: HeartRateDevice,

        top: Float
    ) {

        val rect = RectF(

            30f,

            top,

            width - 30f,

            top + 130f
        )

        val cardPaint = Paint().apply {

            color = Color.argb(
                50,
                255,
                255,
                255
            )

            isAntiAlias = true
        }

        canvas.drawRoundRect(

            rect,

            24f,

            24f,

            cardPaint
        )

        canvas.drawText(

            device.name,

            60f,

            top + 50f,

            namePaint
        )

        canvas.drawText(

            device.mac,

            60f,

            top + 95f,

            infoPaint
        )

        canvas.drawText(

            "RSSI ${device.rssi}",

            width - 250f,

            top + 50f,

            infoPaint
        )

        val hrText = if (
            device.hasHeartRateService
        ) {

            "Heart Rate Service"
        } else {

            "Unknown Device"
        }

        heartPaint.color = if (
            device.hasHeartRateService
        ) {

            Color.CYAN
        } else {

            Color.RED
        }

        canvas.drawText(

            hrText,

            width - 320f,

            top + 95f,

            heartPaint
        )
    }
}
