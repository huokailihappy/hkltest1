package com.vhall.myapplication.model.launch

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.vhall.myapplication.base.BaseActivity
import com.vhall.myapplication.databinding.ActivityLaunchBinding
import com.vhall.myapplication.model.MainModel

class LaunchActivity() : BaseActivity<MainModel, ActivityLaunchBinding>(ActivityLaunchBinding::inflate) {

    override lateinit var viewModel: MainModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        viewModel= MainModel(application)
        if (null == savedInstanceState) {
            dispatchIntent(intent)
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
}