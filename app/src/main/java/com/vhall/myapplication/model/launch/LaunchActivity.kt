package com.vhall.myapplication.model.launch

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MotionEvent
import androidx.work.*
import com.vhall.myapplication.base.BaseActivity
import com.vhall.myapplication.databinding.ActivityLaunchBinding
import com.vhall.myapplication.model.MainModel
import com.vhall.myapplication.model.worker.UploadLogWorker
import java.lang.Exception
import java.util.concurrent.TimeUnit

class LaunchActivity() :
    BaseActivity<MainModel, ActivityLaunchBinding>(ActivityLaunchBinding::inflate) {

    override lateinit var viewModel: MainModel

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        viewModel = MainModel(application)
        if (null == savedInstanceState) {
            dispatchIntent(intent)
        }


        mViewBinding.hello.setOnClickListener { toast("hello") }
        mViewBinding.hello.setOnTouchListener { v, event ->
            Log.e("touch", "${event.action}")
            true
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        dispatchIntent(intent)
    }

    private fun dispatchIntent(intent: Intent?) {
        if (null == intent) {
            return
        }
        val uri: Uri = intent.data ?: return
        // 根据自定义协议解析 Uri
        toast(uri.toString())
//
    }

    private fun filterBeautifyImpl() {
        try {
            val metaData = applicationInfo.metaData
            for (component in metaData.keySet()) {
                if (TextUtils.equals(metaData.getString(component), "hkl_test1")) {
                    Class.forName(component).newInstance()
                    //只注册第一个被发现的美颜sdk
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}