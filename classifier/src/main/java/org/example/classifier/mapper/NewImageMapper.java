package org.example.classifier.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.classifier.dto.NewImageDto;

@Mapper
public interface NewImageMapper {
    void registerErrorImage(NewImageDto image);
}
