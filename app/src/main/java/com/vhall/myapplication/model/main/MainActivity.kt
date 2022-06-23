package com.vhall.myapplication.model.main

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.lifecycle.lifecycleScope
import com.vhall.myapplication.base.BaseActivity
import com.vhall.myapplication.databinding.ActivityTestLayoutBinding
import com.vhall.myapplication.model.MainIntent
import com.vhall.myapplication.model.MainModel
import com.vhall.myapplication.model.MainViewState
import com.vhall.myapplication.model.sort
import com.vhall.myapplication.test.MyPlay
import com.vhall.myapplication.test.MyView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity :
    BaseActivity<MainModel, ActivityTestLayoutBinding>(ActivityTestLayoutBinding::inflate) {

    private val _viewBinding by lazy {
        ActivityTestLayoutBinding.inflate(layoutInflater)
    }
    override lateinit var viewModel: MainModel

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(MyPlay())
        setContentView(_viewBinding.root)
        viewModel = MainModel(application)

        _viewBinding.myView.setOnTouchListener { v, event ->
            Log.e("touch", "${event.action}")
            super.onTouchEvent(event)
        }
        _viewBinding.sampleText.setOnClickListener {
            getList()
            val intent = Intent()
            intent.apply {
                data = Uri.parse("hkl://www.myapp.com/goods/?goodsId=123456")
            }
            startActivity(intent)
        }

        observeViewModel()
        val t = Test()
        _viewBinding.myView.setOnClickListener {
            Log.e("touch", "click")
            t.setI(0)
            t.test()
        }
    }

    private fun getList() {
        lifecycleScope.launch {
            viewModel.mIntent.send(MainIntent.GetListData)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.mainState.collect {
                when (it) {
                    is MainViewState.Idle -> {
                        _viewBinding.sampleText.text = "Idle"
                    }
                    is MainViewState.Loading -> {
                        toast("Loading")
                    }

                    is MainViewState.News -> {
                        _viewBinding.sampleText.text = it.name
                    }
                    is MainViewState.Error -> {
                    }

                    is MainViewState.HintLoading -> {
                        toast("HintLoading")
                    }
                }
            }
        }
    }


    fun get() {
        lifecycleScope.launch {
            _viewBinding.sampleText.text = getClipBoardStr()
        }
        var other = sort(intArrayOf(1, 3, 9, 4, 0, 7))
        other.forEach {
            Log.e("vhall", "" + it)
        }
        _viewBinding.sampleText.text = "".plus(other.toString())
    }

    private suspend fun getClipBoardStr(): String {
        val clipboardManager: ClipboardManager =
            this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val mClipData = ClipData.newPlainText("Label", "我是复制的")
        clipboardManager.setPrimaryClip(mClipData)
        delay(1000L)
        val primaryClip: ClipData? = clipboardManager.primaryClip
        // NPE
        val itemAt = primaryClip?.getItemAt(0) ?: return "拿不到呀"
        val text = itemAt.text ?: return ""
        return text.toString()
    }

    /**
     * A native method that is implemented by the 'myapplication' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String?
    external fun byteArray(): ByteArray
    private fun showAuthFromJNI(): String {
        val arrFromJNI = byteArray()
        var arrStr = ""
        for (singleChar in arrFromJNI) {
            arrStr = arrStr + singleChar
        }
        return arrStr
    }

    companion object {
        // Used to load the 'myapplication' library on application startup.
        init {
            System.loadLibrary("myapplication1")
        }
    }
}