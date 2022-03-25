package com.vhall.myapplication.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * @author hkl
 *Date: 2022/3/25 6:38 下午
 */
class UploadLogWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    /**
     * doWork()方法有三种类型的返回值：

    执行成功返回Result.success()
    执行失败返回Result.failure()
    需要重新执行返回Result.retry()

     */
    override fun doWork(): Result {

        return Result.success()
    }
}