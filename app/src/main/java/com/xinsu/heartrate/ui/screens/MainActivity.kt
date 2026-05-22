package com.xinsu.heartrate.ui.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xinsu.heartrate.bluetooth.runtime.BlePermissionHelper
import com.xinsu.heartrate.ui.widgets.RootHudLayout

class MainActivity :
    AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )

        checkPermissions()
    }

    /**
     * 检查 BLE 权限
     */
    private fun checkPermissions() {

        if (

            BlePermissionHelper.hasPermissions(
                this
            )

        ) {

            initUI()

        } else {

            BlePermissionHelper
                .requestPermissions(
                    this
                )
        }
    }

    /**
     * 权限回调
     */
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

            BlePermissionHelper.REQUEST_CODE

        ) {

            if (

                BlePermissionHelper.hasPermissions(
                    this
                )

            ) {

                initUI()
            }
        }
    }

    /**
     * 初始化 UI
     */
    private fun initUI() {

        val root =
            RootHudLayout(this)

        setContentView(root)
    }
    }
