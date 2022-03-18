package com.vhall.myapplication.model

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.vhall.myapplication.base.BaseActivity
import com.vhall.myapplication.base.BaseUserIntent
import com.vhall.myapplication.databinding.ActivityTestLayoutBinding
import com.vhall.myapplication.test.MyPlay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

 class MainActivity : BaseActivity<MainModel, ActivityTestLayoutBinding,MainIntent>(ActivityTestLayoutBinding::inflate) {

    private val _viewBinding by lazy {
        ActivityTestLayoutBinding.inflate(layoutInflater)
    }
     override lateinit var viewModel: MainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(MyPlay())
        setContentView(_viewBinding.root)
        viewModel=MainModel(application)

        _viewBinding.sampleText.setOnClickListener { getList() }

        observeViewModel()
    }

    private fun getList(){
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
                       toastText("Loading")
                    }

                    is MainViewState.News -> {
                        _viewBinding.sampleText.text = it.name
                    }
                    is MainViewState.Error -> {
                    }

                    is MainViewState.HintLoading -> {
                        toastText("HintLoading")
                    }
                }
            }
        }
    }

    private fun toastText(s: String) {
        TODO("Not yet implemented")
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
        val clipboardManager: ClipboardManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
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