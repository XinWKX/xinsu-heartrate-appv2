package com.xinsu.heartrate.bluetooth.ui

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import com.xinsu.heartrate.bluetooth.model.HeartRateDevice

class DeviceListPanel(
    context: Context
) : View(context) {

    /**
     * 设备列表
     */
    private val devices =
        mutableListOf<HeartRateDevice>()

    /**
     * Device 点击区域
     */
    private val deviceBounds =
        mutableListOf<Pair<RectF, HeartRateDevice>>()

    /**
     * 当前连接设备
     */
    private var connectedMac:
            String? = null

    /**
     * 当前按下设备
     */
    private var pressedMac:
            String? = null

    /**
     * 点击回调
     */
    var onDeviceClicked:
            ((HeartRateDevice) -> Unit)?
        = null

    /**
     * Background
     */
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

    /**
     * Border
     */
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

    /**
     * 标题
     */
    private val titlePaint =
        Paint().apply {

            color = Color.WHITE

            textSize = 42f

            isAntiAlias = true

            typeface =
                Typeface.DEFAULT_BOLD
        }

    /**
     * Device Name
     */
    private val namePaint =
        Paint().apply {

            color = Color.WHITE

            textSize = 34f

            isAntiAlias = true
        }

    /**
     * Info
     */
    private val infoPaint =
        Paint().apply {

            color = Color.GRAY

            textSize = 26f

            isAntiAlias = true
        }

    /**
     * Heart
     */
    private val heartPaint =
        Paint().apply {

            color = Color.CYAN

            textSize = 24f

            isAntiAlias = true
        }

    /**
     * Connected
     */
    private val connectedPaint =
        Paint().apply {

            color = Color.argb(
                80,
                0,
                255,
                200
            )

            isAntiAlias = true
        }

    /**
     * Pressed
     */
    private val pressedPaint =
        Paint().apply {

            color = Color.argb(
                120,
                255,
                255,
                255
            )

            isAntiAlias = true
        }

    /**
     * 更新设备
     */
    fun updateDevices(
        list: List<HeartRateDevice>
    ) {

        devices.clear()

        devices.addAll(list)

        invalidate()
    }

    /**
     * 设置当前连接设备
     */
    fun setConnectedDevice(
        mac: String?
    ) {

        connectedMac = mac

        invalidate()
    }

    /**
     * Draw
     */
    override fun onDraw(
        canvas: Canvas
    ) {

        super.onDraw(canvas)

        deviceBounds.clear()

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

        var top = 150f

        devices.forEach {

            device ->

            drawDeviceCard(

                canvas,

                device,

                top
            )

            top += 170f
        }

        if (devices.isEmpty()) {

            drawEmpty(canvas)
        }
    }

    /**
     * 空状态
     */
    private fun drawEmpty(
        canvas: Canvas
    ) {

        val paint =
            Paint().apply {

                color = Color.argb(
                    180,
                    255,
                    255,
                    255
                )

                textSize = 32f

                isAntiAlias = true

                textAlign =
                    Paint.Align.CENTER
            }

        canvas.drawText(

            "NO HEART RATE DEVICE",

            width / 2f,

            height / 2f,

            paint
        )
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

            30f,

            top,

            width - 30f,

            top + 130f
        )

        deviceBounds.add(
            Pair(rect, device)
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

        /**
         * 已连接高亮
         */
        if (

            connectedMac ==
            device.mac

        ) {

            canvas.drawRoundRect(

                rect,

                24f,

                24f,

                connectedPaint
            )
        }

        /**
         * 按下效果
         */
        if (

            pressedMac ==
            device.mac

        ) {

            canvas.drawRoundRect(

                rect,

                24f,

                24f,

                pressedPaint
            )
        }

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

        /**
         * CONNECTED
         */
        if (

            connectedMac ==
            device.mac

        ) {

            val connectPaint =
                Paint().apply {

                    color = Color.GREEN

                    textSize = 22f

                    isAntiAlias = true
                }

            canvas.drawText(

                "CONNECTED",

                width - 260f,

                top + 120f,

                connectPaint
            )
        }
    }

    /**
     * Touch
     */
    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {

                pressedMac =

                    findTouchedDevice(

                        event.x,

                        event.y

                    )?.mac

                invalidate()

                return true
            }

            MotionEvent.ACTION_UP -> {

                val device =

                    findTouchedDevice(

                        event.x,

                        event.y
                    )

                pressedMac = null

                invalidate()

                if (device != null) {

                    onDeviceClicked
                        ?.invoke(device)
                }

                return true
            }

            MotionEvent.ACTION_CANCEL -> {

                pressedMac = null

                invalidate()

                return true
            }
        }

        return super.onTouchEvent(event)
    }

    /**
     * 查找点击设备
     */
    private fun findTouchedDevice(

        x: Float,

        y: Float

    ): HeartRateDevice? {

        deviceBounds.forEach {

            pair ->

            if (

                pair.first.contains(
                    x,
                    y
                )

            ) {

                return pair.second
            }
        }

        return null
    }
}
