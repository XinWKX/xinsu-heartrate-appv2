package com.xinsu.heartrate.bluetooth.runtime

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object BlePermissionHelper {

    const val REQUEST_CODE =
        8891

    /**
     * 检查权限
     */
    fun hasPermissions(
        context: Context
    ): Boolean {

        return requiredPermissions()

            .all {

                ContextCompat.checkSelfPermission(

                    context,

                    it

                ) == PackageManager.PERMISSION_GRANTED
            }
    }

    /**
     * 请求权限
     */
    fun requestPermissions(
        activity: Activity
    ) {

        ActivityCompat.requestPermissions(

            activity,

            requiredPermissions(),

            REQUEST_CODE
        )
    }

    /**
     * 权限列表
     */
    private fun requiredPermissions():
            Array<String> {

        return if (

            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.S

        ) {

            arrayOf(

                Manifest.permission.BLUETOOTH_SCAN,

                Manifest.permission.BLUETOOTH_CONNECT
            )

        } else {

            arrayOf(

                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }
}
