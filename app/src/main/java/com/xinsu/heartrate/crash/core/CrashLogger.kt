package com.xinsu.heartrate.crash.core

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CrashLogger {

    /**
     * 写入崩溃日志
     */
    fun write(

        context: Context,

        throwable: Throwable
    ) {

        try {

            val dir = File(

                context.filesDir,

                "crash_logs"
            )

            if (!dir.exists()) {

                dir.mkdirs()
            }

            val time =

                SimpleDateFormat(

                    "yyyy-MM-dd_HH-mm-ss",

                    Locale.US
                ).format(Date())

            val file = File(

                dir,

                "crash_$time.txt"
            )

            val content = buildString {

                appendLine(
                    "=== XINSU CRASH LOG ==="
                )

                appendLine()

                appendLine(
                    "TIME: $time"
                )

                appendLine()

                appendLine(
                    "THREAD: ${
                        Thread.currentThread().name
                    }"
                )

                appendLine()

                appendLine(
                    "MESSAGE:"
                )

                appendLine(
                    throwable.message
                )

                appendLine()

                appendLine(
                    "STACKTRACE:"
                )

                appendLine()

                appendLine(

                    throwable
                        .stackTraceToString()
                )
            }

            file.writeText(content)

        } catch (_: Exception) {

        }
    }
}
