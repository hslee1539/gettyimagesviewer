//
// Created by heesu on 2020-05-06.
//

#include "HttpsConnection.h"

heesu::network::HttpsConnection::HttpsConnection(std::string &url) {
    // 문제가 되면 싱글톤으로 해야 할 것 같음.
    curl_global_init(CURL_GLOBAL_DEFAULT);
    this->curl = curl_easy_init();
    if(this->curl){
        curl_easy_setopt(this->curl, CURLOPT_URL, url.c_str());
        curl_easy_setopt(this->curl, CURLOPT_SSL_VERIFYPEER, nullptr);
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, this->_write_func);
    }
}

heesu::network::HttpsConnection::~HttpsConnection() {
    curl_easy_cleanup(this->curl);
    this->curl = nullptr;
    curl_global_cleanup();
}

std::basic_string<char> heesu::network::HttpsConnection::get() {
    std::basic_string<char> retval;
    if(this->curl){
        curl_easy_setopt(this->curl, CURLOPT_WRITEDATA, &retval);
        curl_easy_perform(this->curl);
    }
    //return std::move(retval); // 자동으로 rvalue로 만든다고 함.
    return retval;
}

size_t heesu::network::HttpsConnection::_write_func(void *ptr, size_t size, size_t nmemb,
                                                    std::basic_string<char> *data) {
    data->append(reinterpret_cast<char*>(ptr), size * nmemb);
    return size * nmemb;
}

std::unique_ptr<heesu::network::IConnection> heesu::network::create_https_connection(std::string &url) {
    return std::unique_ptr<IConnection>(new HttpsConnection(url));
}
