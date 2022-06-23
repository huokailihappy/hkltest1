package com.vhall.myapplication.model.worker

import android.os.Bundle
import androidx.work.*
import com.vhall.myapplication.base.BaseActivity
import com.vhall.myapplication.databinding.ActivityWorkerBinding
import java.util.concurrent.TimeUnit

class WorkerActivity(override var viewModel: WorkerModel) :
    BaseActivity<WorkerModel, ActivityWorkerBinding>(ActivityWorkerBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        viewModel = WorkerModel(application)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val request = OneTimeWorkRequest.from(UploadLogWorker::class.java)

        //时间间隔 最多15
        val request2 = PeriodicWorkRequest
            .Builder(UploadLogWorker::class.java, 15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(mContext).enqueue(request2)
    }
}