package com.xinsu.heartrate.crash.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CrashActivity : AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        val textView = TextView(this)

        textView.text =
            "应用发生崩溃\\n\\n请导出日志"

        textView.textSize = 18f

        setContentView(textView)
    }
}
