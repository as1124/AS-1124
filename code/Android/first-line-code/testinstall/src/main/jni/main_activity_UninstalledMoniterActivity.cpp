//
// Created by as-1124 on 2018/9/4.
//

/* 头文件begin */
#include "main_activity_UninstalledMoniterActivity.h"
/* 头文件end */

#ifdef __cplusplus
extern "C" {
#endif

/* 内全局变量begin */
static char c_TAG[] = "UninstalledMoniterActivity.init";
static jboolean b_IS_COPY = JNI_TRUE;
/* 内全局变量 */

/*
 * Class:     main_activity_UninstalledMoniterActivity
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_main_activity_UninstalledMoniterActivity_init(JNIEnv *env, jobject obj)
{
    jstring tag = (*env)->NewStringUTF(env, c_TAG);

    //初始化log
    LOG_DEBUG((*env)->GetStringUTFChars(env, tag, &b_IS_COPY)
            , (*env)->GetStringUTFChars(env, (*env)->NewStringUTF(env, "init OK"), &b_IS_COPY));

    //fork子进程，以执行轮询任务
    pid_t pid = fork();
    if (pid < 0)
    {
        //出错log
        LOG_ERROR((*env)->GetStringUTFChars(env, tag, &b_IS_COPY)
                , (*env)->GetStringUTFChars(env, (*env)->NewStringUTF(env, "fork error !!!"), &b_IS_COPY));
    }
    else if (pid == 0)
    {
        //子进程轮询"/data/data/pym.test.uninstalledmoniter"目录是否存在，若不存在则说明已被卸载
        while (1)
        {
            FILE *p_file = fopen("/data/data/pym.test.uninstalledmoniter", "r");
            if (p_file != NULL)
            {
                fclose(p_file);

                //目录存在log
                LOG_DEBUG((*env)->GetStringUTFChars(env, tag, &b_IS_COPY)
                            , (*env)->GetStringUTFChars(env, (*env)->NewStringUTF(env, "I'm OK !!!"), &b_IS_COPY));

                sleep(1);
            }
            else
            {
                //目录不存在log
                LOG_DEBUG((*env)->GetStringUTFChars(env, tag, &b_IS_COPY)
                            , (*env)->GetStringUTFChars(env, (*env)->NewStringUTF(env, "I'm NOT OK !!!"), &b_IS_COPY));

                //执行命令am start -a android.intent.action.VIEW -d http://shouji.360.cn/web/uninstall/uninstall.html
                execlp("am", "am", "start", "-a", "android.intent.action.VIEW", "-d", "http://shouji.360.cn/web/uninstall/uninstall.html", (char *)NULL);
            }
        }
    }
    else
    {
        //父进程直接退出，使子进程被init进程领养，以避免子进程僵死
    }
}

#ifdef __cplusplus
}
#endif