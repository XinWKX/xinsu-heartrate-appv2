package com.xinsu.heartrate.bluetooth.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class BlePermissionManager(

    private val activity: Activity
) {

    companion object {

        const val REQUEST_CODE = 1001
    }

    /**
     * 是否已经拥有权限
     */
    fun hasPermissions(): Boolean {

        return requiredPermissions()
            .all {

                ContextCompat.checkSelfPermission(

                    activity,

                    it

                ) == PackageManager.PERMISSION_GRANTED
            }
    }

    /**
     * 请求权限
     */
    fun requestPermissions() {

        ActivityCompat.requestPermissions(

            activity,

            requiredPermissions(),

            REQUEST_CODE
        )
    }

    /**
     * 获取不同安卓版本权限
     */
    private fun requiredPermissions():
            Array<String> {

        return when {

            Build.VERSION.SDK_INT >=
                    Build.VERSION_CODES.S -> {

                arrayOf(

                    Manifest.permission
                        .BLUETOOTH_SCAN,

                    Manifest.permission
                        .BLUETOOTH_CONNECT
                )
            }

            else -> {

                arrayOf(

                    Manifest.permission
                        .ACCESS_FINE_LOCATION,

                    Manifest.permission
                        .BLUETOOTH,

                    Manifest.permission
                        .BLUETOOTH_ADMIN
                )
            }
        }
    }
}
