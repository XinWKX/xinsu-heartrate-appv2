package com.xinsu.heartrate.ui.glass

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class GlassButton @JvmOverloads constructor(

    context: Context,

    attrs: AttributeSet? = null

) : AppCompatButton(
    context,
    attrs
) {

    init {

        text = "CONNECT"

        textSize = 18f

        setTextColor(
            Color.WHITE
        )

        val bg =
            GradientDrawable()

        bg.cornerRadius = 42f

        bg.setColor(
            Color.parseColor(
                "#33FFFFFF"
            )
        )

        background = bg

        setPadding(
            60,
            30,
            60,
            30
        )
    }
}
