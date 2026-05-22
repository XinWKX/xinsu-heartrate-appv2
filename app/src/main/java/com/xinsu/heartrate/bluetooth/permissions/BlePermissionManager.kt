package com.xinsu.heartrate.bluetooth.permissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object BlePermissionManager {

    const val REQUEST_CODE = 1001

    /**
     * 检查 BLE 权限
     */
    fun hasPermissions(
        context: Context
    ): Boolean {

        return if (

            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.S

        ) {

            val scan =
                ContextCompat.checkSelfPermission(

                    context,

                    Manifest.permission.BLUETOOTH_SCAN
                )

            val connect =
                ContextCompat.checkSelfPermission(

                    context,

                    Manifest.permission.BLUETOOTH_CONNECT
                )

            scan ==
                    PackageManager.PERMISSION_GRANTED

                    &&

                    connect ==
                    PackageManager.PERMISSION_GRANTED

        } else {

            val location =
                ContextCompat.checkSelfPermission(

                    context,

                    Manifest.permission.ACCESS_FINE_LOCATION
                )

            location ==
                    PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * 请求 BLE 权限
     */
    fun requestPermissions(
        activity: Activity
    ) {

        if (

            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.S

        ) {

            ActivityCompat.requestPermissions(

                activity,

                arrayOf(

                    Manifest.permission.BLUETOOTH_SCAN,

                    Manifest.permission.BLUETOOTH_CONNECT
                ),

                REQUEST_CODE
            )

        } else {

            ActivityCompat.requestPermissions(

                activity,

                arrayOf(

                    Manifest.permission.ACCESS_FINE_LOCATION
                ),

                REQUEST_CODE
            )
        }
    }
}
