
#include <jni.h>
#ifndef _Included_com_chenjw_attach_agent_NativeHelper
#define _Included_com_chenjw_attach_agent_NativeHelper
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_chenjw_attach_agent_NativeHelper
 * Method:    findInstancesByClass
 * Signature: (Ljava/lang/Class;)[Ljava/lang/Object;
 */
JNIEXPORT jobjectArray JNICALL Java_com_chenjw_knife_agent_NativeHelper_findInstancesByClass0
  (JNIEnv *, jclass, jclass);

/*
 * Class:     com_chenjw_knife_agent_NativeHelper
 * Method:    getFieldValue0
 * Signature: (Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_com_chenjw_knife_agent_NativeHelper_getFieldValue0
  (JNIEnv *, jclass, jobject, jclass, jstring, jclass);

/*
 * Class:     com_chenjw_knife_agent_NativeHelper
 * Method:    startClassFileLoadHook0
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_chenjw_knife_agent_NativeHelper_startClassFileLoadHook0
  (JNIEnv *, jclass);

/*
 * Class:     com_chenjw_knife_agent_NativeHelper
 * Method:    stopClassFileLoadHook0
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_chenjw_knife_agent_NativeHelper_stopClassFileLoadHook0
  (JNIEnv *, jclass);

/*
 * Class:     com_chenjw_knife_agent_NativeHelper
 * Method:    retransformClasses
 * Signature: ([Ljava/lang/Class;)V
 */
JNIEXPORT void JNICALL Java_com_chenjw_knife_agent_NativeHelper_retransformClasses
  (JNIEnv *, jclass, jobjectArray);


#ifdef __cplusplus
}
#endif
#endif

