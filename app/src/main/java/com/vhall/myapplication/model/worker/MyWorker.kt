package com.vhall.myapplication.model.worker

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import kotlinx.coroutines.coroutineScope

/**
 * @author hkl
 *Date: 2022/6/23 18:51
 */
class MyWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = coroutineScope {


        Log.e("vhall_", "MyWorker success")
        getForegroundInfo().notification//执行任务
        Result.success()
    }

    // 实现通知
    fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            1, NotificationCompat.Builder(applicationContext, "33")
                .setContentTitle("标题")
                .setContentText("描述")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()
        )
    }
}

