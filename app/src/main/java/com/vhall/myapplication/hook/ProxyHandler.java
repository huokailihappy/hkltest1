package com.vhall.myapplication.hook;

import android.app.Notification;
import android.text.TextUtils;

import com.vhall.vhallzoom.R;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {
    private Object origin;

    public ProxyHandler(Object origin) {
        this.origin = origin;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        processSetServiceForeground(method, args);
        processEnqueueNotificationWithTag(method, args);
        return method.invoke(origin,args);
    }

    private void processEnqueueNotificationWithTag(Method method, Object[] args) {
        if(TextUtils.equals("enqueueNotificationWithTag",method.getName())){
            if(args.length > 4){
                if(args[4] instanceof Notification){
                    ((Notification) args[4]).extras.putString("android.title","Vhall");
                    ((Notification) args[4]).icon = R.mipmap.app_icon;
                }
            }
        }
    }

    private void processSetServiceForeground(Method method, Object[] args) {
        if(TextUtils.equals(method.getName(),"setServiceForeground")){
            if(args.length > 3){
                if(args[3] instanceof Notification){
                    ((Notification) args[3]).extras.putString("android.title","Vhall");
                    ((Notification) args[3]).icon = R.mipmap.app_icon;
                }
            }
        }
    }
}
