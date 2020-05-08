//
// Created by heesu on 2020-05-06.
//

#ifndef GETTYIMAGESVIEWER_HTTPSCONNECTION_H
#define GETTYIMAGESVIEWER_HTTPSCONNECTION_H
#include "IConnection.h"
#include <jni.h>
#include "curl/curl.h"

namespace heesu{
    namespace network{
        class HttpsConnection : public IConnection{
        private:
            CURL *curl;
        public:
            HttpsConnection(std::string &url);
            ~HttpsConnection();
        protected:
            static size_t _write_func(void* ptr, size_t size, size_t nmemb, std::basic_string<char> *data);
        public:
            std::basic_string<char> get() override;
        };

        // https 프로토콜 연결을 합니다.
        // ## return
        // HttpsConnection 객체를 반환합니다.
        std::unique_ptr<IConnection> create_https_connection(std::string &url);
    }
}



#endif //GETTYIMAGESVIEWER_HTTPSCONNECTION_H
