package com.vhall.myapplication.model.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.PeriodicWorkRequest

import androidx.work.WorkRequest
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit


/**
 * @author hkl
 *Date: 2022/3/25 6:38 下午
 * https://juejin.cn/post/6844903630194081805
 * WorkManager
 */
class UploadLogWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    /**
     * doWork()方法有三种类型的返回值：

    执行成功返回Result.success()
    执行失败返回Result.failure()
    需要重新执行返回Result.retry()

     */
    override  fun doWork(): Result {
        Log.e("vhall_", "success")

        return Result.success()
    }

    var saveRequest: WorkRequest = PeriodicWorkRequest.Builder(
        UploadLogWorker::class.java,
        1, TimeUnit.HOURS,
        15, TimeUnit.MINUTES
    ).build()


}