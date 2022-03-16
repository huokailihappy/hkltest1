package com.vhall.myapplication

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.vhall.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println(sort(intArrayOf(1, 3, 9, 4, 0, 7)))
        }

        fun sort(num: IntArray): IntArray {
            val length = num.size
            for (i in 0 until length - 1) {
                var pos = 0
                for (j in 0 until length - i - 1) {
                    val temp = num[j]
                    if (num[j] > num[j + 1]) {
                        num[j] = num[j + 1]
                        num[j + 1] = temp
                        pos = 1
                    }
                }
                if (pos == 0) {
                    return num
                }
            }
            return num
        }

        // Used to load the 'myapplication' library on application startup.
        init {
            System.loadLibrary("myapplication1")
        }
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(MyPlay())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Example of a call to a native method
        val c: ContentResolverTest = ContentResolverTest()
//        binding.sampleText.text = ContentResolverTest().get(this)
        lifecycleScope.launch {
            binding.sampleText.text = getClipBoardStr()
        }

        binding.sampleText.setOnClickListener {
            get()
        }
    }

    fun get() {
        lifecycleScope.launch {
            binding.sampleText.text = getClipBoardStr()
        }
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
}