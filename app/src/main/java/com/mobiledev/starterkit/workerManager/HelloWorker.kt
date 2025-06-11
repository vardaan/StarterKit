package com.mobiledev.starterkit.workerManager

import android.content.Context
import android.os.Looper
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import timber.log.Timber

class HelloWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        Timber.i("👋 Hello from background!")

        val thread = Thread({
            // Simulate some work
            Looper.prepare()
            Thread.sleep(2000)
        }).start()
        return Result.success()
    }
}
