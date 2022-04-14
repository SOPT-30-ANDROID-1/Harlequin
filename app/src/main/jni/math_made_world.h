#include <jni.h>
#include "android/log.h"

#ifndef ESTRELLA_NATIVE_H
#define ESTRELLA_NATIVE_H


#define LOGTAG "Native"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOGTAG,__VA_ARGS__)


extern "C"
JNIEXPORT jstring JNICALL
Java_happy_mjstudio_harlequin_util_NativeLib_prime_1factorize_1zirige_1fast(JNIEnv *env,
                                                                            jobject thiz, jlong n);


#endif //ESTRELLA_NATIVE_H