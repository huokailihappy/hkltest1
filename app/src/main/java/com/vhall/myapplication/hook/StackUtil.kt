package com.vhall.myapplication.hook

import java.lang.Exception

/**
 * @author hkl
 *Date: 2022/3/17 10:36 上午
 */
class StackUtil {
    fun configLegal(): Boolean {
        val stackTrace = Thread.currentThread().stackTrace
        val size = stackTrace.size
        for (index in 0 until size) {
            val stackTraceElement = stackTrace[index]
            val className = stackTraceElement.className
            val methodName = stackTraceElement.methodName
            try {
                //是这个类调用这个方法才可以
                if (className == "com.vhall.myapplication.hook.StackUtil" && methodName == "configLegal") {
                    return true
                }
            } catch (var8: Exception) {
                var8.printStackTrace()
            }
        }
        return false
    }
}