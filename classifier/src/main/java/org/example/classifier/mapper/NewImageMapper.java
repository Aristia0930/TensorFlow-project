package org.example.classifier.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.classifier.dto.NewImageDto;

import java.util.List;

@Mapper
public interface NewImageMapper {

    void registerErrorImage(NewImageDto image);

    //학습할 이미지 불러오기
    List<NewImageDto> imgeTrain();


    Integer addTrain(int id);

    Integer retrain(int id);
}
