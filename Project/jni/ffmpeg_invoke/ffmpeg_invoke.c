#include <stdlib.h>
#include <dlfcn.h>
#include "ffmpeg_invoke.h"

void Java_co_vine_android_recorder_FFmpegInvoke_run(JNIEnv* env, jobject obj, jstring libffmpeg_path, jobjectArray ffmpeg_args)
{
	const char* path;
	void* handle;
	void (*Java_com_atonality_ffmpeg_FFmpeg_run)(JNIEnv*, jobject, jobjectArray);

	path = (*env)->GetStringUTFChars(env, libffmpeg_path, 0);
	handle = dlopen(path, RTLD_LAZY);
	(*env)->ReleaseStringUTFChars(env, libffmpeg_path, path);

	Java_com_atonality_ffmpeg_FFmpeg_run = dlsym(handle, "Java_co_vine_android_recorder_Processor_run");
	(*Java_com_atonality_ffmpeg_FFmpeg_run)(env, obj, ffmpeg_args);

	dlclose(handle);
}
