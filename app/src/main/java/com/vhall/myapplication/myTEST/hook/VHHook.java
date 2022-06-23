package com.vhall.myapplication.myTEST.hook;

import android.app.NotificationManager;
import android.content.Context;
import android.os.IBinder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class VHHook {
    public static void binderHook(){
        try {

// 下面这一段的意思实际就是: ServiceManager.getService("clipboard");
// 只不过 ServiceManager这个类是@hide的
            Class<?> serviceManager = Class.forName("android.os.ServiceManager");
            Method getService = serviceManager.getDeclaredMethod("getService", String.class);
// ServiceManager里面管理的原始的Clipboard Binder对象
// 一般来说这是一个Binder代理对象
            IBinder remoteBinder = (IBinder) getService.invoke(null, Context.CLIPBOARD_SERVICE);

// Hook 掉这个Binder代理对象的 queryLocalInterface 方法
// 然后在 queryLocalInterface 返回一个IInterface对象, hook掉我们感兴趣的方法即可.
            IBinder hookedBinder = (IBinder) Proxy.newProxyInstance(serviceManager.getClassLoader(),
                    new Class<?>[] { IBinder.class },
                    new BinderProxyHookHandler(remoteBinder));

// 把这个hook过的Binder代理对象放进ServiceManager的cache里面
// 以后查询的时候 会优先查询缓存里面的Binder, 这样就会使用被我们修改过的Binder了
            Field cacheField = serviceManager.getDeclaredField("sCache");
            cacheField.setAccessible(true);
            Map<String, IBinder> cache = (Map) cacheField.get(null);
            cache.put(Context.CLIPBOARD_SERVICE, hookedBinder);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }



    public static void hookActivityManager(){
        try {
            Class<?> amnClass = Class.forName("android.app.ActivityManager");
            Method getService = amnClass.getDeclaredMethod("getService");

            getService.setAccessible(true);

            Object IActivityTaskManager = getService.invoke(null);


            Field IActivityManagerSingletonF = amnClass.getDeclaredField("IActivityManagerSingleton");
            IActivityManagerSingletonF.setAccessible(true);
            Object IActivityManagerSingleton = IActivityManagerSingletonF.get(null);

            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);

            Object instance = mInstanceField.get(IActivityManagerSingleton);

            //创建代理 handler
            ProxyHandler handler = new ProxyHandler(instance);
            Class<?> amClass = Class.forName("android.app.IActivityManager");
            Object amProxy = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{amClass}, handler);

            mInstanceField.set(IActivityManagerSingleton,amProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void hookNotificationManager(){
        try {
            Method getServiceM = NotificationManager.class.getDeclaredMethod("getService");
            getServiceM.setAccessible(true);
            Object iNotificationManager = getServiceM.invoke(null);
            NotificationManager a = null;
            a.getClass().getClassLoader();

            Field sServiceF = NotificationManager.class.getDeclaredField("sService");
            sServiceF.setAccessible(true);

            //代理
            ProxyHandler handler = new ProxyHandler(iNotificationManager);
            Class<?> iNotificationManagerC = Class.forName("android.app.INotificationManager");
            Object proxy = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),new Class[]{iNotificationManagerC},handler);

            sServiceF.set(null,proxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
