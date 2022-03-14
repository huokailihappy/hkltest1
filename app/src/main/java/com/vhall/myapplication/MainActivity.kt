package com.vhall.myapplication

import androidx.appcompat.app.AppCompatActivity
import kotlin.jvm.JvmStatic
import com.vhall.myapplication.MainActivity
import android.os.Bundle
import com.vhall.myapplication.MyPlay
import android.widget.TextView
import com.vhall.myapplication.databinding.ActivityMainBinding

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

    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(MyPlay())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        // Example of a call to a native method
        val c: ContentResolverTest = ContentResolverTest()
        val tv = binding!!.sampleText
        tv.text =ContentResolverTest().get(this)
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