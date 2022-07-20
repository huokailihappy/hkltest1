package com.vhall.myapplication.model.worker

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.vhall.myapplication.R
import kotlinx.coroutines.coroutineScope

/**
 * @author hkl
 *Date: 2022/6/23 18:51
 */
class ExpeditedWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result = coroutineScope {
        Log.e("vhall_", "MyWorker success")
        getForegroundInfo()//执行任务
        Result.success()
    }

    // 实现通知
    private fun getForegroundInfo(): ForegroundInfo {

        return ForegroundInfo(
            1, createNotification()
        )
    }

    /**
     * 为前台服务运行任务创建 Notification 和所需的 channel (Andrid O版本以上)
     */
    private fun createNotification(): Notification {
        //PendingIntent 可用来取消 Worker
        val intent = WorkManager.getInstance(applicationContext).createCancelPendingIntent(id)

        val builder = NotificationCompat.Builder(applicationContext, "1")
            .setContentTitle("title")
            .setTicker("title")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setOngoing(true)
            .addAction(R.mipmap.ic_launcher, "cancel", intent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("1", "name").also {
                builder.setChannelId(it.id)
            }
        }
        return builder.build()
    }

    /**
     * 为 Android O 及以上版本的设备创建所需的 notification channel
     */
    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        channelId: String,
        name: String
    ): NotificationChannel {
        return NotificationChannel(
            channelId, name, NotificationManager.IMPORTANCE_LOW
        ).also { channel ->
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}

