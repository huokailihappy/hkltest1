package com.vhall.myapplication.myTEST.hook;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class BinderHookHandler implements InvocationHandler {

    private static final String TAG = "BinderHookHandler";

    // 原始的Service对象 (IInterface)
    Object base;

    public BinderHookHandler(IBinder base, Class<?> stubClass) {
        try {
            Method asInterfaceMethod = stubClass.getDeclaredMethod("asInterface", IBinder.class);
            // IClipboard.Stub.asInterface(base);
            this.base = asInterfaceMethod.invoke(null, base);
        } catch (Exception e) {
            throw new RuntimeException("hooked failed!");
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(TextUtils.equals(method.getName(),"setPrimaryClip") && args[0] instanceof ClipData){
            ClipData data = (ClipData) args[0];
            String text = data.getItemAt(0).getText().toString();
            text = text.replace("mcmeet.com","vhallyun.com/vmeeting");
            args[0] = ClipData.newPlainText(data.getDescription().getLabel(), text);
        }
        return method.invoke(base, args);
    }
}