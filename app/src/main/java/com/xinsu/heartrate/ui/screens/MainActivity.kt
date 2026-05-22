package com.xinsu.heartrate.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.xinsu.heartrate.ui.widgets.RootHudLayout

class MainActivity :
    AppCompatActivity() {

    companion object {

        private const val REQUEST_BLE =
            1001
    }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )

        requestBlePermissions()
    }

    /**
     * BLE 权限
     */
    private fun requestBlePermissions() {

        val permissions =
            mutableListOf<String>()

        // Android 12+
        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.S
        ) {

            if (

                ContextCompat.checkSelfPermission(

                    this,

                    Manifest.permission.BLUETOOTH_SCAN

                ) !=
                PackageManager.PERMISSION_GRANTED
            ) {

                permissions.add(
                    Manifest.permission.BLUETOOTH_SCAN
                )
            }

            if (

                ContextCompat.checkSelfPermission(

                    this,

                    Manifest.permission.BLUETOOTH_CONNECT

                ) !=
                PackageManager.PERMISSION_GRANTED
            ) {

                permissions.add(
                    Manifest.permission.BLUETOOTH_CONNECT
                )
            }
        }

        // Android 10 / 11
        else {

            if (

                ContextCompat.checkSelfPermission(

                    this,

                    Manifest.permission.ACCESS_FINE_LOCATION

                ) !=
                PackageManager.PERMISSION_GRANTED
            ) {

                permissions.add(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }

        if (
            permissions.isNotEmpty()
        ) {

            ActivityCompat.requestPermissions(

                this,

                permissions.toTypedArray(),

                REQUEST_BLE
            )

        } else {

            initUI()
        }
    }

    /**
     * 权限结果
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
            requestCode == REQUEST_BLE
        ) {

            val granted =

                grantResults.all {

                    it ==
                    PackageManager.PERMISSION_GRANTED
                }

            if (granted) {

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
