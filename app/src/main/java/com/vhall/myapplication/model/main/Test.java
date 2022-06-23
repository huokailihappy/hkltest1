package com.vhall.myapplication.model.main;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hkl
 * Date: 2022/4/15 6:50 下午
 */
public class Test {
    private ExecutorService joinChannelService = Executors.newSingleThreadExecutor();
    int i = 0;

    private Handler handler=new Handler();


    public void test() {
        Log.e("vhall", "i=" + i);

        joinChannelService.shutdownNow();
        joinChannelService = Executors.newSingleThreadExecutor();
        joinChannelService.execute(() -> {
            test();
        });
    }

    public void setI(int i) {
        this.i = i;
    }
}