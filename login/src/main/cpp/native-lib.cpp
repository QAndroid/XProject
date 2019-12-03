//
// Created by QITMAC0000562 on 2019-08-29.
//
#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_workshop1024_com_xproject_native_NativeLib_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
