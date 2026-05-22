package com.xinsu.heartrate.ui.screens

import android.graphics.Color
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.xinsu.heartrate.bluetooth.permission.BlePermissionManager
import com.xinsu.heartrate.ui.widgets.RootHudLayout

class MainActivity :
    AppCompatActivity() {

    private lateinit var
            permissionManager:
            BlePermissionManager

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )

        permissionManager =
            BlePermissionManager(this)

        try {

            if (
                permissionManager
                    .hasPermissions()
            ) {

                initUI()

            } else {

                permissionManager
                    .requestPermissions()
            }

        } catch (
            e: Exception
        ) {

            showErrorScreen(e)
        }
    }

    override fun onRequestPermissionsResult(

        requestCode: Int,

        permissions: Array<out String>,

        grantResults: IntArray
    ) {

        super.onRequestPermissionsResult(

            requestCode,

            permissions,

            grantResults
        )

        if (
            requestCode ==
            BlePermissionManager
                .REQUEST_CODE
        ) {

            if (
                permissionManager
                    .hasPermissions()
            ) {

                initUI()

            } else {

                finish()
            }
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
     * 错误页面
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

                append(
                    "UI 初始化失败\n\n"
                )

                append(

                    throwable
                        .javaClass
                        .simpleName
                )

                append("\n\n")

                append(
                    throwable.message
                )

                append("\n\n")

                append(
                    throwable
                        .stackTraceToString()
                )
            }

        layout.addView(textView)

        setContentView(layout)
    }
    }
