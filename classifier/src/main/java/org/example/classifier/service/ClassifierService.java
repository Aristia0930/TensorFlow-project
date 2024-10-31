package org.example.classifier.service;

import org.example.classifier.dto.CategoryDto;
import org.example.classifier.dto.NewImageDto;
import org.example.classifier.mapper.CategoryMapper;
import org.example.classifier.mapper.NewImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


//이미지 분류후 정답정정이나 올바른 정답에 대한
// 이미지 임시 파일에 저장
@Service
public class ClassifierService {
    private final NewImageMapper newImageMapper;
    private final CategoryMapper categoryMapper;


    //파일생성위치
    @Value("${custom.temp-img}")
    private String path;

    public ClassifierService(NewImageMapper newImageMapper, CategoryMapper categoryMapper) {
        this.newImageMapper = newImageMapper;
        this.categoryMapper = categoryMapper;
    }

    //임시저장파일생성위치 관리
    public boolean make_dir(){
        File file=new File(path);
        return file.mkdir();

    }

    //파일 임시보폴더에 저장
    //이름 중복없애기 위한 uuid 사용
    public boolean makeFile(MultipartFile file,String text) {
        try {
            String originName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String newName = uuid+originName;

            file.transferTo(new File(path , newName));

            //이제 db에 저장
            NewImageDto image=new NewImageDto();
            image.setPath(path);
            image.setName(newName);
            image.setCategory(text);
            image.setApproval(0);
            image.setStatus(1);

            newImageMapper.registerErrorImage(image);

            return true;
        }catch (IOException e){
            return false;
        }

    }

    public List<String> categorys() {
        return categoryMapper.categoryNames();
    }
}
