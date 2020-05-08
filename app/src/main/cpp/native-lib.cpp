#include <jni.h>
#include <string>
#include "HttpsConnection.h"
#include "GettyimagesParser.h"

extern "C"
JNIEXPORT jobjectArray JNICALL
Java_com_example_gettyimagesviewer_GettyImagesLoader_get_1urls(JNIEnv *env, jobject thiz,
                                                               jstring url) {
    auto target_url = std::string(env->GetStringUTFChars(url,0));
    auto connection = heesu::network::create_https_connection(target_url);

    auto html_src = connection->get();
    auto img_srcs = heesu::html::GettyimagesParser::get_srcs(html_src);

    auto returnValue = env->NewObjectArray(img_srcs.size(),
                                           env->FindClass("java/lang/String"),
                                           0);
    auto i = size_t(0);
    for(auto img_src : img_srcs){
        env->SetObjectArrayElement(returnValue, i++, env->NewStringUTF(img_src.c_str()));
    }
    return returnValue;
}