package org.example.classifier.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.classifier.dto.CategoryDto;

import java.util.List;

@Mapper
public interface CategoryMapper {
    //카테고리 조회
    List<CategoryDto> categoryName();

    //카테고리 이름추가
    void addName(String name);

    //카테고리 이름 중복확인
    int countName(String name);

}
