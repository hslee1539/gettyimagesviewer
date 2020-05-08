//
// Created by heesu on 2020-05-06.
//

#ifndef GETTYIMAGESVIEWER_ICONNECTION_H
#define GETTYIMAGESVIEWER_ICONNECTION_H

#include <string>


namespace heesu{
    namespace network{
        class IConnection {
        public:
            // 공통적으로 연결을 해제합니다.
            virtual ~IConnection() {};
        public:
            // 공통적으로 값을 불러옵니다.
            virtual std::basic_string<char> get() = 0;
        };
    }
}

#endif //GETTYIMAGESVIEWER_ICONNECTION_H
