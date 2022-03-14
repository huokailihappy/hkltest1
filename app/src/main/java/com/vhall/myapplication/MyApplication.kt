package com.vhall.myapplication

import android.app.Application
import com.vhall.myapplication.hook.VHHook

/**
 * @author hkl
 *Date: 2022/3/14 6:46 下午
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        VHHook.binderHook()
        VHHook.hookActivityManager()
        VHHook.hookNotificationManager()
    }
}