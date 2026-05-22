package com.xinsu.heartrate

import android.graphics.Color
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.xinsu.heartrate.ui.widgets.RootHudLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        try {

            initUI()

        } catch (e: Exception) {

            showErrorScreen(e)
        }
    }

    /**
     * 初始化主界面
     */
    private fun initUI() {

        val root =
            RootHudLayout(this)

        setContentView(root)
    }

    /**
     * 如果 RootHudLayout 崩溃
     * 直接显示异常内容
     */
    private fun showErrorScreen(
        throwable: Throwable
    ) {

        val layout =
            FrameLayout(this)

        layout.setBackgroundColor(
            Color.BLACK
        )

        val textView =
            TextView(this)

        textView.setTextColor(
            Color.WHITE
        )

        textView.textSize = 14f

        textView.text =
            buildString {

                append("UI 初始化失败\n\n")

                append(
                    throwable.javaClass.simpleName
                )

                append("\n\n")

                append(
                    throwable.message
                )

                append("\n\n")

                append(
                    throwable.stackTraceToString()
                )
            }

        layout.addView(textView)

        setContentView(layout)
    }
}
