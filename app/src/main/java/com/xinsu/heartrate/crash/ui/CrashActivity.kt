package com.xinsu.heartrate.crash.ui

import android.os.Bundle
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CrashActivity : AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        val log = intent.getStringExtra(
            "crash_log"
        ) ?: "No Crash Log"

        val textView = TextView(this)

        textView.text = log

        textView.textSize = 14f

        val scrollView = ScrollView(this)

        scrollView.addView(textView)

        setContentView(scrollView)
    }
}
