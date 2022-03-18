package com.vhall.myapplication.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding,V:BaseUserIntent>(val inflateFunc: (LayoutInflater) -> VB) :
    AppCompatActivity() {

    abstract val viewModel: VM
    protected val mViewBinding by lazy { inflateFunc(layoutInflater) }

    lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        mContext = this
    }
}