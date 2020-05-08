//
// Created by heesu on 2020-05-06.
//

#include "GettyimagesParser.h"

enum class ParseingStep : int{
    init = 1,
    find_assets,
    find_img,
    find_src,
    append
};

// TODO : DOM 트리 검색을 이렇게 하자! 해당 사이트에 fit하게... 시간이 없음...
// TODO : 1. class search-content__gallery-assets 찾기 [root]
// TODO : 2. gi-asset 태그 찾기 [중간]
// TODO : 3. img 태그 찾기

size_t heesu::html::GettyimagesParser::find_assets(std::string &src) {
    size_t start = src.find("class=\"search-content__gallery-assets");
    size_t returnValue = start;
    if(start != std::string::npos){
        returnValue = src.rfind("<", start);
    }
    return returnValue;
}

size_t heesu::html::GettyimagesParser::find_img(std::string &src, size_t pos) {
    return src.find("<figure class=\"gallery-mosaic-asset__figure\" ng-class=\"figureClassNames\"><img ", pos);;
}

size_t heesu::html::GettyimagesParser::find_src(std::string &src, size_t pos) {
    std::string target = "src=\"";
    size_t returnValue = src.find(target, pos);
    if(returnValue != std::string::npos){
        returnValue += target.size();
    }
    return returnValue;
}

std::string heesu::html::GettyimagesParser::substring_from_value(std::string &src, size_t pos) {
    std::string returnValue;
    size_t last_pos = src.find('\"', pos);
    if(last_pos != std::string::npos){
        returnValue = src.substr(pos, last_pos - pos);
    }

    return returnValue;
}

std::vector<std::string> heesu::html::GettyimagesParser::get_srcs(std::string &src) {
    std::vector<std::string> returnValue;
    size_t pos = 0;
    ParseingStep step = ParseingStep::init;

    // 매번 스탭을 진행 할 때마다 find가 성공했는지 체크하기 위해 이런 시퀀스 처리 패턴 씀.
    while(pos != std::string::npos){
        switch(step){
            case ParseingStep::init:
            {
                step = ParseingStep::find_assets;
            }
                break;
            case ParseingStep::find_assets:
            {
                pos = GettyimagesParser::find_assets(src);
                step = ParseingStep::find_img;
            }
                break;
            case ParseingStep::find_img:
            {
                pos = GettyimagesParser::find_img(src, pos);
                step = ParseingStep::find_src;
            }
                break;
            case ParseingStep::find_src:
            {
                pos = GettyimagesParser::find_src(src, pos);
                step = ParseingStep::append;
            }
                break;
            case ParseingStep::append:
            {
                returnValue.push_back(GettyimagesParser::substring_from_value(src, pos));
                step = ParseingStep::find_img;
            }
                break;

        }

    }
    return returnValue;

}