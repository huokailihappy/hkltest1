package com.vhall.myapplication.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import vhall.com.vss2.VssSdk

abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding>(val inflateFunc: (LayoutInflater) -> VB) :
        AppCompatActivity() {

    abstract val viewModel: VM
    protected val mViewBinding by lazy { inflateFunc(layoutInflater) }

    lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        mContext = this
//        SchemeHelper.handleDeepLink(this)
    }

    protected fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}