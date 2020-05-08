//
// Created by heesu on 2020-05-06.
//

#ifndef GETTYIMAGESVIEWER_GETTYIMAGESPARSER_H
#define GETTYIMAGESVIEWER_GETTYIMAGESPARSER_H

#include <string>
#include <vector>

namespace heesu{
    namespace html{
        class GettyimagesParser {
        public:
            // img가 포함된 div의 위치를 찾음
            static size_t find_assets(std::string &src);
            // img를 찾음
            static size_t find_img(std::string &src, size_t pos);
            // src를 찾음
            static size_t find_src(std::string &src, size_t pos);
            // ""안의 값을 문자열로 반환함
            static std::string substring_from_value(std::string &src, size_t pos);
            // 이미지 url을 파싱함.
            static std::vector<std::string> get_srcs(std::string &src);
        };
    }
}


#endif //GETTYIMAGESVIEWER_GETTYIMAGESPARSER_H
