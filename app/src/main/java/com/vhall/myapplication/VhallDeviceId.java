package com.vhall.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author hkl
 * Date: 2022/3/11 10:53 上午
 */
public class VhallDeviceId {
    private static final String SHARED_PREFERENCES_NAME = "VhallDeviceId";
    private static final String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + ".vvcatchnum";
    private static String vhallDeviceId = "";
    //允许读取系统设置
    private static boolean systemReader = true;

    public static void setSystemReader(boolean systemReader) {
        VhallDeviceId.systemReader = systemReader;
    }

    public static String getVhallDeviceId(Context context) {
        if (!TextUtils.isEmpty(vhallDeviceId)) {
            return vhallDeviceId;
        }
        if (!getIdFromCatch(context).isEmpty()) {
            vhallDeviceId = getIdFromCatch(context);
            return vhallDeviceId;
        }
        if (!getIdFromFile(context).isEmpty()) {
            vhallDeviceId = getIdFromFile(context);
            return vhallDeviceId;
        }
        if (systemReader && !TextUtils.isEmpty(getIMEI(context))) {
            vhallDeviceId = getIMEI(context);
            setId(context, vhallDeviceId);
            setIdToFile(context, vhallDeviceId);
            return vhallDeviceId;
        }
        vhallDeviceId = UUID.randomUUID().toString();
        setIdToFile(context, vhallDeviceId);
        setId(context, vhallDeviceId);
        return vhallDeviceId;
    }

    @SuppressLint("MissingPermission")
    private static String getIMEI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //获取 当前选择的第几个滤镜
    private static String getIdFromCatch(Context context) {
        if (context == null) {
            return "";
        }
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String vhall_id = sp.getString("vhall_id", "");
        return vhall_id;
    }

    //存储 当前选择的第几个滤镜
    private static void setId(Context context, String vhall_id) {
        if (context == null) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("vhall_id", vhall_id);
        editor.apply();
    }

    private static boolean lacksPermissions(Context mContexts) {
        if (mContexts.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && mContexts.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private static String getIdFromFile(Context context) {
        String content = "";
        if (lacksPermissions(context)) {
            File file = new File(fileName);
            FileInputStream fis;
            try {
                if (file.exists()) {
                    fis = new FileInputStream(file);
                    content = readStringFromInputStream(fis);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    private static String readStringFromInputStream(FileInputStream fis) {
        if (fis == null) {
            return "";
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toString();
    }

    private static void setIdToFile(Context context, String id) {
        if (lacksPermissions(context)) {
            File file = new File(fileName);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(id.getBytes("UTF-8"));
            } catch (Exception e) {
                Log.e("vhall_", e.getMessage());
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
} 