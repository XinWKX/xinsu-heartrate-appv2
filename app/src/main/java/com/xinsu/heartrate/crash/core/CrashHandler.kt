package com.xinsu.heartrate.crash.core

import android.content.Context
import android.content.Intent
import com.xinsu.heartrate.crash.ui.CrashActivity
import kotlin.system.exitProcess

class CrashHandler(

    private val context: Context
) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(

        thread: Thread,

        throwable: Throwable
    ) {

        // 写入日志
        CrashLogger.write(

            context,

            throwable
        )

        // 启动 CrashActivity
        val intent = Intent(

            context,

            CrashActivity::class.java
        )

        intent.addFlags(

            Intent.FLAG_ACTIVITY_NEW_TASK
        )

        context.startActivity(intent)

        // 强制退出
        android.os.Process.killProcess(

            android.os.Process.myPid()
        )

        exitProcess(1)
    }
}
