package com.vhall.myapplication.model.worker

import android.os.Bundle
import android.util.Log
import androidx.work.*
import com.vhall.myapplication.base.BaseActivity
import com.vhall.myapplication.databinding.ActivityWorkerBinding
import java.util.concurrent.TimeUnit

class WorkerActivity() :
    BaseActivity<WorkerModel, ActivityWorkerBinding>(ActivityWorkerBinding::inflate) {

    private val _viewBinding by lazy {
        ActivityWorkerBinding.inflate(layoutInflater)
    }

    override lateinit var viewModel: WorkerModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        viewModel = WorkerModel(application)

        //参数传递
        val inputData = Data.Builder().putString("key", "value").build()
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val request = OneTimeWorkRequest.from(UploadLogWorker::class.java)
        val request1 = OneTimeWorkRequest.Builder(UploadLogWorker::class.java)
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()
        //执行定时任务
        val work1 = OneTimeWorkRequest.Builder(MyWorker::class.java)
            //1分钟后执行，还有其他指定单位(毫秒/秒/分钟/小时/天)
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()


        //时间间隔 最多15
        val request2 = PeriodicWorkRequest
            .Builder(UploadLogWorker::class.java, 15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()
        // 提交任务
        WorkManager.getInstance(mContext).enqueue(work1)
        WorkManager.getInstance(mContext).enqueue(request1)
        //通过LiveData接收返回的数据
        WorkManager.getInstance(mContext).getWorkInfoByIdLiveData(request1.id)
            .observe(this) {

                //当状态处于完成时，也就是SUCCEEDED、FAILED、CANCELLED时，
                // 任务才结束，才能去拿结果数据，不然获取到的数据是null
                Log.e("vhall_", "it.state=  " + it.state)
                if (it.state.isFinished) {
                    val msg = it.outputData.getString("key")

                    Log.e("vhall_", "it.stmsgate=  " + msg)
                    toast(msg.plus("eee"))
                }
            }


    }
}