package org.example.classifier.service;

import org.example.classifier.dto.CategoryDto;
import org.example.classifier.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//분류된 이미지를 관리자가 선별하여 모델 업데이트
public class ManagerService {
    private final CategoryMapper categoryMapper;

    public ManagerService(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    //카테고리 이름 조회
    public List<CategoryDto> names(){
        return categoryMapper.categoryName();
    }

    //카테고리 추가
    public int addName(String name){

        //같은이름존재하는지 확인
        int count=categoryMapper.countName(name);


        if (count>0){
            System.out.println("중복입니다");
            return 0;
        }
        else{
            System.out.println("입력가능");
            return 1;
        }




    }


}
