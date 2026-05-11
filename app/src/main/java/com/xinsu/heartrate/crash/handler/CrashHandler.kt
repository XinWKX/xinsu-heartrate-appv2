package com.xinsu.heartrate.crash.handler

import android.content.Context
import android.content.Intent
import com.xinsu.heartrate.crash.logger.CrashLogger
import com.xinsu.heartrate.crash.ui.CrashActivity
import kotlin.system.exitProcess

class CrashHandler(

    private val context: Context

) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(

        thread: Thread,

        throwable: Throwable
    ) {

        // 保存崩溃日志
        CrashLogger.saveCrashLog(

            context,
            throwable
        )

        // 打开崩溃页面
        val intent = Intent(

            context,
            CrashActivity::class.java
        )

        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK
        )

        context.startActivity(intent)

        // 杀死进程
        android.os.Process.killProcess(
            android.os.Process.myPid()
        )

        exitProcess(1)
    }
}
