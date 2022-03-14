#include <jni.h>
#include <string>
#include "authpack.h"
#include <android/log.h>

extern "C" JNIEXPORT jbyteArray JNICALL
Java_com_vhall_myapplication_MainActivity_byteArray(
        JNIEnv* env,
        jobject /* this */) {

    int arrLen = sizeof (g_auth_package);
    jbyteArray arr = env->NewByteArray(arrLen);
    (*env).SetByteArrayRegion(arr, 0, arrLen, reinterpret_cast<const jbyte *>(g_auth_package));
    return arr;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_vhall_myapplication_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}