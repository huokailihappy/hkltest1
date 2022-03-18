package com.vhall.myapplication.test;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * @author hkl
 * Date: 2022/3/11 9:45 上午
 */
public class MyPlay implements LifecycleEventObserver {

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:

                break;
            case ON_START:

                break;
            case ON_RESUME:

                break;
            case ON_PAUSE:

                break;
            case ON_STOP:

                break;
            case ON_DESTROY:

                break;
            case ON_ANY:
                throw new IllegalArgumentException("ON_ANY must not been send by anybody");
        }
    }
}